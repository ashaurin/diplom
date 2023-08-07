package com.github.ashaurin.diplom.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.ashaurin.diplom.model.Dish;
import com.github.ashaurin.diplom.repository.DishRepository;
import com.github.ashaurin.diplom.repository.RestaurantRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Dish update(Dish dish, int id) {
        dish.setRestaurant(dishRepository.getExisted(id).getRestaurant());
        return dishRepository.save(dish);
    }

    @Transactional
    public Dish create(Dish dish, int restaurantId) {
        dish.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return dishRepository.save(dish);
    }

    public static LocalDate convertStringToDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(stringDate, formatter);
    }

    public static String convertDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}

