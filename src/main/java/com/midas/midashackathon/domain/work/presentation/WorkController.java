package com.midas.midashackathon.domain.work.presentation;

import com.midas.midashackathon.domain.work.presentation.dto.WorkCommonDto;
import com.midas.midashackathon.domain.work.presentation.dto.request.EditTodoRequest;
import com.midas.midashackathon.domain.work.presentation.dto.request.TodoStatusRequest;
import com.midas.midashackathon.domain.work.presentation.dto.request.WorkStatusRequest;
import com.midas.midashackathon.domain.work.presentation.dto.response.TodoListResponse;
import com.midas.midashackathon.domain.work.presentation.dto.response.WorkStatusResponse;
import com.midas.midashackathon.domain.work.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/works")
@RestController
public class WorkController {
    private final WorkService workService;

    @GetMapping("/todo")
    public TodoListResponse getAllTodo() {
        return workService.getAllTodo();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/todo")
    public void createTodo(@RequestBody @Valid EditTodoRequest request) {
        workService.createTodo(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/todo/{todo-id}")
    public void editTodo(@PathVariable("todo-id") Long todoId, @RequestBody EditTodoRequest request) {
        workService.editTodo(todoId, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/todo/{todo-id}/status")
    public void changeTodoStatus(@PathVariable("todo-id") Long todoId, @RequestBody TodoStatusRequest request) {
        workService.changeTodoStatus(todoId, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/todo/{todo-id}")
    public void deleteTodo(@PathVariable("todo-id") Long todoId) {
        workService.deleteTodo(todoId);
    }

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
