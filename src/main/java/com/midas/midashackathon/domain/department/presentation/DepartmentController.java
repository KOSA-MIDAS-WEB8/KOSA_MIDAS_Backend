package com.midas.midashackathon.domain.department.presentation;

import com.midas.midashackathon.domain.department.presentation.dto.request.DepartmentEditRequest;
import com.midas.midashackathon.domain.department.presentation.dto.response.DepartmentListResponse;
import com.midas.midashackathon.domain.department.presentation.dto.response.MemberListResponse;
import com.midas.midashackathon.domain.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/departments")
@RestController
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public DepartmentListResponse getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addDepartment(@RequestBody @Valid DepartmentEditRequest request) {
        departmentService.createDepartment(request);
    }

    @GetMapping("/{department-code}/users")
    public MemberListResponse getAllMembersOfDepartment(@PathVariable("department-code") String departmentCode) {
        return departmentService.getAllMembersOfDepartment(departmentCode);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{department-code}")
    public void editDepartment(@PathVariable("department-code") String departmentCode, @RequestBody DepartmentEditRequest request) {
        departmentService.editDepartment(departmentCode, request);
    }
}
