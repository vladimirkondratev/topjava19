package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDateTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(Meal meal, Integer userId){
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void update(Meal meal, Integer userId){
        log.info("update {} with user id={}", meal, userId);
        service.update(meal, userId);
    }

    public void delete(int id, Integer userId){
        log.info("delete {}", id);
        service.delete(id, userId);
    }

    public Meal get(int id, Integer userId){
        log.info("get {}", id);
        return service.get(id, userId);
    }

    public Collection<Meal> getAll(Integer userId) {
        log.info("getAll id {}", userId);
        return service.getAll(userId);
    }

    public Collection<Meal> getAll(Integer userId, LocalDateTime start, LocalDateTime end) {
        log.info("getAllFiltered id {}", userId);
        return service.getAllFiltered(userId, start, end);
    }

}