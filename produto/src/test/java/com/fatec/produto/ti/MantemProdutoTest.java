package com.fatec.produto.ti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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
	IProdutoRepository produtoRepository;
	@BeforeEach
	public void setup() {
		
		byte[] arquivo1 = null;
		Produto produto1 = new Produto("Eletrobomba para maquina de lavar", "maquina de lavar", 51.66, 12);
		produtoRepository.save(produto1);
		Path path = Paths.get("c:/temp/produto1.jpg");
		try {
			InputStream file = Files.newInputStream(path);
			arquivo1 = file.readAllBytes();
		} catch (Exception e) {
			System.out.println(">>>>>> erro no acesso ao arquivo");
			fail("erro");
		}
		Imagem imagem = new Imagem();
		imagem.setId(produto1.getId()); // associa o id do produto ao id da imagem
		imagem.setNome("produto1.jpg");
		imagem.setCaminho("imagens/" + imagem.getNome());
		imagem.setArquivo(arquivo1);

		imagemRepository.save(imagem);
		System.out.println("quantidade de registros no repositorio imagem=>" + imagemRepository.count());
	}

	@Test
	public void ct01_quando_repositorio_vazio_consulta_retorna_vazio() {
		produtoRepository.deleteAll();
		imagemRepository.deleteAll();
		List<Catalogo> catalogo = produtoServico.consultaCatalogo();
		assertTrue(catalogo.isEmpty());
	}

	@Test
	public void ct02_quando_existem_itens_no_catalogo_consulta_retornar_vazio_false() {
		List<Catalogo> catalogo = produtoServico.consultaCatalogo();
		assertFalse(catalogo.isEmpty());
	}
	@Test
    public void ct03_consulta_descricao_vazia_retorna_vazio() {
		
		List<Catalogo> catalogo = produtoServico.consultaPorDescricao("");
        assertEquals (0, catalogo.size());
        assertTrue(catalogo.isEmpty());
    }
	@Test
    public void ct04_quando_consulta_parcial_retorna_true() {
        List<Catalogo> catalogo1 = produtoServico.consultaPorDescricao("Eletrobomba");
        assertFalse(catalogo1.isEmpty());
    }
	@Test
    public void ct05_quando_consulta_descricao_invalida_retorna_vazio() {
		
        List<Catalogo> catalogo = produtoServico.consultaPorDescricao("Descrição inexistente");
        assertTrue(catalogo.isEmpty());
    }
	 @Test
	    public void testCadastrarProdutoValido() {
		 Produto produto2 = new Produto("Eletrobomba para maquina de lavar", "maquina de lavar", 51.66, 12);
	        Optional<Produto> resultado = produtoServico.cadastrar(produto2);
	        assertTrue(resultado.isPresent());
	    }
}
