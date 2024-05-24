package br.com.quarkus.testebackend.controller;

import br.com.quarkus.testebackend.model.Tarefa;
import br.com.quarkus.testebackend.service.TarefaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/tarefas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TarefaController {
    @Inject
    TarefaService tarefaService;

    @GET
    public List<Tarefa> getAllTasks() {
        return tarefaService.getAllTarefa();
    }

    @GET
    @Path("/{id}")
    public Tarefa getTarefaById(@PathParam("id") Long id) {
        return tarefaService.getTarefaById(id);
    }

    @POST
    public Tarefa criarTarefa(Tarefa task) {
        return tarefaService.salvarTarefa(task);
    }

    @PUT
    @Path("/{id}")
    public Tarefa atualizarTarefa(@PathParam("id") Long id, Tarefa tarefa) {
        tarefa.id = id;
        return tarefaService.salvarTarefa(tarefa);
    }

    @DELETE
    @Path("/{id}")
    public void deletarTarefa(@PathParam("id") Long id) {
        tarefaService.deletarTarefa(id);
    }
}
