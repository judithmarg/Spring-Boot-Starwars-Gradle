package com.task.starwars.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa data de un padawan jedi para actualizar")
public record PadawanUpdateDto(
        @Schema(description = "nombre del padawan", example = "Solo")
        String name,
        @Schema(description = "numero de misiones", example = "4")
        Integer totalMissions,
        @Schema(description = "rango de padawan", example = "knight")
        String rank
) { }
