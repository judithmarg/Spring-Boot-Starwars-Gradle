package com.task.starwars.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Schema(description = "Representa data de un padawan jedi para actualizar")
public record PadawanUpdateDto(
        @Schema(description = "nombre del padawan", example = "Solo")
        @Size(min = 3, max = 20)
        String name,
        @Schema(description = "numero de misiones", example = "4")
        @Min(0)
        Integer totalMissions,
        @Schema(description = "rango de padawan", example = "knight")
        @Size(min = 3, max = 30)
        String rank
) { }
