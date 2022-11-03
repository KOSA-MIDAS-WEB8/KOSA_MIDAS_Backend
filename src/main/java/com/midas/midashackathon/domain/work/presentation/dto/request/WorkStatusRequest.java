package com.midas.midashackathon.domain.work.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class WorkStatusRequest {
    @NotNull
    @Pattern(regexp = "(START)|(FINISH)", message = "START 또는 FINISH만 입력할 수 있습니다")
    private String status;
}
