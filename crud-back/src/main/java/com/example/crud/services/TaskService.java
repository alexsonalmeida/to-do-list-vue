package com.example.crud.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.crud.dtos.TaskRecordDTO;
import com.example.crud.models.ListModel;
import com.example.crud.models.TaskModel;
import com.example.crud.repositories.ListRepository;
import com.example.crud.repositories.TaskRepository;

import jakarta.validation.Valid;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ListRepository listRepository;

    public ResponseEntity<Object> getTasksByList( UUID listId) {
        List<TaskModel> listTasks = taskRepository.findAll();
        List<TaskModel> listTasksReturned = new ArrayList<>();
        for (TaskModel task : listTasks) {
            if (task.getList().getId().equals(listId)) {
                listTasksReturned.add(task);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(listTasksReturned);
    }

    public ResponseEntity<?> saveTask(UUID listId, TaskRecordDTO taskRecordDTO) {
        Optional<ListModel> listModelFound = listRepository.findById(listId);
        if (listModelFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não é possível adicionar uma task em uma lista inexistente");
        }
        var taskModel = new TaskModel();
        BeanUtils.copyProperties(taskRecordDTO, taskModel);
        taskModel.setList(listModelFound.get());
        listModelFound.get().getTasks().add(taskModel);
        taskRepository.save(taskModel);
        listRepository.save(listModelFound.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(taskModel);
    }

    public ResponseEntity<Object> uptadteTask( List<TaskRecordDTO> taskRecordDTO) {
        for (var task : taskRecordDTO) {
            Optional<TaskModel> taskModelFound = taskRepository.findById(task.id());
            if (taskModelFound.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task não encontrada");
            }           
            var taskModel = taskModelFound.get();

            if (task.description() != null) {
                taskModel.setDescription(task.description()); 
            }

            if (task.title() != null) {
                taskModel.setTitle(task.title());            
            }
            
            if (task.finished() != null) {
                taskModel.setFinished(task.finished());
            }          
            taskRepository.save(taskModel);   
        }
        return ResponseEntity.status(HttpStatus.OK).body("Listas atualizadas");
    }

    public ResponseEntity<Object> deleteTask(UUID id) {
        Optional<TaskModel> taskModelFound = taskRepository.findById(id);
        if (taskModelFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task não encontrada");
        }
        taskRepository.delete(taskModelFound.get());
        return ResponseEntity.status(HttpStatus.OK).body("Task deletada com sucesso");
    }
}
