package gestao.com.br.gestao_tarefas.Api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Envelope padrão de respostas da API")
public class ApiResponse<T> {

    @Schema(description = "Indica se a operação foi bem-sucedida")
    private boolean success;

    @Schema(description = "Dados retornados quando success=true")
    private T data;

    @Schema(description = "Informações de erro quando success=false")
    private ApiError error;

    @Schema(description = "Timestamp ISO-8601 do processamento")
    private Instant timestamp;

    @Schema(description = "Path do endpoint chamado")
    private String path;

    @Schema(description = "HTTP status da resposta (ex.: 200, 400, 404, 500)")
    private int status;

    public static <T> ApiResponse<T> ok(T data, String path, int status) {
        ApiResponse<T> r = new ApiResponse<>();
        r.success = true;
        r.data = data;
        r.timestamp = Instant.now();
        r.path = path;
        r.status = status;
        return r;
    }

    public static <T> ApiResponse<T> error(ApiError error, String path, int status) {
        ApiResponse<T> r = new ApiResponse<>();
        r.success = false;
        r.error = error;
        r.timestamp = Instant.now();
        r.path = path;
        r.status = status;
        return r;
    }


    // getters/setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public ApiError getError() { return error; }
    public void setError(ApiError error) { this.error = error; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}

