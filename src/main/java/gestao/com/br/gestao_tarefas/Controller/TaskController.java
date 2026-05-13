package gestao.com.br.gestao_tarefas.Controller;


import gestao.com.br.gestao_tarefas.Api.ApiResponse;
import gestao.com.br.gestao_tarefas.Dto.Task.CreateTaskDto;
import gestao.com.br.gestao_tarefas.Dto.Task.ListTaskDto;
import gestao.com.br.gestao_tarefas.Dto.Task.UpdateStatusTaskDto;
import gestao.com.br.gestao_tarefas.Dto.Task.UpdateTaskDto;
import gestao.com.br.gestao_tarefas.Enum.Task.PriorityTaskEnum;
import gestao.com.br.gestao_tarefas.Enum.Task.StatusTaskEnum;
import gestao.com.br.gestao_tarefas.Repository.TaskRepository;
import gestao.com.br.gestao_tarefas.Service.Task.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
                        "due_date": "2026-05-10"
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
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createTask(
            @Valid @RequestBody CreateTaskDto dto,
            HttpServletRequest request
    ) {

        taskService.createTask(dto);

        ApiResponse<String> response = ApiResponse.ok(
                "Task successfully registered!",
                request.getRequestURI(),
                HttpStatus.CREATED.value()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update task")
    @PutMapping("/update")
    public  ResponseEntity<ApiResponse<String>> updateTask(
            @Valid @RequestBody UpdateTaskDto dto,
            HttpServletRequest request
    ) {
        taskService.updateTask(dto);

        ApiResponse<String> response = ApiResponse.ok(
                "Task successfully update!",
                request.getRequestURI(),
                HttpStatus.OK.value()
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "List task")
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<ListTaskDto>>> listTask(
            HttpServletRequest request
    ) {
        List<ListTaskDto> list = taskService.findAll();
        ApiResponse<List<ListTaskDto>> response =
                ApiResponse.ok(list, request.getRequestURI(), HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "List task by id")
    @GetMapping("/listById/{id_task}")
    public ResponseEntity<ApiResponse<ListTaskDto>> findById(
            @PathVariable("id_task") Long id_task,
            HttpServletRequest request)
    {
        ListTaskDto task = taskService.findById(id_task);
        ApiResponse<ListTaskDto> response =
                ApiResponse.ok(task, request.getRequestURI(), HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete task by id")
    @DeleteMapping("/delete/{id_task}")
    public ResponseEntity<ApiResponse<String>> deleteTask(
            @PathVariable("id_task") Long idTask,
            HttpServletRequest request
    ) {
        taskService.delete(idTask);

        ApiResponse<String> response = ApiResponse.ok(
                "Task successfully deleted!",
                request.getRequestURI(),
                HttpStatus.OK.value()
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //Filtro de busca por status das tarefas
    @Operation(summary = "List tasks by status")
    @GetMapping("/listByStatus")
    public ResponseEntity<ApiResponse<List<ListTaskDto>>> listByStatus(
            @RequestParam StatusTaskEnum status,
            HttpServletRequest request
    ) {
        List<ListTaskDto> list = taskService.findByStatus(status);

        ApiResponse<List<ListTaskDto>> response =
                ApiResponse.ok(list, request.getRequestURI(), HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }

    //Filtro de busca por prioridade
    @Operation(summary = "List tasks by priority")
    @GetMapping("/listByPriority")
    public ResponseEntity<ApiResponse<List<ListTaskDto>>> listByPriority(
            @RequestParam PriorityTaskEnum priority,
            HttpServletRequest request
    ) {
        List<ListTaskDto> list = taskService.findByPriority(priority);

        ApiResponse<List<ListTaskDto>> response =
                ApiResponse.ok(list, request.getRequestURI(), HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }

    //Filtro de busca por prazo final da tarefa
    @Operation(summary = "List tasks by Due Date")
    @GetMapping("listByDueDate")
    public ResponseEntity<ApiResponse<List<ListTaskDto>>> listByDueDate(
            @RequestParam("due_date")
            LocalDate due_date,
            HttpServletRequest request
    ) {
        List<ListTaskDto> list = taskService.findByDueDate(due_date);

        ApiResponse<List<ListTaskDto>> response =
                ApiResponse.ok(list, request.getRequestURI(), HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }

    //Ordenação Crescente
    @GetMapping("/order/createdAt/asc")
    public ResponseEntity<ApiResponse<List<ListTaskDto>>> orderByCreatedAtAsc(
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        taskService.listOrderByCreatedAtAsc(),
                        request.getRequestURI(),
                        HttpStatus.OK.value()
                )
        );
    }

    //Ordenação Decrescente
    @GetMapping("/order/createdAt/desc")
    public ResponseEntity<ApiResponse<List<ListTaskDto>>> orderByCreatedAtDesc(
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        taskService.listOrderByCreatedAtDesc(),
                        request.getRequestURI(),
                        HttpStatus.OK.value()
                )
        );
    }

    //Ordenação por Prioridade
    @GetMapping("/order/priority")
    public ResponseEntity<ApiResponse<List<ListTaskDto>>> orderByPriority(
            HttpServletRequest request) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        taskService.listOrderByPriority(),
                        request.getRequestURI(),
                        HttpStatus.OK.value()
                )
        );
    }

    @Operation(summary = "Update task status")
    @PatchMapping("/update-status")
    public ResponseEntity<ApiResponse<String>> updateTaskStatus(
            @Valid @RequestBody UpdateStatusTaskDto dto,
            HttpServletRequest request
    ) {
        taskService.updateStatus(dto);

        ApiResponse<String> response = ApiResponse.ok(
                "Task status updated successfully!",
                request.getRequestURI(),
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(response);
    }
}

