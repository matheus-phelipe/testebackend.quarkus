package br.com.quarkus.testebackend.model.dtos;

public class PessoaDTO {
    private Long id;
    private String nome;
    private String departamento;
    private int totalHorasTarefas;

    public PessoaDTO(String nome, String departamento, int totalHours) {
        this.nome = nome;
        this.departamento = departamento;
        this.totalHorasTarefas = totalHours;
    }
}
