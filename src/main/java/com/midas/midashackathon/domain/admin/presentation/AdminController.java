package com.midas.midashackathon.domain.admin.presentation;

import com.midas.midashackathon.domain.admin.presentation.dto.request.UserEditRequest;
import com.midas.midashackathon.domain.admin.presentation.dto.response.UserListResponse;
import com.midas.midashackathon.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/users")
    public UserListResponse getAllUser() {
        return adminService.getAllUser();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/users/{user-id}")
    public void modifyUser(@PathVariable("user-id") Long userId, @RequestBody UserEditRequest request) {
        adminService.modifyUser(userId, request);
    }

}
