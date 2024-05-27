package br.com.quarkus.testebackend.service;

import br.com.quarkus.testebackend.exceptions.ExceptionHandler;
import br.com.quarkus.testebackend.model.Pessoa;
import br.com.quarkus.testebackend.model.Tarefa;
import br.com.quarkus.testebackend.model.dtos.PessoaDTO;
import br.com.quarkus.testebackend.model.dtos.PessoaHorasTotaisDTO;
import br.com.quarkus.testebackend.repository.PessoaRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PessoaService {

    @Inject
    PessoaRepository pessoaRepository;

    public List<PessoaHorasTotaisDTO> retornarHorasTotais() throws Exception {
        List<Pessoa> pessoas = Pessoa.listAll();
        List<PessoaHorasTotaisDTO> pessoasDTO = new ArrayList<>();

        if (pessoas == null || pessoas.isEmpty()) {
            throw new ExceptionHandler("A lista de pessoas está vazia ou nula.");
        }

        for (Pessoa pessoa : pessoas) {
            List<Tarefa> tarefas = pessoa.getTarefas();

            int totalHours = 0;
            for (Tarefa tarefa : tarefas) {
                if (tarefa != null) {
                    totalHours += tarefa.getDuracao();
                }
            }

            if (pessoa.getNome() == null || pessoa.getNome().isEmpty()) {
                throw new ExceptionHandler("Nome da pessoa não foi definido.");
            }

            if (pessoa.getDepartamento() == null) {
                throw new ExceptionHandler("Departamento não foi definido na pessoa: " + pessoa.getNome());
            }

            pessoasDTO.add(new PessoaHorasTotaisDTO(pessoa.getNome(), pessoa.getDepartamento(), totalHours));
        }

        return pessoasDTO;
    }

    public Double buscarPessoasPorNomeEPeriodo(String nome, LocalDate dataInicio, LocalDate dataFim) throws ExceptionHandler {
        // Consultar a pessoa no banco de dados pelo nome
        List<Pessoa> pessoas = Pessoa.find("nome = ?1", nome).list();

        if (pessoas.isEmpty()) {
            throw new ExceptionHandler("Nenhuma pessoa encontrada com o nome especificado.");
        }

        // Lista para armazenar o resultado
        List<PessoaDTO> resultado = new ArrayList<>();

        for (Pessoa pessoa : pessoas) {
            // Calcular a média de horas gastas por tarefa no período especificado
            int totalHoras = 0;
            int totalTarefas = 0;

            // Iterar sobre as tarefas da pessoa
            for (Tarefa tarefa : pessoa.getTarefas()) {
                if (tarefa.getPrazo().isAfter(dataInicio) && tarefa.getPrazo().isBefore(dataFim)) {
                    totalHoras += tarefa.getDuracao();
                    totalTarefas++;
                }
            }

            // Calcular a média de horas gastas por tarefa
            double mediaHorasPorTarefa = totalTarefas > 0 ? (double) totalHoras / totalTarefas : 0.0;

            // Criar objeto PessoaDTO com o nome da pessoa e a média de horas por tarefa
            PessoaDTO pessoaGastosDTO = new PessoaDTO(pessoa.getNome(), mediaHorasPorTarefa);

            // Adicionar o objeto à lista de resultados
            resultado.add(pessoaGastosDTO);
        }

        return resultado.get(0).getMediaHorasPorTarefa();
    }

    @Transactional
    public Pessoa salvarPessoa(Pessoa pessoa) throws Exception {
        if(pessoa.getNome().isEmpty()){
            throw new ExceptionHandler("Nome da pessoa está vazio!");
        }

        if (pessoa.id == null) {
            // Verifique se já existe uma pessoa com o mesmo nome
            Pessoa pessoaExiste = Pessoa.find("nome = :nome", Parameters.with("nome", pessoa.nome)).firstResult();

            if (pessoaExiste != null) {
                throw new ExceptionHandler("Pessoa já existe no sistema!");
            } else {
                pessoa.persist();
                return pessoa;
            }
        } else {
            Pessoa pessoaExiste = Pessoa.findById(pessoa.id);

            if (pessoaExiste == null) {
                pessoa.persist();
                return pessoa;
            } else {
                throw new ExceptionHandler("Pessoa já existe no sistema!");
            }
        }
    }

    @Transactional
    public Pessoa atualizarPessoa(Pessoa pessoa) throws Exception {
        try {
            if (pessoa.id == null) {
                throw new IllegalArgumentException("ID da pessoa é obrigatório");
            }

            Pessoa pessoaExistente = Pessoa.findById(pessoa.id);
            if (pessoaExistente != null) {
                Pessoa pessoaComMesmoNome = Pessoa.find("nome = :nome and id != :id",
                        Parameters.with("nome", pessoa.getNome()).and("id", pessoa.id)).firstResult();

                if (pessoaComMesmoNome != null) {
                    throw new ExceptionHandler("Já existe uma pessoa com o nome " + pessoa.getNome());
                }

                pessoaExistente.setNome(pessoa.getNome());
                pessoaExistente.setDepartamento(pessoa.getDepartamento());
                pessoaExistente.getTarefas().clear();
                if (pessoa.getTarefas() != null) {
                    pessoaExistente.getTarefas().addAll(pessoa.getTarefas());
                }
                for (Tarefa tarefa : pessoaExistente.getTarefas()) {
                    tarefa.setPessoa(pessoaExistente);
                }
                pessoaExistente.persist();
                return pessoaExistente;
            } else {
                throw new ExceptionHandler("Pessoa com o ID: " + pessoa.id + " não encontrada");
            }
        } catch (ExceptionHandler e) {
            throw new ExceptionHandler(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void deletarPessoa(Long id) throws ExceptionHandler {
        boolean deletado = Pessoa.deleteById(id);
        if (!deletado) {
            throw new ExceptionHandler("Pessoa com ID " + id + " não encontrada");
        }
    }
}
