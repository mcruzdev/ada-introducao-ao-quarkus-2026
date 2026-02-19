package tech.ada;

import io.quarkus.runtime.Startup;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import tech.ada.config.TarefaDAOCOnfig;
import tech.ada.dao.TarefaDAO;
import tech.ada.dao.TarefaDAOList;
import tech.ada.dao.TarefaDAOMap;

@ApplicationScoped
public class TarefaDAOProducer {

//    @ConfigProperty(name = "tipo.tarefa-dao", defaultValue = "map")
//    String tipo;
    @Inject
    TarefaDAOCOnfig config;

    @Produces
    public TarefaDAO tarefaDAO() {
        if (config.tipoDao().equals("list")) {
            return new TarefaDAOList();
        }
        return new TarefaDAOMap();
    }

    // Identifier(list) e Default
//    @Produces
//    @Identifier("list")
//    @Default
//    public TarefaDAO tarefaDaoList() {
//        return new TarefaDAOList();
//    }
//
//    @Produces
//    @Identifier("map")
//    @Startup
//    public TarefaDAO TarefaDAOMap() {
//        return new TarefaDAOMap();
//    }
}
