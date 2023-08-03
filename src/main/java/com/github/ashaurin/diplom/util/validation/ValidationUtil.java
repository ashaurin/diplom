package com.github.ashaurin.diplom.util.validation;

import lombok.experimental.UtilityClass;
import com.github.ashaurin.diplom.HasId;
import com.github.ashaurin.diplom.error.IllegalRequestDataException;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.of;

@UtilityClass
public class ValidationUtil {

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void checkTime() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 11, 0)) ) {
            throw new IllegalArgumentException("It is too late, vote can't be changed");
        }
    }
}