package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.service.MealService;

public class MealRestController {
    @Autowired
    private MealService service;

}