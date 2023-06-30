package com.DDD.controller;

import com.DDD.dto.BookingDTO;
import com.DDD.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/newTicket")
    public ResponseEntity<Boolean> bookTicket(@RequestBody Map<String, String> data) {
        String exhibitNo = data.get("exhibitNo");
        String id = data.get("id");
        String bookingDate = data.get("bookingDate");
        String visitDate = data.get("visitDate");
        boolean result = bookingService.bookTicket(exhibitNo, id, bookingDate, visitDate);
        if(result) {
            return new ResponseEntity(true, HttpStatus.OK);
        } else {
            return new ResponseEntity(false, HttpStatus.BAD_GATEWAY);
        }

    }

    @GetMapping("/checkTicket")
    public ResponseEntity<List<BookingDTO>> getBookedTicketList(String id) {
        List<BookingDTO> list = bookingService.FindTicket(id);
        return  new ResponseEntity<>(list,HttpStatus.OK);
    }
}
