package com.fatec.produto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.produto.model.Catalogo;
import com.fatec.produto.model.IProdutoRepository;
import com.fatec.produto.model.Imagem;
import com.fatec.produto.model.Produto;

@Service
public class ProdutoServico implements IProdutoServico {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	IProdutoRepository produtoRepository;
	@Autowired
	IImagemServico imagemServico;

	@Override
	public List<Catalogo> consultaPorDescricao(String descricao) {
		if (descricao.isBlank() | descricao.isEmpty() ) {
			return new ArrayList<Catalogo>();
		} else {
			List<Produto> produto = produtoRepository.findByDescricaoContaining(descricao);
			List<Catalogo> catalogo = consultaCatalogo();
			List<Catalogo> lista = new ArrayList<>();
			for (Catalogo c : catalogo) {
				for (Produto p : produto) {
					if (c.getId().equals(p.getId())) {
						lista.add(c);
					}
				}

			}
			return lista;
		}
	}

	@Override
	public Optional<Produto> cadastrar(Produto produto) {
		logger.info(">>>>>> servico cadastrar produto iniciado ");
		return Optional.ofNullable(produtoRepository.save(produto));
	}

	@Override
	public Optional<Produto> consultarPorId(String id) {
		logger.info(">>>>>> servico consulta por id chamado");
		long codProduto = Long.parseLong(id);
		return produtoRepository.findById(codProduto);
	}

	@Override
	public Optional<Produto> atualizar(Long id, Produto produto) {
		return Optional.empty();
	}

	@Override
	public void excluir(Long id) {
		// excluir produto pelo id
	}

	/**
	 * associa o id do produto ao id da imagem e adiciona no catalogo de produtos
	 * retorna - lista de produtos com a imagem
	 */
	public List<Catalogo> consultaCatalogo() {
		logger.info(">>>>>> servico consulta catalogo iniciado");
		Catalogo c = null;
		List<Catalogo> lista = new ArrayList<Catalogo>();
		List<Produto> listaP = produtoRepository.findAll();
		List<Imagem> listaI = imagemServico.getAll();
		for (Produto p : listaP) {
			for (Imagem i : listaI) {
				if (p.getId().equals(i.getId())) {
					c = new Catalogo(p.getId(), p.getDescricao(), p.getCategoria(), p.getCusto(),
							p.getQuantidadeNoEstoque(), i.getArquivo());
					lista.add(c);
				}
			}
		}
		return lista;
	}
}
