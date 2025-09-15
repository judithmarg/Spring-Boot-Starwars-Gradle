package com.task.starwars.controller;

import com.task.starwars.dto.PadawanDto;
import com.task.starwars.dto.PadawanUpdateDto;
import com.task.starwars.service.PadawanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/padawans")
public class PadawanController {
    private PadawanService padawanService;

    public PadawanController(PadawanService pService) {
        this.padawanService = pService;
    }

    @Operation(
            summary = "Obtener la lista de maestros",
            description = "Devuelve la lista de maestros"
    )
    @ApiResponse(responseCode = "200", description = "Lista de maestros jedi")
    @GetMapping
    public List<PadawanDto> getAllPadawans() {
        return this.padawanService.getAllPadawans();
    }

    @Operation(
            summary = "Obtener un maestro",
            description = "Devuelve el maestro dado el id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Padawan con id encontrado"),
            @ApiResponse(responseCode = "404", description = "Padawan jedi no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PadawanDto> getPadawanById(@Parameter(description = "ID de padawan", example = "100")
            @PathVariable int id) {
        return ResponseEntity.ok(this.padawanService.getPadawanById(id));
    }

    @Operation(
            summary = "Registrar un Padawan",
            description = "Devuelve el Padawan registrado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Padawan registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "No se cumplen los parametros de solicitud")
    })
    @PostMapping
    public ResponseEntity<PadawanDto> createPadawan(
            @Parameter(description = "Padawan a registrar", example = "{ \"id\": 2, \"name\": \"Obi-Wan\", " +
                    "\"totalMissions\": 55, \"speciality\": \"lightsaber\" }")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de padawan en formato JSON",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "Ejemplo 1",
                                            summary = "Actualizar rango",
                                            value =  "{ \"id\": 200, \"name\": \"Jok\", " +
                                                    "\"totalMissions\": 3, \"rank\": \"apprentice\" }"
                                    ),
                                    @ExampleObject(name = "Ejemplo 2",
                                            summary = "Actualizar numero de misiones",
                                            value =  "{ \"id\": 206, \"name\": \"Suki-Wan\", " +
                                                    "\"totalMissions\": 2, \"rank\": \"knight\" }"
                                    )
                            }
                    )
            )
            @RequestBody PadawanDto padawan) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.padawanService.createPadawan(padawan));
    }

    @Operation(
            summary = "Actualizar completamente un Padawan",
            description = "Devuelve el Padawan actualizado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Padawan registrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Padawan jedi no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PadawanDto> updatePadawanEntirely(
            @Parameter(description = "id del Padawan a cambiar", example = "100")
            @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de padawan en formato JSON",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "Ejemplo 1",
                                            summary = "Actualizar todo",
                                            value = "{ \"name\": \"Turem\", " +
                                                    "\"totalMissions\": 12, \"rank\": \"admin\" }"
                                    ),
                                    @ExampleObject(name = "Ejemplo 2",
                                            summary = "Actualizar todo 2",
                                            value = "{ \"name\": \"Spok\", " +
                                                    "\"totalMissions\": 1, \"rank\": \"knight\" }"
                                    )
                            }
                    )
            )
            @RequestBody PadawanUpdateDto padawan) {
        return ResponseEntity.ok(this.padawanService.updatePadawanEntirely(id, padawan));
    }

    @Operation(
            summary = "Actualizar solo un campo de un Padawan",
            description = "Devuelve el Padawan actualizado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Padawan registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "No se cumplen los parametros de solicitud"),
            @ApiResponse(responseCode = "404", description = "Padawan jedi no encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PadawanDto> updatePadawanPartially(
            @Parameter(description = "id del Padawan a actualizar")
            @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de padawan en formato JSON",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "Ejemplo 1",
                                        summary = "Actualizar rango",
                                        value = "{ \"rank\": \"knight\"}"
                                    ),
                                    @ExampleObject(name = "Ejemplo 2",
                                            summary = "Actualizar nombre",
                                            value = "{ \"name\": \"Ezra\"}"
                                    ),
                                    @ExampleObject(name = "Ejemplo 3",
                                        summary = "Actualizar numero de misiones",
                                        value = "{ \"totalMissions\": 12}"
                                    )
                            }
                    )
            )
            @RequestBody PadawanUpdateDto padawan) {
        return ResponseEntity.ok(this.padawanService.updatePadawanPartially(id, padawan));
    }

    @Operation(
            summary = "Eliminar un Padawan",
            description = "Elimina el Padawan dado el id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Padawan eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Padawan jedi no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<PadawanDto> deletePadawan(@Parameter(description = "ID de Padawan", example = "100")
            @PathVariable int id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.padawanService.deletePadawan(id));
    }

    @Operation(
            summary = "Obtener la lista de Padawans con filtros",
            description = "Devuelve la lista de Padawans filtrando la rango"
    )
    @ApiResponse(responseCode = "200", description = "Lista de Padawans jedi filtrados")
    @GetMapping("filter-by/{rank}")
    public ResponseEntity<List<PadawanDto>> filterByRank(
            @Parameter(description = "rango del Padawan jedi", example = "knight")
            @PathVariable String rank) {
        return ResponseEntity.ok(this.padawanService.filterByRank(rank));
    }
}
