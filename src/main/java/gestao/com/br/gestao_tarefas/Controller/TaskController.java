package gestao.com.br.gestao_tarefas.Controller;


import gestao.com.br.gestao_tarefas.Api.ApiResponse;
import gestao.com.br.gestao_tarefas.Dto.Task.CreateTaskDto;
import gestao.com.br.gestao_tarefas.Service.Task.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks", description = "Gerenciamento de tarefas")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(
            summary = "Criar uma nova tarefa",
            description = "Realiza o cadastro de uma nova tarefa no sistema."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Tarefa criada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "success": true,
                      "message": "Task successfully registered!",
                      "path": "/tasks",
                      "status": 201,
                      "data": {
                        "id": 1,
                        "title": "Estudar Spring Boot",
                        "description": "Criar API REST com JPA",
                        "status": "PENDING",
                        "priority": "HIGH",
                        "dueDate": "2026-05-10"
                      }
                    }
                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "success": false,
                      "message": "Erro de validação",
                      "path": "/tasks",
                      "status": 400,
                      "error": {
                        "field": "title",
                        "message": "title não pode ser vazio"
                      }
                    }
                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado",
                    content = @Content
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "Conflito de dados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<String>> createTask(
            @Valid @RequestBody CreateTaskDto dto,
            HttpServletRequest request
    ) {

        taskService.createTask(dto);

        ApiResponse<String> response = ApiResponse.ok(
                "Task successfully registered!",
                request.getRequestURI(),
                HttpStatus.OK.value()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}