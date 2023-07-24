package com.fatec.produto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.produto.model.Catalogo;
import com.fatec.produto.model.IImagemRepository;
import com.fatec.produto.model.IProdutoRepository;
import com.fatec.produto.model.Imagem;
import com.fatec.produto.model.Produto;

@Service
public class ProdutoServico implements IProdutoServico {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	IProdutoRepository repositoryP;
	@Autowired
	IImagemRepository repositoryI;

	@Override
	public List<Produto> consultaTodos() {
		return repositoryP.findAll();
	}

	@Override
	public List<Produto> consultaPorDescricao() {
		return null;
	}

	@Override
	public Optional<Produto> cadastrar(Produto produto) {
		logger.info(">>>>>> servico cadastrar produto iniciado ");
		return Optional.ofNullable(repositoryP.save(produto));
	}

	@Override
	public Optional<Produto> consultarPorId(String id) {
		logger.info(">>>>>> servico consulta por id chamado");
		long codProduto = Long.parseLong(id);
		return repositoryP.findById(codProduto);
	}

	@Override
	public Optional<Produto> atualizar(Long id, Produto produto) {
		return Optional.empty();
	}

	@Override
	public void excluir(Long id) {
		// excluir produto pelo id
	}

	public List<Catalogo> consultaCatalogo() {
		Catalogo c = null;
		List<Catalogo> lista = new ArrayList<Catalogo>();
		List<Produto> listaP = repositoryP.findAll();
		List<Imagem> listaI = repositoryI.findAll();
		System.out.println(">>>>>> tamanho da listaP => " + listaP.size());
		System.out.println(">>>>>> tamanho da listaI => " + listaI.size());
		for (Produto p : listaP) {
			for (Imagem i : listaI) {
				System.out.println(">>>>>> produto  => " + p.getId() + "-" + i.getId());
				if (p.getId().equals(i.getId())) {
					c = new Catalogo(p.getId(), p.getDescricao(), p.getCategoria(), p.getCusto(), i.getArquivo());
					lista.add(c);
				}
			}
		}
		return lista;
	}
}
