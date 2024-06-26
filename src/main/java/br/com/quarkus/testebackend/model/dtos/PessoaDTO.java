package br.com.quarkus.testebackend.model.dtos;

import br.com.quarkus.testebackend.model.Departamento;
import br.com.quarkus.testebackend.model.Tarefa;

import java.util.List;

public class PessoaDTO {
    private Long id;
    private String nome;
    private String departamento;
    private int totalHorasTarefas;
    private double mediaHorasPorTarefa;
    private List<Tarefa> tarefas;

    public PessoaDTO(Long id, String nome, Departamento departamentoDTO, int totalHours) {
        this.id = id;
        this.nome = nome;
        this.departamento = departamentoDTO.nome;
        this.totalHorasTarefas = totalHours;
    }

    public PessoaDTO(String nome, double mediaHorasPorTarefa) {
        this.nome = nome;
        this.mediaHorasPorTarefa = mediaHorasPorTarefa;
    }

    public double getMediaHorasPorTarefa() {
        return mediaHorasPorTarefa;
    }

    public void setMediaHorasPorTarefa(double mediaHorasPorTarefa) {
        this.mediaHorasPorTarefa = mediaHorasPorTarefa;
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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getTotalHorasTarefas() {
        return totalHorasTarefas;
    }

    public void setTotalHorasTarefas(int totalHorasTarefas) {
        this.totalHorasTarefas = totalHorasTarefas;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }
}
