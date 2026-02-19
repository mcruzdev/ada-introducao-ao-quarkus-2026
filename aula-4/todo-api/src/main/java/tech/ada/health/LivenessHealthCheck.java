package tech.ada.health;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import tech.ada.dao.TarefaDAO;

@Liveness
@ApplicationScoped
public class LivenessHealthCheck implements HealthCheck {

    private final TarefaDAO tarefaDAO;

    @Inject
    public LivenessHealthCheck(TarefaDAO tarefaDAO) {
        this.tarefaDAO = tarefaDAO;
    }

    @Override
    public HealthCheckResponse call() {
        if (false) { // chamada no banco de dados SELECT 1;
            return HealthCheckResponse.up("banco de dados respondendo");
        }
        return HealthCheckResponse.down("liveness: Banco de dados nao responsendo");
    }

}
