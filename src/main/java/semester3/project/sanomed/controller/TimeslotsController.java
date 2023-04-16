package semester3.project.sanomed.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import semester3.project.sanomed.Generated;
import semester3.project.sanomed.business.TimeslotsUseCase;
import semester3.project.sanomed.configuration.security.isauthenticated.IsAuthenticated;
import semester3.project.sanomed.domain.Timeslot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/timeslots")
@AllArgsConstructor
@Generated
public class TimeslotsController {
    private final TimeslotsUseCase timeslotsUseCase;

    @IsAuthenticated
    @GetMapping("/{id}")
    public ResponseEntity<List<Timeslot>> getLegitTimeslotsWeek(@PathVariable(value = "id") final long id, @RequestParam("date")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        return ResponseEntity.ok(timeslotsUseCase.getLegitTimeslots(id, date));
    }

    @IsAuthenticated
    @GetMapping("/day/{id}")
    public ResponseEntity<List<Timeslot>> getLegitTimeslotsDay(@PathVariable(value = "id") final long id, @RequestParam("date")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(timeslotsUseCase.getLegitTimeslotsForADay(id, date));
    }
}
