package gestao.com.br.gestao_tarefas.Util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeBR {
    private static final ZoneId ZONE_BR = ZoneId.of("America/Sao_Paulo");

    // "2025-10-11 16:10:25"
    private static final DateTimeFormatter DB_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // "11/10/2025 16:10:25" (se quiser visual BR)
    private static final DateTimeFormatter VIEW_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private DateTimeBR() {}

    public static LocalDateTime nowLocal() {
        return LocalDateTime.now(ZONE_BR);
    }

    public static String nowForDb() {
        return nowLocal().format(DB_FORMAT);
    }

    public static String nowForView() {
        return nowLocal().format(VIEW_FORMAT);
    }
}
