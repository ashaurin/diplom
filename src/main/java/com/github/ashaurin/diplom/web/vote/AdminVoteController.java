package com.github.ashaurin.diplom.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.ashaurin.diplom.model.Vote;
import com.github.ashaurin.diplom.repository.VoteRepository;
import com.github.ashaurin.diplom.web.AuthUser;

import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminVoteController {

    static final String REST_URL = "/api/admin/votes";

    private final VoteRepository repository;


    @GetMapping
    public List<Vote> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll for user {}", authUser.id());
        return repository.getAll();
    }

}
