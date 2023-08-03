package com.github.ashaurin.diplom.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.ashaurin.diplom.model.Restaurant;
import com.github.ashaurin.diplom.repository.RestaurantRepository;

@Service
@AllArgsConstructor
public class RestaurantService {

    @Autowired
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
}
