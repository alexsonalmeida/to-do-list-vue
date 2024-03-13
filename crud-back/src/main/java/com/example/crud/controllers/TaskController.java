package com.example.crud.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.dtos.TaskRecordDTO;
import com.example.crud.models.ListModel;
import com.example.crud.models.TaskModel;
import com.example.crud.repositories.ListRepository;
import com.example.crud.repositories.TaskRepository;
import com.example.crud.services.TaskService;

import jakarta.validation.Valid;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("tasks/{listId}")
    public ResponseEntity<Object> getTasksByList(@PathVariable(value="listId") UUID listId) {
        return taskService.getTasksByList(listId);
    }

    @PostMapping("tasks/{listId}")
    public ResponseEntity<?> saveTask(@PathVariable(value="listId") UUID listId, @RequestBody @Valid TaskRecordDTO taskRecordDTO) {
        return taskService.saveTask(listId, taskRecordDTO);
    }

    @PutMapping("tasks")
    public ResponseEntity<Object> uptadteTask(@RequestBody List<TaskRecordDTO> taskRecordDTO) {
        return taskService.uptadteTask(taskRecordDTO);
    }

    @DeleteMapping("tasks/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable(value="id") UUID id) {
        return taskService.deleteTask(id);
    }
}
