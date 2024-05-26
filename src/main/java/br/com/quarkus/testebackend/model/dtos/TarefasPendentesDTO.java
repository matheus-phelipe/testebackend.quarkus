package br.com.quarkus.testebackend.model.dtos;

import java.time.LocalDate;

public class TarefasPendentesDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String departamento;
    private int duracao;
    private boolean completado;
    private LocalDate prazo;
}
