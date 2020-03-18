package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {
    @Autowired
    private MealService service;

    @GetMapping("/delete")
    public String deleteMeal(@RequestParam String id) {
        int mealID = Integer.parseInt(id);
        int userId = SecurityUtil.authUserId();
        service.delete(mealID, userId);
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String createMeal(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/update")
    public String updateMeal(@RequestParam String id, Model model) {
        int mealID = Integer.parseInt(id);
        int userId = SecurityUtil.authUserId();
        Meal meal = service.get(mealID, userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping
    public String createOrUpdate(HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        int userId = SecurityUtil.authUserId();
        if (request.getParameter("id").isEmpty()) {
            service.create(meal, userId);
        } else {
            int mealId = Integer.valueOf(request.getParameter("id"));
            assureIdConsistent(meal, mealId);
            service.update(meal, userId);
        }
        return "redirect:/meals";
    }
}
