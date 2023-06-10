package com.fatec.produto.tu;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.produto.model.Produto;
import com.fatec.produto.service.ProdutoServico;
@SpringBootTest
class Req02ConsultarProdutoTests {
	@Autowired
	ProdutoServico servico;

	@Test
	void ct01_consultar_produto_por_id_com_sucesso() {
		//dado que existe um registro cadastrado - LoadDatabase
		//quando consulto por id
		Optional <Produto> produto = servico.consultarPorId("1");
		//entao os detalhes do produto estao disponiveis
		assertTrue(produto.isPresent());
	}

}
