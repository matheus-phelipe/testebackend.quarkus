package br.com.quarkus.testebackend.service;

import br.com.quarkus.testebackend.exceptions.ExceptionHandler;
import br.com.quarkus.testebackend.model.Departamento;
import br.com.quarkus.testebackend.model.Pessoa;
import br.com.quarkus.testebackend.model.Tarefa;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DepartamentoService {
    @Transactional
    public Map<String, Map<String, Integer>> listarDepartamentosQuantidade() throws ExceptionHandler {
        Map<String, Map<String, Integer>> departamentosInfo = new HashMap<>();

        List<Departamento> departamentos = Departamento.listAll();

        if(!departamentos.isEmpty()){
            for (Departamento departamento : departamentos) {
                String nomeDepartamento = departamento.nome;
                int quantidadePessoas = departamento.getPessoas().size();
                int quantidadeTarefas = departamento.getTarefas().size();

                Map<String, Integer> infoDepartamento = new HashMap<>();
                infoDepartamento.put("pessoas ", quantidadePessoas);
                infoDepartamento.put("tarefas ", quantidadeTarefas);

                departamentosInfo.put(nomeDepartamento, infoDepartamento);
            }
        }else{
            throw new ExceptionHandler("Departamento está vazio!");
        }

        return departamentosInfo;
    }

    @Transactional
    public boolean alocarDepartamentoEmTarefa(Long tarefaId, Long departamentoId) {
        Tarefa tarefa = Tarefa.findById(tarefaId);
        Departamento departamento = Departamento.findById(departamentoId);

        if (tarefa == null || departamento == null) {
            return false;
        }

        tarefa.setDepartamento(departamento);
        tarefa.persist();
        return true;
    }

    @Transactional
    public boolean alocarDepartamentoEmPessoa(Long pessoaId, Long departamentoId) {
        Pessoa pessoa = Pessoa.findById(pessoaId);
        Departamento departamento = Departamento.findById(departamentoId);

        if (pessoa == null || departamento == null) {
            return false;
        }

        pessoa.setDepartamento(departamento);
        pessoa.persist();
        return true;
    }

    @Transactional
    public Departamento criarDepartamento(Departamento departamento) {
        Departamento.persist(departamento);
        return departamento;
    }
}
