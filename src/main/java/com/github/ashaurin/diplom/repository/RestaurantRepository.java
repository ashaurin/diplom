package com.github.ashaurin.diplom.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.github.ashaurin.diplom.model.Restaurant;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant>{

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.dishes")
    List<Restaurant> getAll();

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.dishes WHERE r.id = :id")
    Optional<Restaurant> get(int id);

}
