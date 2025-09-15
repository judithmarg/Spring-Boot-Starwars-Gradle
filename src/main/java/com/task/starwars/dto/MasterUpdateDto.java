package com.task.starwars.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa data un maestro jedi para actualizarse")
public record MasterUpdateDto (
        @Schema(description = "nombre del maestro", example = "Han")
        String name,
        @Schema(description = "numero de misiones", example = "4")
        Integer totalMissions,
        @Schema(description = "especialidad", example = "strategy")
        String speciality
){}
