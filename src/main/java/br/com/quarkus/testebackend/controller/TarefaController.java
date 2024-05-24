package br.com.quarkus.testebackend.controller;

import br.com.quarkus.testebackend.model.Tarefa;
import br.com.quarkus.testebackend.service.TarefaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

    @PUT
    @Path("/alocar/{tarefaId}/{pessoaId}")
    public Response alocarPessoaNaTarefa(@PathParam("taskId") Long tarefaId, @PathParam("personId") Long pessoaId) {
        Tarefa tarefaAtualizada = tarefaService.alocarPessoaNaTarefa(tarefaId, pessoaId);
        if (tarefaAtualizada != null) {
            return Response.ok(tarefaAtualizada).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/completar/{taskId}")
    public Response tarefaCompleta(@PathParam("taskId") Long tarefaId) {
        Tarefa tarefaCompletada = tarefaService.tarefaCompleta(tarefaId);
        if (tarefaCompletada != null) {
            return Response.ok(tarefaCompletada).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
