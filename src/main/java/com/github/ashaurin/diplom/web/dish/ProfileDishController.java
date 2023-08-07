package com.github.ashaurin.diplom.web.dish;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.github.ashaurin.diplom.model.Dish;
import com.github.ashaurin.diplom.repository.DishRepository;
import com.github.ashaurin.diplom.web.AuthUser;

import java.time.LocalDate;
import java.util.List;

import static com.github.ashaurin.diplom.service.DishService.convertStringToDate;

@RestController
@RequestMapping(value = ProfileDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class ProfileDishController {

    static final String REST_URL = "/api/profile/dishes";
    private final DishRepository repository;

    @GetMapping("/byDate")
    public List<Dish> getAllByDate(@AuthenticationPrincipal AuthUser authUser, @RequestParam String date) {
        log.info("getAllByDate for user {}", authUser.id());
        return repository.getAllByDate(convertStringToDate(date));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getByDate(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("getByDate dish {} for user {}", id, authUser.id());
        return ResponseEntity.of(repository.getByDate(id, LocalDate.now()));
    }

}
