package br.com.quarkus.testebackend.service;

import br.com.quarkus.testebackend.model.Pessoa;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PessoaService {

    public List<Pessoa> getAllPessoas() {
        return Pessoa.listAll();
    }

    public Pessoa getPessoaById(Long id) {
        return Pessoa.findById(id);
    }

    @Transactional
    public Pessoa salvarPerssoa(Pessoa pessoa) {
        if (pessoa.id == null) {
            pessoa.persist();
        } else {
            pessoa = Pessoa.getEntityManager().merge(pessoa);
        }
        return pessoa;
    }

    @Transactional
    public void deletarPessoa(Long id) {
        Pessoa.deleteById(id);
    }
}
