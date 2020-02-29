package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID_1, USER_ID);
        assertMatch(meal, MEAL_USER_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(1, 1);
    }

    @Test(expected = NotFoundException.class)
    public void getWrongUser() {
        service.get(MEAL_ID_1, 1);
    }

    @Test(expected = NotFoundException.class)
    public void delete() {
        service.delete(MEAL_ID_1, USER_ID);
        service.get(MEAL_ID_1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWrongUser() {
        service.delete(MEAL_ID_1, 1);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(1, 1);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> meals = service.getBetweenHalfOpen(START_DATE, END_DATE, USER_ID);
        assertMatch(meals, MEAL_USER_3, MEAL_USER_2, MEAL_USER_1);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, MEAL_USER_5, MEAL_USER_4, MEAL_USER_3, MEAL_USER_2, MEAL_USER_1);
    }

    @Test
    public void update() {
        Meal meal = MealTestData.getUpdated();
        service.update(meal, USER_ID);
        assertMatch(service.get(MEAL_ID_1, USER_ID), meal);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal meal = MealTestData.getUpdated();
        meal.setId(1);
        service.update(meal, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateWrongUser() {
        Meal meal = MealTestData.getUpdated();
        service.update(meal, 1);
    }

    @Test
    public void create() {
        Meal newMeal = MealTestData.getNew();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }
}