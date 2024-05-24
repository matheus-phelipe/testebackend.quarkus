package br.com.quarkus.testebackend.repository;

import br.com.quarkus.testebackend.model.Pessoa;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;

public interface PessoaRepository extends PanacheEntityResource<Pessoa, Long> {
}
