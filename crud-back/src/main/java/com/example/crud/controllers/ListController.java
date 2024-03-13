package com.example.crud.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.dtos.ListRecordDTO;
import com.example.crud.models.ListModel;
import com.example.crud.repositories.ListRepository;
import com.example.crud.services.ListService;

import jakarta.validation.Valid;

@RestController
public class ListController {
    @Autowired
    ListRepository listRepository;
    @Autowired
    ListService listService;

    @GetMapping("/lists")
    public ResponseEntity<List<ListModel>> getAllLists() {
        return listService.getAllLists();
    }

    @GetMapping("/lists/{id}")
    public ResponseEntity<Object> getOneList(@PathVariable(value="id") UUID id) {
        return listService.getOneList(id);
    }

    @PostMapping("/lists")
    public ResponseEntity<?> saveList(@RequestBody @Valid ListRecordDTO listRecordDTO) {
        return listService.saveList(listRecordDTO);
    }

    @PutMapping("/lists/{id}")
    public ResponseEntity<Object> updateList(@PathVariable(value="id") UUID id, @RequestBody @Valid ListRecordDTO listRecordDTO) {
        return listService.updateList(id, listRecordDTO);
    }

    @DeleteMapping("/lists/{id}")
    public ResponseEntity<Object> deleteList(@PathVariable(value="id") UUID id) {
        return listService.deleteList(id);
    }
}
