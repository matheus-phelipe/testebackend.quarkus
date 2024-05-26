package br.com.quarkus.testebackend.model.dtos;

import br.com.quarkus.testebackend.model.Departamento;
import br.com.quarkus.testebackend.model.Pessoa;
import br.com.quarkus.testebackend.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class DepartamentoDTO {
    private Long id;
    private String nome;
    private List<Tarefa> tarefas;
    private List<Pessoa> pessoas;

    public DepartamentoDTO(Departamento departamento) {
        if (departamento != null) {
            this.nome = departamento.getNome();
            this.id = departamento.id;
            this.tarefas = departamento.getTarefas();
            this.pessoas = departamento.getPessoas();
        } else {
            this.nome = "Departamento Desconhecido";
            this.id = null;
            this.tarefas = new ArrayList<>();
            this.pessoas = new ArrayList<>();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
}
