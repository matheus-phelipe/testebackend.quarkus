package br.com.quarkus.testebackend.service;

import br.com.quarkus.testebackend.model.Pessoa;
import br.com.quarkus.testebackend.model.Tarefa;
import br.com.quarkus.testebackend.model.dtos.PessoaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public List<PessoaDTO> listPersons() {
        List<Pessoa> pessoas = Pessoa.listAll();
        List<PessoaDTO> pessoasDTO = new ArrayList<>();

        for (Pessoa pessoa : pessoas) {
            List<Tarefa> tarefas = pessoa.getTarefas();
            int totalHours = 0;
            for (Tarefa tarefa : tarefas) {
                totalHours += tarefa.getDuracao();
            }
            pessoasDTO.add(new PessoaDTO(pessoa.getNome(), pessoa.getDepartamento(), totalHours));
        }
        return pessoasDTO;
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
