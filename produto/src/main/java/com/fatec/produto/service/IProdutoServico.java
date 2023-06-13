package com.fatec.produto.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.fatec.produto.model.Imagem;
import com.fatec.produto.model.Produto;

public interface IProdutoServico {
	public List<Produto> consultaTodos();
	public List<Produto> consultaPorDescricao();
	public Optional <Produto> cadastrar(Produto produto);
	public Optional <Produto> consultarPorId(String id);
	public Optional <Produto> atualiza(Long id, Produto produto);
	public void excluir(Long id);
	
}
