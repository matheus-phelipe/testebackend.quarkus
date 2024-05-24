package br.com.quarkus.testebackend.service;

import br.com.quarkus.testebackend.model.Pessoa;
import br.com.quarkus.testebackend.model.Tarefa;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TarefaService {

    public List<Tarefa> getAllTarefa() {
        return Tarefa.listAll();
    }

    public Tarefa getTarefaById(Long id) {
        return Tarefa.findById(id);
    }

    @Transactional
    public Tarefa salvarTarefa(Tarefa tarefa) {
        if (tarefa.id == null) {
            tarefa.persist();
        } else {
            tarefa = Tarefa.getEntityManager().merge(tarefa);
        }
        return tarefa;
    }

    @Transactional
    public void deletarTarefa(Long id) {
        Tarefa.deleteById(id);
    }

    @Transactional
    public Tarefa alocarPessoaNaTarefa(Long tarefaId, Long pessoaId) {
        Tarefa tarefa = Tarefa.findById(tarefaId);
        Pessoa pessoa = Pessoa.findById(pessoaId);

        if (tarefa == null || pessoa == null) {
            return null;
        }

        if (!tarefa.getDepartamento().equals(pessoa.getDepartamento())) {
            return null; // Não pode alocar pessoa de outro departamento
        }

        tarefa.setPessoa(pessoa);
        return tarefa;
    }

    @Transactional
    public Tarefa tarefaCompleta(Long taskId) {
        Tarefa tarefa = Tarefa.findById(taskId);
        if (tarefa != null) {
            tarefa.setCompletado(true);
        }
        return tarefa;
    }


}
