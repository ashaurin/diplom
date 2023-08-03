package com.github.ashaurin.diplom.util;

import lombok.experimental.UtilityClass;
import com.github.ashaurin.diplom.model.Role;
import com.github.ashaurin.diplom.model.User;
import com.github.ashaurin.diplom.to.UserTo;

@UtilityClass
public class UsersUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}