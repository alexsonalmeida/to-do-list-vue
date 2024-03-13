package com.example.crud.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.crud.dtos.ListRecordDTO;
import com.example.crud.models.ListModel;
import com.example.crud.repositories.ListRepository;

import jakarta.validation.Valid;

@Service
public class ListService {
    @Autowired
    ListRepository listRepository;

    public ResponseEntity<List<ListModel>> getAllLists() {
        return ResponseEntity.status(HttpStatus.OK).body(listRepository.findAll());
    }

    public ResponseEntity<Object> getOneList(UUID id) {
        Optional<ListModel> listModel = listRepository.findById(id);
        if (listModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listModel.get());
    }

    public ResponseEntity<?> saveList(ListRecordDTO listRecordDTO) {
        try {
            var listModel = new ListModel();
            BeanUtils.copyProperties(listRecordDTO, listModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(listRepository.save(listModel));            
        } catch(DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Não é possível criar duas listas com o mesmo nome");
        }
    }

    public ResponseEntity<Object> updateList(UUID id, ListRecordDTO listRecordDTO) {
        Optional<ListModel> listModelFound = listRepository.findById(id);
        if (listModelFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista não encontrada");
        }
        try {
            var listModel = listModelFound.get();
            BeanUtils.copyProperties(listRecordDTO, listModel);
            return ResponseEntity.status(HttpStatus.OK).body(listRepository.save(listModel));            
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Não é possível criar duas listas com o mesmo nome");
        }
    }

    public ResponseEntity<Object> deleteList( UUID id) {
        Optional<ListModel> listModelFound = listRepository.findById(id);
        if (listModelFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista não encontrada");
        }
        listRepository.delete(listModelFound.get());
        return ResponseEntity.status(HttpStatus.OK).body("Lista deletada com sucesso");
    }
}
