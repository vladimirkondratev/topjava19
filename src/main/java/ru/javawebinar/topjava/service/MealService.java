package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, Integer userId){
        return repository.save(meal, userId);
    }

    public void update(Meal meal, Integer userId){
        checkNotFoundWithId(repository.save(meal, userId), userId);
    }

    public void delete(int id, Integer userId){
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, Integer userId){
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Collection<Meal> getAll(Integer userId) {
        return repository.getAll(userId);
    }

    public Collection<Meal> getAllFiltered(Integer userId, LocalDateTime start, LocalDateTime end){
        return repository.getAllFiltered(userId, start, end);
    }
}