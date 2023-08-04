package com.github.ashaurin.diplom.web.vote;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.github.ashaurin.diplom.model.Vote;
import com.github.ashaurin.diplom.repository.VoteRepository;
import com.github.ashaurin.diplom.util.JsonUtil;
import com.github.ashaurin.diplom.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.ashaurin.diplom.web.user.UserTestData.*;
import static com.github.ashaurin.diplom.web.vote.ProfileVoteController.REST_URL;
import static com.github.ashaurin.diplom.web.vote.VoteTestData.getNew;
import static com.github.ashaurin.diplom.web.vote.VoteTestData.*;

public class ProfileVoteControllerTest extends AbstractControllerTest {

    private static final int checkHour = 11;

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(vote1));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL))
                .andExpect(status().isNoContent());
        assertFalse(voteRepository.getByDate(VOTE1_ID, LocalDate.now()).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(of(now.getYear(), now.getMonth(), now.getDayOfMonth(), checkHour, 0))){

            Vote updated = VoteTestData.getUpdated();

            perform(MockMvcRequestBuilders.put(ProfileVoteController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

            VOTE_MATCHER.assertMatch(voteRepository.getExisted(USER_ID), updated);
        }
        else {
            perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL2)
    void createWithLocation() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(of(now.getYear(), now.getMonth(), now.getDayOfMonth(), checkHour, 0))){
            Vote newVote = getNew();
            ResultActions action = perform(MockMvcRequestBuilders.post(ProfileVoteController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andDo(print());

            Vote created = VOTE_MATCHER.readFromJson(action);
            int newId = created.id();
            newVote.setId(newId);

            VOTE_MATCHER.assertMatch(created, newVote);
            VOTE_MATCHER.assertMatch(voteRepository.getExisted(USER_ID + 2), newVote);

        }
        else {
            perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }
    }

}

