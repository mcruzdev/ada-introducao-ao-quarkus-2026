package tech.ada.services;

import io.quarkus.logging.Log;
import io.smallrye.common.annotation.Identifier;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.ada.dao.TarefaDAO;
import tech.ada.dao.TarefaDAOList;
import tech.ada.model.Tarefa;

import java.util.List;

@ApplicationScoped // // -> uma instancia dessa classe em toda a minha app
// lazy ... ele é carregada apenas quando solicitado
public class TarefaService {

    private static final Logger log = LoggerFactory.getLogger(TarefaService.class);

    TarefaDAO dao;

    public TarefaService(@Default TarefaDAO dao) {
        this.dao = dao;
    }

    @PostConstruct
    void init() {
        Log.info("Inicializando agora...");
    }

    public void criarTarefa(Tarefa tarefa) {
        log.info("Recebi uma tarefa: {}", tarefa);
        dao.insertTarefa(tarefa);
    }

    public List<Tarefa> listarTarefas() {
        return dao.selectAll();
    }
}
