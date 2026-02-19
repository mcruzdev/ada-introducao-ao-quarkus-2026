package tech.ada.dao;

import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import tech.ada.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

// -> uma instancia dessa classe em toda a minha app
// ele é apressado... assim que a app inicializa eu tenho o bean pronto apra uso
public class TarefaDAOList implements TarefaDAO {

    private static final List<Tarefa> LISTA_DE_TAREFAS = new ArrayList<>();

    @Override
    public void insertTarefa(Tarefa tarefa) {
        LISTA_DE_TAREFAS.add(tarefa);
    }

    @Override
    public List<Tarefa> selectAll() {
        Log.info("Executando em " + this.getClass().getName());
        return LISTA_DE_TAREFAS;
    }
}
