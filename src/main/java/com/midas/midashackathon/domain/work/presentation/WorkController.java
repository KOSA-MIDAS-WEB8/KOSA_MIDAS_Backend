package com.midas.midashackathon.domain.work.presentation;

import com.midas.midashackathon.domain.work.presentation.dto.WorkCommonDto;
import com.midas.midashackathon.domain.work.presentation.dto.request.WorkStatusRequest;
import com.midas.midashackathon.domain.work.presentation.dto.response.WorkStatusResponse;
import com.midas.midashackathon.domain.work.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/works")
@RestController
public class WorkController {
    private final WorkService workService;

    @GetMapping("/{date}")
    public WorkStatusResponse getStatus(@PathVariable("date") String date) {
        return workService.getStatus(date);
    }

    @PutMapping("/{date}")
    public void updatePlan(@PathVariable("date") String date, @RequestBody @Valid WorkCommonDto request) {
        workService.updatePlan(date, request);
    }

    @PutMapping("/{date}/actual")
    public void updateActual(@PathVariable("date") String date, @RequestBody @Valid WorkStatusRequest request) {
        workService.updateActual(date, request);
    }
}
