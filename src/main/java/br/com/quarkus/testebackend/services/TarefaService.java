package br.com.quarkus.testebackend.services;

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
}
