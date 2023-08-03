package com.github.ashaurin.diplom.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.ashaurin.diplom.model.Dish;
import com.github.ashaurin.diplom.repository.DishRepository;

@Service
@AllArgsConstructor
public class DishService {
    private final DishRepository dishRepository;

    @Transactional
    public Dish save(Dish dish) {
        return dishRepository.save(dish);
    }
}

