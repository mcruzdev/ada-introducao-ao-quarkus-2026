package tech.ada.dao;

import io.quarkus.logging.Log;
import tech.ada.model.Tarefa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TarefaDAOMap implements TarefaDAO {

    // import java.util.concurrent.ConcurrentHashMap;
    private Map<Long, Tarefa> TAREFAS = new HashMap<>();

    @Override
    public void insertTarefa(Tarefa tarefa) {
        Tarefa tarefaExistente = TAREFAS.get(tarefa.getId());
        if (tarefaExistente != null) {
            throw new IllegalStateException("Duplicado...");
        }
        TAREFAS.put(tarefa.getId(), tarefa);
    }

    @Override
    public List<Tarefa> selectAll() {
        Log.info("Executando em " + this.getClass().getName());
        return TAREFAS.values().stream().toList();
    }
}
