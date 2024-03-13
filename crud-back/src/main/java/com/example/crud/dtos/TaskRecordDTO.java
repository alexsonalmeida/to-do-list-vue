package com.example.crud.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record TaskRecordDTO(UUID id,@NotBlank String title, String description, Boolean finished) {
}
