package bio.prodesp.sanitizador.config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TenantContext {

    private static final ThreadLocal<String> CURRENT_DATASOURCE = new ThreadLocal<>();

    public static String getCurrentTenant() {
        log.debug("::Datasource selecionado: {}", CURRENT_DATASOURCE.get());
        return CURRENT_DATASOURCE.get();
    }

    public static void setCurrentTenant(String tenant) {
        CURRENT_DATASOURCE.set(tenant);
    }
}
