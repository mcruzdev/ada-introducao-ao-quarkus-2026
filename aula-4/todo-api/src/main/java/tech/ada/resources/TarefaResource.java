package tech.ada.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.ResponseStatus;
import tech.ada.dto.AtualizarTarefaDTO;
import tech.ada.dto.CriarTarefaDTO;
import tech.ada.model.Tarefa;
import tech.ada.services.TarefaService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Classe que vai ser responsavel por criar tarefas,
 * deletar tarefas, atualizar tarefas...
 * <p>
 * Só cuida de coisas relacionadas a HTTP, n tenho regra de negócio aqui.
 */
@Path(value = "/tarefas")
public class TarefaResource {

    // estado na minha app
    private static final List<Tarefa> LISTA_DE_TAREFAS = new ArrayList<>();

    // se eu tiver replicas eu estou lascado... pq? ...
    private AtomicLong incrementador = new AtomicLong(0);

    @Inject // Injetado pelo Quarkus... Injetado pelo Arc... Injetado pelo Arc CDI
    TarefaService tarefaService;

    @POST
    @ResponseStatus(201)
    public void criarTarefa(CriarTarefaDTO dto) {
        Tarefa novaTarefa = new Tarefa(incrementador.incrementAndGet(), dto.titulo());
        tarefaService.criarTarefa(novaTarefa);
    }

    // Data Transfer Object
    @GET
    // retornar DTO
    public List<Tarefa> listaTarefas(@QueryParam("apenasConcluidas") Boolean concluida) {

        return tarefaService.listarTarefas();
    }

    @PUT
    @Path("/{id}") // @Path("id") -> @PathParam("id")
    public Response atualizarTarefa(@PathParam(value = "id") Long id, AtualizarTarefaDTO dto) {
        for (Tarefa tarefa : LISTA_DE_TAREFAS) {
            if (tarefa.getId().equals(id)) {
                tarefa.atualizarTitulo(dto.titulo());
                return Response.ok(tarefa).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PATCH
    @Path("/{tarefaId}")
    public Response concluirTarefa(@PathParam("tarefaId") Long id) {
        for (Tarefa tarefa : LISTA_DE_TAREFAS) {
            if (tarefa.getId().equals(id)) {
                tarefa.concluir();
                return Response.ok(tarefa).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    public void deletarTodas() {
        LISTA_DE_TAREFAS.clear();
    }

    @DELETE
    @Path("/{id}")
    public Response deletarTarefa(@PathParam("tarefaId") Long id) {
        // deletar aqui
        return Response.noContent().build();
    }

}