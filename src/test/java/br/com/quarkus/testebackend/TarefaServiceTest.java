package br.com.quarkus.testebackend;

import br.com.quarkus.testebackend.exceptions.ExceptionHandler;
import br.com.quarkus.testebackend.model.Tarefa;
import br.com.quarkus.testebackend.service.TarefaService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class TarefaServiceTest {
    @Inject
    TarefaService tarefaService;

    @Test
    public void testCriarTarefa_Success() {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Tarefa 1");

        given()
                .contentType("application/json")
                .body(tarefa)
                .when()
                .post("/tarefas")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .body("nome", is("Tarefa 1"));
    }

    @Test
    public void testCriarTarefa_TarefaJaExiste() throws ExceptionHandler {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Tarefa 1");

        // Salvando a tarefa inicialmente
        tarefaService.salvarTarefa(tarefa);

        given()
                .contentType("application/json")
                .body(tarefa)
                .when()
                .post("/tarefas")
                .then()
                .statusCode(Response.Status.CONFLICT.getStatusCode())
                .body(is("Já existe uma tarefa com o nome Tarefa 1"));
    }
}
