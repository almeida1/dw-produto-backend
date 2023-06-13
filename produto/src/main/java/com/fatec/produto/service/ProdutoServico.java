package com.fatec.produto.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.produto.model.Produto;
import com.fatec.produto.model.ProdutoRepository;

@Service
public class ProdutoServico implements IProdutoServico {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	ProdutoRepository repository;

	@Override
	public List<Produto> consultaTodos() {
		return repository.findAll();
	}

	@Override
	public List<Produto> consultaPorDescricao() {
		return null;
	}

	@Override
	public Optional<Produto> cadastrar(Produto produto) {

		return Optional.empty();
	}

	@Override
	public Optional<Produto> consultarPorId(String id) {
		logger.info(">>>>>> servico consulta por id chamado");
		long codProduto = Long.parseLong(id);
		return repository.findById(codProduto);
	}

	@Override
	public Optional<Produto> atualiza(Long id, Produto produto) {

		return Optional.empty();
	}

	@Override
	public void excluir(Long id) {
		// excluir produto pelo id
	}

}
