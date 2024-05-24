package br.com.quarkus.testebackend.repository;

import br.com.quarkus.testebackend.model.Tarefa;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;

public interface TarefaRepository extends PanacheEntityResource<Tarefa, Long> {
}
