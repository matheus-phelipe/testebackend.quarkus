package br.com.quarkus.testebackend.controller;

import br.com.quarkus.testebackend.model.Pessoa;
import br.com.quarkus.testebackend.services.PessoaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/pessoas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaController {

    @Inject
    PessoaService pessoaService;

    @GET
    public List<Pessoa> getAllPessoas() {
        return pessoaService.getAllPessoas();
    }

    @GET
    @Path("/{id}")
    public Pessoa getPessoaById(@PathParam("id") Long id) {
        return pessoaService.getPessoaById(id);
    }

    @POST
    public Pessoa criarPessoa(Pessoa pessoa) {
        return pessoaService.salvarPerssoa(pessoa);
    }

    @PUT
    @Path("/{id}")
    public Pessoa atualizarPessoa(@PathParam("id") Long id, Pessoa pessoa) {
        pessoa.id = id;
        return pessoaService.salvarPerssoa(pessoa);
    }

    @DELETE
    @Path("/{id}")
    public void deletarPessoa(@PathParam("id") Long id) {
        pessoaService.deletarPessoa(id);
    }
}
