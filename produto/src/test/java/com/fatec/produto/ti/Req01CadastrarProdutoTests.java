package com.fatec.produto.ti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.fatec.produto.model.Produto;
import com.google.gson.Gson;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class Req01CadastrarProdutoTests {
	String urlBase = "/api/v1/produtos/";
	@Autowired
	TestRestTemplate testRestTemplate;
	
	@Test
	void ct01_quando_consulta_por_id_retorna_detalhaes_do_produto() throws Exception {
		// Dado - que id esta cadastrado
		Gson gson = new Gson();
		String id = "1";
		Produto re = new Produto("Eletrobomba 110V para Maquina de Lavar e Lava Louças", 51.66, 12);
		// Quando - o usuario consulta o id 
		ResponseEntity<String> resposta = testRestTemplate.getForEntity(urlBase + id, String.class);
		// Entao - retorna not found assertFalse(ro.isPresent());
		Produto ro = gson.fromJson(resposta.getBody(), Produto.class);
		assertEquals("200 OK", resposta.getStatusCode().toString());
		assertTrue(re.equals(ro));
	}
	@Test
	void ct01_quando_consulta_por_id_nao_cadastrado_retorna_erro() throws Exception {
		// Dado - que id nao esta cadastrado
		String id = "2";
		// Quando - o usuario consulta o id 
		ResponseEntity<String> resposta = testRestTemplate.getForEntity(urlBase + id, String.class);
		// Entao - retorna not found 
		assertEquals("404 NOT_FOUND", resposta.getStatusCode().toString());
		assertEquals ("Id não encontrado.", resposta.getBody());
	}
}
