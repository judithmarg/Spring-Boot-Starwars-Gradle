package com.task.starwars.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa un maestro jedi")
public record MasterDto(
        @Schema(description = "id del maestro", example = "12")
        int id,
        @Schema(description = "nombre del maestro", example = "Han")
        String name,
        @Schema(description = "numero de misiones", example = "4")
        int totalMissions,
        @Schema(description = "especialidad", example = "strategy")
        String speciality
) { }
