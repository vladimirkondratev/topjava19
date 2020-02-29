package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final Meal MEAL_USER_1 = new Meal(100002, LocalDateTime.of(2020, 2, 28, 8, 0), "завтрак", 500);
    public static final Meal MEAL_USER_2 = new Meal(100003, LocalDateTime.of(2020, 2, 28, 14, 0), "обед", 1000);
    public static final Meal MEAL_USER_3 = new Meal(100004, LocalDateTime.of(2020, 2, 28, 20, 0), "ужин", 500);
    public static final Meal MEAL_USER_4 = new Meal(100005, LocalDateTime.of(2020, 2, 29, 20, 0), "ужин", 500);
    public static final Meal MEAL_USER_5 = new Meal(100006, LocalDateTime.of(2020, 2, 29, 21, 0), "ужин 2", 500);
    public static final Meal MEAL_ADMIN_1 = new Meal(100007, LocalDateTime.of(2020, 2, 29, 11, 0), "ланч", 400);
    public static final Meal MEAL_ADMIN_2 = new Meal(100008, LocalDateTime.of(2020, 2, 29, 16, 0), "полдник", 500);

    public static final int MEAL_ID_1 = 100002;

    public static final LocalDate START_DATE = LocalDate.of(2020,2,29);
    public static final LocalDate END_DATE = LocalDate.of(2020,2,29);

    public static final Meal getNew(){
        return new Meal(LocalDateTime.of(2020, 2, 29, 23, 30), "новая еда", 500);
    }

    public static final Meal getUpdated(){
        Meal meal = new Meal(MEAL_USER_1);
        meal.setDescription("Updated meal");
        return meal;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
