package com.hari.calendar.frontend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hari.calendar.backend.EnglishToHinduOutput;
import com.hari.calendar.model.GregorianMonth;

@RestController
@RequestMapping("/gregorian")
public class SamvatRestController {

    @RequestMapping(params = { "month", "day", "year" }, method = RequestMethod.GET)
    ResponseEntity<String> getTestCaseResultsByTime(@RequestParam("month") GregorianMonth month,
            @RequestParam("day") int day, @RequestParam("year") long year) {
        EnglishToHinduOutput convertedTithi = new EnglishToHinduOutput(month.name(), day, year);

        return new ResponseEntity<>(convertedTithi.getConverted(), HttpStatus.OK);
    }

}
