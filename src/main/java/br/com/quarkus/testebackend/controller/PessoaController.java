package br.com.quarkus.testebackend.controller;

import br.com.quarkus.testebackend.exceptions.ExceptionHandler;
import br.com.quarkus.testebackend.model.Pessoa;
import br.com.quarkus.testebackend.model.dtos.PessoaHorasTotaisDTO;
import br.com.quarkus.testebackend.service.PessoaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;

@Path("/pessoas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaController {

    @Inject
    PessoaService pessoaService;

    @GET
    public Response retornarHorasTotais() {
        try {
            List<PessoaHorasTotaisDTO> pessoasDTO = pessoaService.retornarHorasTotais();
            return Response.ok(pessoasDTO).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao retornar horas totais: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/gastos")
    public Response buscarPessoasPorNomeEPeriodo(
            @QueryParam("nome") String nome,
            @QueryParam("dataInicio") String dataInicio,
            @QueryParam("dataFim") String dataFim) {

        try {
            LocalDate dataInicioParsed = LocalDate.parse(dataInicio);
            LocalDate dataFimParsed = LocalDate.parse(dataFim);

            Double gastos = pessoaService.buscarPessoasPorNomeEPeriodo(nome, dataInicioParsed, dataFimParsed);

            return Response.ok(gastos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao processar a solicitação: " + e.getMessage()).build();
        }
    }

    @POST
    public Response criarPessoa(Pessoa pessoa) {
        try {
            Pessoa novaPessoa = pessoaService.salvarPessoa(pessoa);
            return Response.status(Response.Status.CREATED).entity(novaPessoa).build();
        } catch (ExceptionHandler e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao criar pessoa").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizarPessoa(@PathParam("id") Long id, Pessoa pessoa) {
        try {
            pessoa.id = id;
            Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(pessoa);
            return Response.ok(pessoaAtualizada).build();
        } catch (ExceptionHandler e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao atualizar pessoa").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarPessoa(@PathParam("id") Long id) {
        try {
            pessoaService.deletarPessoa(id);
            return Response.status(Response.Status.OK).build();
        } catch (ExceptionHandler e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao deletar pessoa").build();
        }
    }
}
