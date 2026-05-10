package gestao.com.br.gestao_tarefas.Exception;

import gestao.com.br.gestao_tarefas.Api.ApiError;
import gestao.com.br.gestao_tarefas.Api.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidFormat(
            HttpMessageNotReadableException ex,
            HttpServletRequest req) {

        ApiError err = new ApiError(
                "FORMATO_INVALIDO",
                "O formato de um ou mais campos no JSON está incorreto.",
                ex.getMostSpecificCause().getMessage()
        );

        ApiResponse<Void> body = ApiResponse.error(
                err,
                req.getRequestURI(),
                HttpStatus.BAD_REQUEST.value()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest req) {

        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Dados inválidos.");

        ApiError err = new ApiError(
                "PARAMETRO_INVALIDO",
                mensagem,
                null
        );

        ApiResponse<Void> body = ApiResponse.error(
                err,
                req.getRequestURI(),
                HttpStatus.BAD_REQUEST.value()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrity(
            DataIntegrityViolationException ex,
            HttpServletRequest req) {

        String mensagem = ex.getRootCause() != null
                ? ex.getRootCause().getMessage()
                : ex.getMessage();

        ApiError err;

        if (mensagem != null && mensagem.contains("TIPO_ENTREGA")) {
            err = new ApiError(
                    "DADO_INVALIDO",
                    "Tipo de entrega inválido.",
                    mensagem
            );
        } else if (mensagem != null && mensagem.contains("CPF_UNIQUE")) {
            err = new ApiError(
                    "DADO_DUPLICADO",
                    "CPF já cadastrado.",
                    mensagem
            );
        } else {
            err = new ApiError(
                    "ERRO_BANCO",
                    "Erro de integridade de dados.",
                    mensagem
            );
        }

        ApiResponse<Void> body = ApiResponse.error(
                err,
                req.getRequestURI(),
                HttpStatus.CONFLICT.value()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(
            Exception ex,
            HttpServletRequest req) {

        ApiError err = new ApiError(
                "ERRO_INTERNO",
                "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                ex.getMessage()
        );

        ApiResponse<Void> body = ApiResponse.error(
                err,
                req.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}




