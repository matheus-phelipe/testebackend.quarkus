package br.com.quarkus.testebackend.controller;

import br.com.quarkus.testebackend.exceptions.ExceptionHandler;
import br.com.quarkus.testebackend.model.Departamento;
import br.com.quarkus.testebackend.service.DepartamentoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/departamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartamentoController {
    @Inject
    DepartamentoService departamentoService;

    @GET
    public Response listarDepartamentosQuantidade() {
        try {
            Map<String, Map<String, Integer>> departamentosInfo = departamentoService.listarDepartamentosQuantidade();
            return Response.ok(departamentosInfo).build();
        } catch (ExceptionHandler e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar departamentos: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Transactional
    public Departamento criarDepartamento(Departamento departamento) throws Exception {
        return departamentoService.criarDepartamento(departamento);
    }

    @PUT
    @Path("/alocar/tarefa/{tarefaId}/departamento/{departamentoId}")
    public boolean alocarDepartamentoEmTarefa(@PathParam("tarefaId") Long tarefaId, @PathParam("departamentoId") Long departamentoId) {
        return departamentoService.alocarDepartamentoEmTarefa(tarefaId, departamentoId);
    }

    @PUT
    @Path("/alocar/pessoa/{pessoaId}/departamento/{departamentoId}")
    public boolean alocarDepartamentoEmPessoa(@PathParam("pessoaId") Long pessoaId, @PathParam("departamentoId") Long departamentoId) {
        return departamentoService.alocarDepartamentoEmPessoa(pessoaId, departamentoId);
    }

}
