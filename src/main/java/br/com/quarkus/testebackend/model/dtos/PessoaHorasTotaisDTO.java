package br.com.quarkus.testebackend.model.dtos;

import br.com.quarkus.testebackend.model.Departamento;

public class PessoaHorasTotaisDTO {
    private Long id;
    private String nome;
    private String departamento;
    private int totalHorasTarefas;

    public PessoaHorasTotaisDTO(String nome, Departamento departamentoDTO, int totalHours) {
        this.nome = nome;
        this.departamento = departamentoDTO.nome;
        this.totalHorasTarefas = totalHours;
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
}
