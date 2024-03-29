package com.example.crud.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.crud.models.ListModel;

@Repository
public interface ListRepository extends JpaRepository<ListModel, UUID> {
}
