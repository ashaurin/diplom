package com.github.ashaurin.diplom.web.dish;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.github.ashaurin.diplom.model.Dish;
import com.github.ashaurin.diplom.repository.DishRepository;
import com.github.ashaurin.diplom.service.DishService;
import com.github.ashaurin.diplom.web.AuthUser;

import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.github.ashaurin.diplom.util.validation.ValidationUtil.assureIdConsistent;
import static com.github.ashaurin.diplom.util.validation.ValidationUtil.checkNew;
import static com.github.ashaurin.diplom.service.DishService.convertStringToDate;


@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminDishController {

    static final String REST_URL = "/api/admin/dishes";

    private final DishRepository repository;
    private final DishService service;

    @GetMapping("/by-date")
    public List<Dish> getAllByDate(@AuthenticationPrincipal AuthUser authUser, @RequestParam String date) {
        log.info("getAllByDate dishes for user {}", authUser.id());
        return repository.getAllByDate(convertStringToDate(date));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getByDate(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("getByDate dish {} for user {}", id, authUser.id());
        return ResponseEntity.of(repository.getByDate(id, LocalDate.now()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("delete dish {} for user {}", id, authUser.id());
        repository.deleteExisted(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Dish dish, @PathVariable int id) {
        log.info("update dish {} for user {}", id, authUser.id());
        assureIdConsistent(dish, id);
        service.update(dish, id);
    }

    @PostMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        int userId = authUser.id();
        log.info("create {} for user {}", dish, userId);
        checkNew(dish);
        Dish created = service.create(dish, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
