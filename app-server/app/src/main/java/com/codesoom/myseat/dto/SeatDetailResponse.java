package com.codesoom.myseat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 좌석 상세 조회 응답 정보
 */
@Getter
@Builder
@AllArgsConstructor
public class SeatDetailResponse {
    private int number;

    private String name;

    private boolean mySeat;
}
