package com.example.pmnapirest.Controller;

import com.example.pmnapirest.Entity.Task;
import com.example.pmnapirest.Service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;



@RestController
@RequestMapping("/tasks")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;
    private static final List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.fetchAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public ResponseEntity<String> addTask(@Valid @RequestBody Task task, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid input");
        }
        taskService.savaTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task added Successefully");
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id,@Valid @RequestBody Task updatedTask,BindingResult result) {
        if (result.hasErrors()) {
            return null;
        }
        return taskService.updateTaskById(id, updatedTask);
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {

        return taskService.deleteTaskById(id);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
