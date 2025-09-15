package com.task.starwars.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Representa un maestro jedi")
public record MasterDto(
        @Schema(description = "id del maestro", example = "12")
        @NotNull(message = "Id no puede ser null")
        int id,
        @Schema(description = "nombre del maestro", example = "Han")
        @NotEmpty(message = "Name no puede estar vacio")
        String name,
        @Schema(description = "numero de misiones", example = "4")
        @Min(0)
        int totalMissions,
        @Schema(description = "especialidad", example = "strategy")
        @NotEmpty(message = "Name no puede estar vacio")
        @Size(min = 3, max = 30)
        String speciality
) { }
