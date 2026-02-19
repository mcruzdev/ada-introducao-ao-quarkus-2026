package tech.ada.dao;

import tech.ada.model.Tarefa;

import java.util.List;

public interface TarefaDAO {

    void insertTarefa(Tarefa tarefa);

    List<Tarefa> selectAll();

}
