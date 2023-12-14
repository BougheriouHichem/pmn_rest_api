package com.example.pmnapirest.Service;

import com.example.pmnapirest.Entity.Task;
import com.example.pmnapirest.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;


    @Override
    @Transactional
    public Task savaTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public List<Task> fetchAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    @Transactional
    public Task getTaskById(Long id) {
        if (taskRepository.findById(id).isPresent()){
            return taskRepository.findById(id).get();
        }
        return null;
    }

    @Override
    @Transactional
    public Task updateTaskById(Long id, Task task) {
        Optional<Task> task1 = taskRepository.findById(id);
        if (task1.isPresent()){
            Task taskToUpdate = task1.get();
            taskToUpdate.setTitle(task.getTitle());
            taskToUpdate.setDescription(task.getDescription());
            taskToUpdate.setCompleted(taskToUpdate.isCompleted());

            return taskRepository.save(taskToUpdate);
        }
        return null;
    }

    @Override
    @Transactional
    public String deleteTaskById(Long id) {
        if (taskRepository.findById(id).isPresent()){
            taskRepository.deleteById(id);
            return "Task deleted successfully";
        }
        return "No such task in database";
    }
}
