package br.com.quarkus.testebackend;

import br.com.quarkus.testebackend.model.Pessoa;
import br.com.quarkus.testebackend.model.dtos.PessoaDTO;
import br.com.quarkus.testebackend.model.dtos.PessoaHorasTotaisDTO;
import br.com.quarkus.testebackend.service.PessoaService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import javax.inject.Inject;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class PessoaServiceTest {

    @Inject
    PessoaService pessoaService;

    @Test
    @Transactional
    public void testGetAllPersons() throws Exception {
        List<PessoaHorasTotaisDTO> pessoas = pessoaService.retornarHorasTotais();
        assertNotNull(pessoas);
    }

    @Test
    public void testCriarPessoa_Success() {
        Pessoa pessoa = new Pessoa();
        pessoa.nome = "João";

        given()
                .contentType("application/json")
                .body(pessoa)
                .when()
                .post("/pessoas")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .body("nome", is("João"));
    }

    @Test
    public void testCriarPessoa_PessoaJaExiste() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.nome = "Maria";

        // Preenche o banco de dados com a pessoa "Maria"
        pessoaService.salvarPessoa(pessoa);

        given()
                .contentType("application/json")
                .body(pessoa)
                .when()
                .post("/pessoas")
                .then()
                .statusCode(Response.Status.CONFLICT.getStatusCode())
                .body(is("Pessoa já existe no sistema!"));
    }

    @Test
    public void testAtualizarPessoa_Success() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.id = 998L;
        pessoa.nome = "João";

        // Salvando a pessoa inicialmente
        pessoaService.salvarPessoa(pessoa);

        pessoa.nome = "João Atualizado";

        given()
                .contentType("application/json")
                .body(pessoa)
                .when()
                .put("/pessoas/998")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("nome", is("João Atualizado"));
    }

    @Test
    public void testAtualizarPessoa_PessoaJaExiste() throws Exception {
        Pessoa pessoa1 = new Pessoa();
        pessoa1.id = 1000L;
        pessoa1.nome = "Maria";

        Pessoa pessoa2 = new Pessoa();
        pessoa2.nome = "João";

        pessoaService.salvarPessoa(pessoa1);
        pessoaService.salvarPessoa(pessoa2);

        pessoa2.nome = "Maria";  // Tentativa de atualização com um nome existente

        given()
                .contentType("application/json")
                .body(pessoa2)
                .when()
                .put("/pessoas/1000")
                .then()
                .statusCode(Response.Status.CONFLICT.getStatusCode())
                .body(is("Já existe uma pessoa com o nome Maria"));
    }

    @Test
    public void testAtualizarPessoa_NotFound() {
        Pessoa pessoa = new Pessoa();
        pessoa.id = 999L;
        pessoa.nome = "Inexistente";

        given()
                .contentType("application/json")
                .body(pessoa)
                .when()
                .put("/pessoas/999")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode())
                .body(is("Pessoa não encontrada"));
    }

    @Test
    public void testDeletarPessoa_Success() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.nome = "João";
        pessoaService.salvarPessoa(pessoa);

        given()
                .when()
                .delete("/pessoas/" + pessoa.id)
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    public void testDeletarPessoa_PessoaNaoEncontrada() {
        given()
                .when()
                .delete("/pessoas/1999")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode())
                .body(is("Pessoa com ID 1999 não encontrada"));
    }
}
