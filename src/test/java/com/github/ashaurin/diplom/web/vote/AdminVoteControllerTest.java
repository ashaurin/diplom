package com.github.ashaurin.diplom.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.github.ashaurin.diplom.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.ashaurin.diplom.web.user.UserTestData.ADMIN_MAIL;
import static com.github.ashaurin.diplom.web.vote.AdminVoteController.REST_URL;
import static com.github.ashaurin.diplom.web.vote.VoteTestData.VOTE_MATCHER;
import static com.github.ashaurin.diplom.web.vote.VoteTestData.votes;

public class AdminVoteControllerTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(votes));
    }
}
