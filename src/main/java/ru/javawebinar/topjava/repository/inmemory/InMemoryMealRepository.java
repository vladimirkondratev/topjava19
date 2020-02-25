package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return meal.getUserId().equals(userId) ? repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) : null;
    }

    @Override
    public boolean delete(int id, Integer userId) {
        Meal meal = repository.get(id);
        return meal != null && (meal.getUserId().equals(userId) && repository.remove(id) != null);
    }

    @Override
    public Meal get(int id, Integer userId) {
        Meal meal = repository.get(id);
        return (meal != null) && meal.getUserId().equals(userId) ? meal : null;
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        return getAllFiltered(userId, LocalDateTime.MIN, LocalDateTime.MAX);
    }

    @Override
    public Collection<Meal> getAllFiltered(Integer userId, LocalDateTime start, LocalDateTime end) {
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId().equals(userId)
                        && DateTimeUtil.isBetweenInclusive(meal.getDateTime(), start, end))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

