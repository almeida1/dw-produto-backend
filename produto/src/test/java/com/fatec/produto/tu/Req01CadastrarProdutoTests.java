package com.fatec.produto.tu;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.fatec.produto.model.Produto;
import com.fatec.produto.service.IImagemServico;
import com.fatec.produto.service.ImagemServico;

class Req01CadastrarProdutoTests {
	
	@Test
	void ct01_cadastrar_produto_com_sucesso() {
		Produto produto = new Produto("Eletrobomba 110V para Maquina de Lavar e Lava Louças", 51.66, 12);
		assertNotNull(produto);
	}

	@Test
	void ct02_cadastrar_produto_com_descricao_invalida() {
		Produto produto = null;
		try {
			produto = new Produto("", 51.66, 12);
			fail("deveria falhar descricao em branco");
		} catch (Exception e) {
			assertNull(produto);
		}
	}
	
}
