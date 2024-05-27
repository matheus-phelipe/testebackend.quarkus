package br.com.quarkus.testebackend.service;

import br.com.quarkus.testebackend.exceptions.ExceptionHandler;
import br.com.quarkus.testebackend.model.Pessoa;
import br.com.quarkus.testebackend.model.Tarefa;

import br.com.quarkus.testebackend.model.dtos.TarefaDTO;
import br.com.quarkus.testebackend.repository.TarefaRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class TarefaService {

    @Inject
    TarefaRepository tarefaRepository;


    public List<TarefaDTO> listarTarefasPendentes() throws ExceptionHandler {
        // Consultar as tarefas que não têm uma pessoa alocada e que não foram completadas
        List<Tarefa> tarefasPendentesSemPrazo = Tarefa.find("pessoa is null and completado = false and prazo is null").list();

        // Consultar as tarefas que não têm uma pessoa alocada, não foram completadas e têm prazo
        List<Tarefa> tarefasPendentesComPrazo = Tarefa.find("pessoa is null and completado = false and prazo is not null").list();

        List<TarefaDTO> tarefasDTOSemPrazo = new ArrayList<>();

        // Se não houver tarefas com prazo, retornar as tarefas sem prazo
        if (tarefasPendentesComPrazo.isEmpty()) {
            if(tarefasPendentesSemPrazo.isEmpty()){
                throw new ExceptionHandler("Lista de tarefas vazia!");
            }
            for (Tarefa tarefa : tarefasPendentesSemPrazo) {
                TarefaDTO tarefaDTO = new TarefaDTO();
                tarefaDTO.setId(tarefa.id);
                tarefaDTO.setTitulo(tarefa.getTitulo());
                tarefaDTO.setCompletado(tarefa.isCompletado());
                tarefaDTO.setDescricao(tarefa.getDescricao());
                tarefaDTO.setPrazo(tarefa.getPrazo());
                if(tarefa.getDepartamento() != null){
                    tarefaDTO.setDepartamento(tarefa.getDepartamento().getNome());
                }
                tarefasDTOSemPrazo.add(tarefaDTO);
            }
            int numTarefasSelecionadasSem = Math.min(3, tarefasDTOSemPrazo.size());
            return tarefasDTOSemPrazo.subList(0, numTarefasSelecionadasSem);
        }

        // Caso contrário, ordenar as tarefas com prazo pelo prazo (do mais antigo para o mais recente)
        tarefasPendentesComPrazo.sort(Comparator.comparing(Tarefa::getPrazo));

        List<TarefaDTO> tarefasDTO = new ArrayList<>();

        // Preencher os DTOs das tarefas
        for (Tarefa tarefa : tarefasPendentesComPrazo) {
            TarefaDTO tarefaDTO = new TarefaDTO();
            tarefaDTO.setId(tarefa.id);
            tarefaDTO.setTitulo(tarefa.getTitulo());
            tarefaDTO.setCompletado(tarefa.isCompletado());
            tarefaDTO.setDescricao(tarefa.getDescricao());
            tarefaDTO.setPrazo(tarefa.getPrazo());
            if(tarefa.getDepartamento() != null){
                tarefaDTO.setDepartamento(tarefa.getDepartamento().getNome());
            }
            tarefasDTO.add(tarefaDTO);
        }
        // Selecionar as três primeiras tarefas (as mais antigas) com prazo
        int numTarefasSelecionadas = Math.min(3, tarefasDTO.size());

        return tarefasDTO.subList(0, numTarefasSelecionadas);
    }

    @Transactional
    public Tarefa salvarTarefa(Tarefa tarefa) throws ExceptionHandler {
        // Verifique se já existe uma tarefa com o mesmo nome
        Tarefa tarefaExistente = Tarefa.find("titulo = :titulo", Parameters.with("titulo", tarefa.getTitulo())).firstResult();

        if (tarefaExistente != null) {
            throw new ExceptionHandler("Já existe uma tarefa com o titulo: '" + tarefa.getTitulo() + "'");
        } else {
            tarefa.persist();
            return tarefa;
        }
    }

    @Transactional
    public boolean alocarPessoaNaTarefa(Long tarefaId, Long pessoaId) throws Exception {
        Tarefa tarefa = Tarefa.findById(tarefaId);
        Pessoa pessoa = Pessoa.findById(pessoaId);

        if (tarefa == null) {
            throw new ExceptionHandler("Tarefa não encontrada!");
        }else if(pessoa == null){
            throw new ExceptionHandler("Pessoa não encontrada!");
        }

        // Verificar se os departamentos são iguais, considerando os casos de nulo
        if ((tarefa.getDepartamento() == null && pessoa.getDepartamento() == null) ||
                (tarefa.getDepartamento() != null && pessoa.getDepartamento() != null && tarefa.getDepartamento().equals(pessoa.getDepartamento()))) {
            tarefa.setPessoa(pessoa);
            Tarefa.persist(tarefa);

            pessoa.getTarefas().add(tarefa);
            pessoa.persist();

            return true;
        } else if (tarefa.getDepartamento() == null) {
            throw new ExceptionHandler("Departamento não definido em tarefa!");
        } else if (pessoa.getDepartamento() == null){
            throw new ExceptionHandler("Departamento não definido em pessoa!");
        } else {
            throw new ExceptionHandler("Não autorizado, departamentos diferentes!");
        }
    }

    @Transactional
    public String tarefaCompleta(Long taskId) throws Exception {
        Tarefa tarefa = Tarefa.findById(taskId);
        if (tarefa != null) {
            tarefa.setCompletado(true);
            tarefa.persist();
            return "Tarefa completada!";
        } else {
            throw new Exception("Tarefa não encontrada!");
        }
    }

}
