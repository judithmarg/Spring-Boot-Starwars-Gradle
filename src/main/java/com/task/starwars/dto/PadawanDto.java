package com.task.starwars.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa un padawan jedi")
public record PadawanDto(
        @Schema(description = "id del padawan", example = "102")
        int id,
        @Schema(description = "nombre del maestro", example = "Solaris")
        String name,
        @Schema(description = "numero de misiones", example = "4")
        int totalMissions,
        @Schema(description = "rango del padawan", example = "knight")
        String rank
) { }
