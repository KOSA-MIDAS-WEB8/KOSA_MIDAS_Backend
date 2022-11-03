package com.midas.midashackathon.domain.work.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkCommonDto {
    @Pattern(regexp = "[0-2][0-9]:[0-5][0-9]", message = "시간은 hh:mm 형식으로 나타나야 합니다")
    private String start;

    @Pattern(regexp = "[0-2][0-9]:[0-5][0-9]", message = "시간은 hh:mm 형식으로 나타나야 합니다")
    private String end;
}
