package com.github.ashaurin.diplom.web.vote;

import com.github.ashaurin.diplom.model.Vote;
import com.github.ashaurin.diplom.web.MatcherFactory;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user");

    public static final int VOTE1_ID = 1;

    public static final Vote vote1 = new Vote(VOTE1_ID);

    public static Vote getNew() {
        return new Vote(null);
    }

    public static Vote getUpdated() {
        return new Vote(VOTE1_ID);
    }
}
