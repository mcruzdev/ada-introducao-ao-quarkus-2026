package tech.ada.model;

import java.util.Objects;

public class Tarefa {

    // atributos, estado
    private Long id;
    private String titulo;
    private boolean concluida; // false
    private boolean desabilitada = false;

    // metodos, comportamentos
    public Tarefa(Long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isConcluida() {
        return concluida;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", concluida=" + concluida +
                '}';
    }

    public void atualizarTitulo(String novoTitulo) {
        Objects.requireNonNull(novoTitulo, "O titulo não deve ser nulo");
        this.titulo = novoTitulo;
    }

    public void concluir() {
        this.concluida = true;
    }
}
