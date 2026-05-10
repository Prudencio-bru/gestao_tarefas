package gestao.com.br.gestao_tarefas.Api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalhes de erro padrão")
public class ApiError {
    @Schema(description = "Código do erro (interno ou de domínio)", example = "PERMISSAO_NAO_ENCONTRADA")
    private String code;

    @Schema(description = "Mensagem amigável para o cliente")
    private String message;

    @Schema(description = "Detalhes técnicos opcionais")
    private String details;

    public ApiError() {}
    public ApiError(String code, String message, String details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }

    // getters/setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}

