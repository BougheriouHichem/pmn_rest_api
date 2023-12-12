package com.example.pmnapirest.Service;

import com.example.pmnapirest.Entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TaskService {
    Task savaTask(Task task);

    List<Task> fetchAllTasks();

    Task getTaskById(Long id);

    Task updateTaskById(Long id, Task task);

    String deleteTaskById(Long id);
}
