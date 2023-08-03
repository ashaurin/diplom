package com.github.ashaurin.diplom.repository;

import com.github.ashaurin.diplom.error.DataConflictException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.github.ashaurin.diplom.model.Dish;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish>{

    @Query("SELECT d FROM Dish d WHERE d.date = current_date ORDER BY d.id DESC")
    List<Dish> getAll();

    @Query("SELECT d FROM Dish d WHERE d.id = :id AND d.date = current_date")
    Optional<Dish> get(int id);

}
