package com.bingbong.effectivejava.item1;

import java.time.Instant;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

public class StaticFactoryMethodNaming {

    enum Rank {
        JACK, QUEEN, KING
    }

    public static void main(String[] args) {
        Date date = Date.from(Instant.now());
        Set<Rank> set = EnumSet.of(Rank.JACK, Rank.QUEEN, Rank.KING);
        Boolean.valueOf("true");

        // getInstance, newInstance 등등...

    }
}
