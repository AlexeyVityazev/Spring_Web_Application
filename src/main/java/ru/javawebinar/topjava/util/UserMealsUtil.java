package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 30), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), 2000);
        mealsTo.forEach(System.out::println);

       System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(9, 00), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dateAndCalories = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate date = LocalDate.of(meal.getDateTime().getYear(), meal.getDateTime().getMonth(), meal.getDateTime().getDayOfMonth());
            dateAndCalories.merge(date, meal.getCalories(),(val1, val2) -> val1+val2);
        }

        Map<LocalDate, Boolean> dateWithExcess = new HashMap<>();
        for (Map.Entry<LocalDate, Integer> entry : dateAndCalories.entrySet()) {
            if (entry.getValue() > caloriesPerDay) {
                dateWithExcess.put(entry.getKey(), false);
            }else {
                dateWithExcess.put(entry.getKey(), true);
            }
        }
        
        List<UserMealWithExcess> filteredList = new ArrayList<>();
        for(UserMeal meal : meals){
           if(TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(),startTime,endTime)) {
               LocalDate date = LocalDate.of(meal.getDateTime().getYear(), meal.getDateTime().getMonth(), meal.getDateTime().getDayOfMonth());
               boolean excess = false;
               for (Map.Entry<LocalDate, Boolean> entry : dateWithExcess.entrySet()) {
                   if (entry.getKey().equals(date)) {
                       excess = entry.getValue();
                   }
               }
               UserMealWithExcess userMealWithExcess = new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
               filteredList.add(userMealWithExcess);
           }
            }
        return filteredList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
