package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.to.UserTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 1950;

    public static final List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 25, 10, 0), "Завтрак", 500,2),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 26, 13, 0), "Обед", 1000,2),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 27, 20, 0), "Ужин", 500,2),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 28, 0, 0), "Еда на граничное значение", 100,1),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 27, 10, 0), "Завтрак", 1000,2),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 26, 13, 0), "Обед", 500,2),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 25, 20, 0), "Ужин", 410,2)
    );

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return filterByPredicate(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return filterByPredicate(meals, caloriesPerDay, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime));
    }

    private static List<MealTo> filterByPredicate(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        List<MealTo> list = meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());

        Comparator<MealTo> mealInitialComparator = Comparator.comparing(MealTo::getDateTime);
        list.sort(mealInitialComparator);

        return list;
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
