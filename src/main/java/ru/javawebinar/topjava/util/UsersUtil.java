package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserTo;

import java.util.*;
import java.util.stream.Collectors;

public class UsersUtil {

    public static final List<User> users = Arrays.asList(
            new User(null,"DTest1", "test1@mail.ru", "test123"),
            new User(null,"ATest2", "test2@mail.ru", "test123")
    );

    public static List<UserTo> getTos(Collection<User> users) {
        return sortByAlphabet(users);
    }

    private static List<UserTo> sortByAlphabet(Collection<User> users) {
        List<UserTo> list = users.stream()
                .map(UsersUtil::createTo)
                .collect(Collectors.toList());
        Comparator<UserTo> userInitialComparator = Comparator.comparing(UserTo::getName);
        list.sort(userInitialComparator);
        return list;
    }

    private static UserTo createTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail());
    }
}
