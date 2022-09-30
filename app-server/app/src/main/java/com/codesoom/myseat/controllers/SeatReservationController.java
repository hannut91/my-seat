package com.codesoom.myseat.controllers;

import com.codesoom.myseat.domain.SeatReservation;
import com.codesoom.myseat.domain.User;
import com.codesoom.myseat.dto.SeatReservationRequest;
import com.codesoom.myseat.dto.SeatReservationResponse;
import com.codesoom.myseat.security.UserAuthentication;
import com.codesoom.myseat.services.SeatReservationService;
import com.codesoom.myseat.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 좌석 예약 요청 컨트롤러
 */
@RestController
@RequestMapping("/seat-reservation")
@CrossOrigin
@Slf4j
public class SeatReservationController {
    private final SeatReservationService service;
    private final UserService userService;

    public SeatReservationController(
            SeatReservationService service, 
            UserService userService
    ) {
        this.service = service;
        this.userService = userService;
    }

    /**
     * 좌석을 예약한 후 상태코드 201과 예약 정보를 응답한다.
     *
     * @param number 예약할 좌석 번호
     * @param request 좌석 예약 요청 정보
     * @return 좌석 예약 정보
     */
    @PostMapping("{number}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public SeatReservationResponse addReservation(
            @PathVariable int number,
            @RequestBody SeatReservationRequest request
    ) {
        String email = ((UserAuthentication) SecurityContextHolder.getContext().getAuthentication()).getEmail();
        User user = userService.findUser(email);
        log.info("number: " + number);
        log.info("checkin: " + request.getCheckIn());
        log.info("checkout: " + request.getCheckOut());
        
        return toResponse(service.addReservation(number, request, user));
    }

    /**
     * 응답 정보를 반환한다.
     *
     * @param data 예약한 좌석 정보
     * @return 응답 정보
     */
    private SeatReservationResponse toResponse(
            SeatReservation data
    ) {
        return SeatReservationResponse.builder()
                .name(data.getUser().getName())
                .number(data.getSeat().getNumber())
                .date(data.getDate())
                .checkIn(data.getCheckIn())
                .checkOut(data.getCheckOut())
                .build();
    }
}
