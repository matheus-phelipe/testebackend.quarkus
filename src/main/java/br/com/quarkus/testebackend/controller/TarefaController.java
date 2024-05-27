package br.com.quarkus.testebackend.controller;

import br.com.quarkus.testebackend.exceptions.ExceptionHandler;
import br.com.quarkus.testebackend.model.Tarefa;
import br.com.quarkus.testebackend.model.dtos.TarefaDTO;
import br.com.quarkus.testebackend.service.TarefaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/tarefas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TarefaController {
    @Inject
    TarefaService tarefaService;

    @GET
    @Path("/pendentes")
    public Response listarTarefasPendentes() {
        try {
            List<TarefaDTO> tarefasDTO = tarefaService.listarTarefasPendentes();
            return Response.ok(tarefasDTO).build();
        } catch (ExceptionHandler e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar tarefas pendentes: " + e.getMessage())
                    .build();
        }
    }

    @POST
    public Response criarTarefa(Tarefa tarefa) {
        try {
            Tarefa novaTarefa = tarefaService.salvarTarefa(tarefa);
            return Response.status(Response.Status.CREATED).entity(novaTarefa).build();
        } catch (ExceptionHandler e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao criar tarefa: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/alocar/{tarefaId}/{pessoaId}")
    public Response alocarPessoaNaTarefa(@PathParam("tarefaId") Long tarefaId, @PathParam("pessoaId") Long pessoaId) throws Exception {
        boolean sucesso = tarefaService.alocarPessoaNaTarefa(tarefaId, pessoaId);
        if (sucesso) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/finalizar/{taskId}")
    public Response tarefaCompleta(@PathParam("taskId") Long tarefaId) throws Exception {
        String tarefaCompletada = tarefaService.tarefaCompleta(tarefaId);
        if (tarefaCompletada != null) {
            return Response.ok(tarefaCompletada).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
