package com.example.crud.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ListRecordDTO(@NotNull @NotEmpty String title) {
}
