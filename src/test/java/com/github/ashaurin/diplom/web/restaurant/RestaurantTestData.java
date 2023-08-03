package com.github.ashaurin.diplom.web.restaurant;

import com.github.ashaurin.diplom.model.Restaurant;
import com.github.ashaurin.diplom.web.MatcherFactory;

import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);

    public static final int RESTAURANT1_ID = 1;
    public static final int NOTFOUND_ID = 100;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Дядя Ваня");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT1_ID + 1, "Суши-роллы");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT1_ID + 2, "Пануччи");

    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3);

    public static Restaurant getNew() {
        return new Restaurant(null,"Невкусно и грустно");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID,"Баба Клава");
    }

}
