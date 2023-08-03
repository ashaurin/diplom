package com.github.ashaurin.diplom.web.vote;

import jakarta.validation.Valid;
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

import static com.github.ashaurin.diplom.util.validation.ValidationUtil.*;


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
        return ResponseEntity.of(repository.get(userId));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("delete vote for user {}", userId);
        service.delete(userId);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Vote vote) {
        checkTime();
        int userId = authUser.id();
        log.info("update vote for user {}", userId);
        vote.setDate(LocalDate.now());
        Vote currentVote = repository.getExisted(userId);
        assureIdConsistent(vote, currentVote.id());
        service.save(userId, vote);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Vote vote) {
        checkTime();
        int userId = authUser.id();
        log.info("create vote for user {}", userId);
        checkNew(vote);
        vote.setDate(LocalDate.now());
        Vote created = service.save(userId, vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{userId}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
