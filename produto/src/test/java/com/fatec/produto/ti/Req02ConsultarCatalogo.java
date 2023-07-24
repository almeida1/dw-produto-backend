package com.fatec.produto.ti;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.produto.model.Catalogo;
import com.fatec.produto.service.ProdutoServico;

@SpringBootTest
class Req02ConsultarCatalogo {
	@Autowired
	ProdutoServico servico;
	@Test
	void test() {
		List<Catalogo> catalogo = servico.consultaCatalogo();
		assertEquals(2,catalogo.size());
	}

}
