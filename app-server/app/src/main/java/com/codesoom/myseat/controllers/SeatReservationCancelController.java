package com.codesoom.myseat.controllers;

import com.codesoom.myseat.domain.Seat;
import com.codesoom.myseat.domain.User;
import com.codesoom.myseat.security.UserAuthentication;
import com.codesoom.myseat.services.SeatReservationCancelService;
import com.codesoom.myseat.services.SeatService;
import com.codesoom.myseat.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 좌석 예약 취소 컨트롤러
 */
@RestController
@RequestMapping("/seat-reservation")
@CrossOrigin
@Slf4j
public class SeatReservationCancelController {
    private final SeatReservationCancelService cancelService;
    private final UserService userService;
    private final SeatService seatService;

    public SeatReservationCancelController(
            SeatReservationCancelService cancelService,
            UserService userService, 
            SeatService seatService
    ) {
        this.cancelService = cancelService;
        this.userService = userService;
        this.seatService = seatService;
    }

    /**
     * 좌석 예약을 취소한 후 상태코드 200을 응답한다.
     *
     * @param number 예약 취소할 좌석 번호
     */
    @DeleteMapping("{number}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public void cancelReservation(
            @PathVariable int number
    ) {
        String email = ((UserAuthentication) SecurityContextHolder.getContext().getAuthentication()).getEmail();
        User user = userService.findUser(email);
        Seat seat = seatService.findSeat(number);

        cancelService.cancelReservation(user, seat);
    }
}
