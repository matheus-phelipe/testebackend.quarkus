package br.com.quarkus.testebackend.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Pessoa extends PanacheEntity {

    public String nome;
    public String departamento;

    @OneToMany(mappedBy = "pessoa")
    public List<Tarefa> tarefas;
}
