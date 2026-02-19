package tech.ada.health;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import tech.ada.dao.TarefaDAO;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheck implements HealthCheck {

    private final TarefaDAO tarefaDAO;

    @Inject
    public ReadinessHealthCheck(TarefaDAO tarefaDAO) {
        this.tarefaDAO = tarefaDAO;
    }

    @Override
    public HealthCheckResponse call() {
        if (true) { // chamada no banco de dados SELECT 1;
            return HealthCheckResponse
                    .named("")
                    .up()
                    .withData("ok", "ok")
                    .build();
        }
        return HealthCheckResponse.down("Banco de dados nao responsendo");
    }

}
