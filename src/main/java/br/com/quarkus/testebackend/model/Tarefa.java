package br.com.quarkus.testebackend.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Tarefa extends PanacheEntity {

    public String titulo;
    public String descricao;
    public String departamento;
    public int duracao; // duracao em horas
    public boolean completado;

    public LocalDate prazo;

    @ManyToOne
    public Pessoa pessoa;
}
