package com.github.ashaurin.diplom.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.github.ashaurin.diplom.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish>{

    @Query("SELECT d FROM Dish d WHERE d.date = :date")
    List<Dish> getAllByDate(LocalDate date);

    @Query("SELECT d FROM Dish d WHERE d.id = :id AND d.date = :date")
    Optional<Dish> getByDate(int id, LocalDate date);

}
