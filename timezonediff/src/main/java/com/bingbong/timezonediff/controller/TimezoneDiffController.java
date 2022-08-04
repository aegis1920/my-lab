package com.bingbong.timezonediff.controller;

import com.bingbong.timezonediff.repository.PersonDto;
import com.bingbong.timezonediff.repository.PersonRequest;
import com.bingbong.timezonediff.repository.TimezoneDiffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TimezoneDiffController {

    private final TimezoneDiffRepository timezoneDiffRepository;

    @GetMapping("/all")
    public ResponseEntity<List<PersonDto>> findAll() {
        return ResponseEntity.ok(timezoneDiffRepository.selectAll());
    }

    @GetMapping("/{id}}")
    public ResponseEntity<PersonDto> find(@PathVariable final String id) {
        return ResponseEntity.ok(timezoneDiffRepository.select(id));
    }


    @PostMapping("/application/now")
    public ResponseEntity<Integer> createApplicationNow(@RequestBody PersonRequest personRequest) {
        return ResponseEntity.ok(timezoneDiffRepository.create(PersonDto.ofLocalDateTimeNow(personRequest.getName())));
    }

    @PostMapping("/db/now")
    public ResponseEntity<Integer> createDbNow(@RequestBody PersonRequest personRequest) {
        return ResponseEntity.ok(timezoneDiffRepository.createDbNow(personRequest.getName()));
    }

    @PutMapping("/{id}}")
    public ResponseEntity<Integer> update(@PathVariable final String id, @RequestBody PersonDto personDto) {
        return ResponseEntity.ok(timezoneDiffRepository.update(id, personDto));
    }

    @DeleteMapping("/{id}}")
    public ResponseEntity<String> delete(@PathVariable final String id) {
        return ResponseEntity.ok("id : " + id + " 삭제 성공");
    }

    // insert, update, select 전체 Timezone(OS, Application, DB Session Timezone
}
