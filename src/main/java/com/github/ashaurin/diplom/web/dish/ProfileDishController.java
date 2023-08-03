package com.github.ashaurin.diplom.web.dish;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.ashaurin.diplom.model.Dish;
import com.github.ashaurin.diplom.repository.DishRepository;
import com.github.ashaurin.diplom.web.AuthUser;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = ProfileDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class ProfileDishController {

    static final String REST_URL = "/api/profile/dishes";
    private final DishRepository repository;

    @GetMapping
    public List<Dish> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll for user {}", authUser.id());
        return repository.getAllByDate(LocalDate.now());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get dish {} for user {}", id, authUser.id());
        return ResponseEntity.of(repository.getByDate(id, LocalDate.now()));
    }

}
