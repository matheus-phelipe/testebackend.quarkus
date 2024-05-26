package br.com.quarkus.testebackend.model;

import br.com.quarkus.testebackend.model.dtos.DepartamentoDTO;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Departamento extends PanacheEntity {

    public String nome;

    @OneToMany(mappedBy = "departamento")
    private List<Pessoa> pessoas;

    @OneToMany(mappedBy = "departamento")
    private List<Tarefa> tarefas;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }
}
