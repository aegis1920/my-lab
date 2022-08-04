package com.bingbong.timezonediff.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    private String name;
    private LocalDateTime createDtime;
    private ZonedDateTime createZDtime;
    private Timestamp createTs;

    public PersonDto(final String name) {
        this.name = name;
    }

    public static PersonDto ofLocalDateTimeNow(final String name) {
        return new PersonDto(name, LocalDateTime.now(), ZonedDateTime.now(), Timestamp.from(Instant.now()));
    }

    public static PersonDto ofNone(final String name) {
        return new PersonDto(name);
    }
}
