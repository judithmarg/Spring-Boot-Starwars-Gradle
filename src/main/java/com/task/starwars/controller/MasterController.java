package com.task.starwars.controller;

import com.task.starwars.dto.MasterDto;
import com.task.starwars.dto.MasterUpdateDto;
import com.task.starwars.dto.PadawanDto;
import com.task.starwars.service.MasterService;
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
@RequestMapping("/maestros")
public class MasterController {
    private MasterService masterService;

    public MasterController(MasterService mService) {
        this.masterService = mService;
    }

    @Operation(
            summary = "Obtener la lista de maestros",
            description = "Devuelve la lista de maestros"
    )
    @ApiResponse(responseCode = "200", description = "Lista de maestros jedi")
    @GetMapping
    public ResponseEntity<List<MasterDto>> getAllMasters() {
        return ResponseEntity.ok(this.masterService.getAllMasters());
    }

    @Operation(
            summary = "Obtener un maestro",
            description = "Devuelve el maestro dado el id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maestro con id"),
            @ApiResponse(responseCode = "404", description = "Maestro jedi no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MasterDto> getMasterById(@Parameter(description = "ID de maestro", example = "1")
            @PathVariable int id) {
        return ResponseEntity.ok(this.masterService.getMasterById(id));
    }

    @Operation(
            summary = "Registrar un maestro",
            description = "Devuelve el maestro registrado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Maestro registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "No se cumplen los parametros de solicitud")
    })
    @PostMapping
    public ResponseEntity<MasterDto> createMaster(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de maestro en formato JSON",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "Ejemplo 1",
                                            summary = "Actualizar rango",
                                            value = "{  \"id\": 5, \"name\": \"Mar-Wan\", " +
                                                    "\"totalMissions\": 5, \"speciality\": \"lightsaber*2\" }"
                                    ),
                                    @ExampleObject(name = "Ejemplo 2",
                                            summary = "Actualizar numero de misiones",
                                            value =  "{ \"id\": 6, \"name\": \"Obi-Wan\", " +
                                                    "\"totalMissions\": 55, \"speciality\": \"lightsaber\" }"
                                    )
                            }
                    )
            )
            @RequestBody MasterDto Master) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.masterService.createMaster(Master));
    }

    @Operation(
            summary = "Actualizar completamente un maestro",
            description = "Devuelve el maestro actualizado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maestro registrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Maestro jedi no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MasterDto> updateMasterEntirely(
            @Parameter(description = "id del maestro a cambiar", example = "1")
            @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de maestro en formato JSON",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "Ejemplo 1",
                                            summary = "Actualizar todo",
                                            value = "{ \"name\": \"Mar-Wan\", " +
                                                    "\"totalMissions\": 5, \"speciality\": \"lightsaber*2\" }"
                                    ),
                                    @ExampleObject(name = "Ejemplo 2",
                                            summary = "Actualizar todo 2",
                                            value = "{ \"name\": \"Obi-WanII\", " +
                                                    "\"totalMissions\": 50, \"speciality\": \"storm\" }"
                                    )
                            }
                    )
            )
            @RequestBody MasterUpdateDto dto) {
        return ResponseEntity.ok(this.masterService.updateMasterEntirely(id, dto));
    }

    @Operation(
            summary = "Actualizar solo un campo de un maestro",
            description = "Devuelve el maestro actualizado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maestro actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "No se cumplen los parametros de solicitud"),
            @ApiResponse(responseCode = "404", description = "Maestro jedi no encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<MasterDto> updateMasterPartially(
            @Parameter(description = "id del maestro a actualizar")
            @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de maestro en formato JSON",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "Ejemplo 1",
                                            summary = "Actualizar especialidad",
                                            value = "{ \"speciality\": \"agility\"}"
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
            @RequestBody MasterUpdateDto dto) {
        return ResponseEntity.ok(this.masterService.updateMasterPartially(id, dto));
    }

    @Operation(
            summary = "Eliminar un maestro",
            description = "Elimina el maestro dado el id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Maestro eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Maestro jedi no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MasterDto> deleteMaster(@Parameter(description = "ID de maestro", example = "1")
            @PathVariable int id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.masterService.deleteMaster(id));
    }

    @Operation(
            summary = "Asignar un aprendiz a un maestro",
            description = "Asignar un aprendiz a un maestro dado los ids"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Aprendiz registrado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontro el personaje dado el id")
    })
    @PostMapping("/{id}/padawans/{padawanId}")
    public ResponseEntity<String> assignPadawan(
            @Parameter(description = "id del maestro", example = "1")
            @PathVariable int id,
            @Parameter(description = "id del aprendiz", example = "100")
            @PathVariable int padawanId) {
        this.masterService.assignPadawan(id, padawanId);
        return ResponseEntity.status(HttpStatus.CREATED).body("It was assigned succesfully");
    }

    @Operation(
            summary = "Obtener la lista de aprendices",
            description = "Devuelve la lista de aprendices del maestro seleccionado"
    )
    @ApiResponse(responseCode = "200", description = "Lista de aprendices jedi")
    @GetMapping("/{id}/padawans")
    public ResponseEntity<List<PadawanDto>> getOwnPadawans(@Parameter(description="id del maestro jedi", example = "1")
            @PathVariable int id) {
        return ResponseEntity.ok(this.masterService.getOwnPadawans(id));
    }

    @Operation(
            summary = "Obtener la lista de maestros con filtros",
            description = "Devuelve la lista de maestros filtrando la especialidad"
    )
    @ApiResponse(responseCode = "200", description = "Lista de maestros jedi filtrados")
    @GetMapping("/filter-by/{speciality}")
    public ResponseEntity<List<MasterDto>> filterBySpeciality(
            @Parameter(description = "especialidad del maestro jedi", example = "lightsaber")
            @PathVariable String speciality) {
        return ResponseEntity.ok(this.masterService.filterBySpeciality(speciality));
    }
}
