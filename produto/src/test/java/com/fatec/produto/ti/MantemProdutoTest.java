package com.fatec.produto.ti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.produto.model.Catalogo;
import com.fatec.produto.model.IImagemRepository;
import com.fatec.produto.model.IProdutoRepository;
import com.fatec.produto.model.Imagem;
import com.fatec.produto.model.Produto;
import com.fatec.produto.service.IProdutoServico;

@SpringBootTest
public class MantemProdutoTest {
	@Autowired
	private IProdutoServico produtoServico;
	@Autowired
	IImagemRepository imagemRepository;
	@Autowired
	IProdutoRepository repository;

	public void setup() {
		byte[] arquivo1 = null;
		Produto produto1 = new Produto("Eletrobomba teste 110V para Maquina de Lavar e Lava Louças", "maquina de lavar",
				51.66, 12);
		repository.save(produto1);
		Path path = Paths.get("c:/temp/produto1.jpg");
		try {
			InputStream file = Files.newInputStream(path);
			arquivo1 = file.readAllBytes();
		} catch (Exception e) {
			System.out.println("erro no acesso ao arquivo");
		}
		Imagem imagem = new Imagem();
		imagem.setId(1L); // associa o id do produto ao id da imagem
		imagem.setNome("produto1.jpg");
		imagem.setCaminho("imagens/" + imagem.getNome());
		imagem.setArquivo(arquivo1);

		imagemRepository.save(imagem);
	}

	@Test
	public void testConsultaCatalogoVazio() {
		List<Catalogo> catalogo = produtoServico.consultaCatalogo();
		assertTrue(catalogo.isEmpty());
	}

	@Test
	public void testConsultaCatalogoComItens() {
		setup();
		List<Catalogo> catalogo = produtoServico.consultaCatalogo();
		assertFalse(catalogo.isEmpty());
	}
	@Test
    public void testConsultaPorDescricaoVazia() {
		List<Catalogo> catalogo = produtoServico.consultaPorDescricao("");
        //System.out.println(">>>>>> " + catalogo.get(0).getDescricao());
        assertEquals (0, catalogo.size());
        assertTrue(catalogo.isEmpty());
    }
	@Test
    public void testConsultaPorDescricaoComResultados() {
        // Inserir produtos com descrição correspondente no banco de dados para este cenário
        List<Catalogo> catalogo = produtoServico.consultaPorDescricao("Eletrobomba");
        assertFalse(catalogo.isEmpty());
    }
}
