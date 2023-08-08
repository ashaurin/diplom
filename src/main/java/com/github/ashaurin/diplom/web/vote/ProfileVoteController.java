package com.github.ashaurin.diplom.web.vote;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.github.ashaurin.diplom.model.Vote;
import com.github.ashaurin.diplom.repository.VoteRepository;
import com.github.ashaurin.diplom.service.VoteService;
import com.github.ashaurin.diplom.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;

import static com.github.ashaurin.diplom.service.VoteService.checkTime;
import static com.github.ashaurin.diplom.util.validation.ValidationUtil.checkNew;


@RestController
@RequestMapping(value = ProfileVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class ProfileVoteController {

    static final String REST_URL = "/api/profile/vote";

    private final VoteRepository repository;
    private final VoteService service;

    @GetMapping
    public ResponseEntity<Vote> get(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("get vote for user {}", userId);
        return ResponseEntity.of(repository.getByDate(userId, LocalDate.now()));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("delete vote for user {}", userId);
        service.delete(userId, LocalDate.now());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        checkTime();
        int userId = authUser.id();
        log.info("update vote for user {}", userId);
        service.update(userId, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @Schema(hidden = true) @RequestBody Vote vote, @RequestParam int restaurantId) {
        checkTime();
        int userId = authUser.id();
        log.info("create vote for user {}", userId);
        checkNew(vote);
        Vote created = service.create(userId, restaurantId, vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
