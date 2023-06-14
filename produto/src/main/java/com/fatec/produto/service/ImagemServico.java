package com.fatec.produto.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.produto.model.Imagem;
import com.fatec.produto.model.ImagemRepository;
import com.fatec.produto.model.Produto;
import com.fatec.produto.model.ProdutoRepository;

@Service
public class ImagemServico implements IImagemServico {
	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private ImagemRepository imagemRepository;
	@Autowired
	private ProdutoRepository produtoRepository;

	private Environment environment = null;

	public ImagemServico(ImagemRepository imagemRepository, Environment environment) {
		this.imagemRepository = imagemRepository;
		// this.environment = environment;
	}

	public Optional<Imagem> salvar(MultipartFile arquivo, long id) throws IOException {
		// **********************************************************
		// Obter informações sobre o arquivo
		// **********************************************************
		Optional<Produto> p = produtoRepository.findById(id);
		if (p.isPresent()) {
			logger.info(">>>>>> servico salvar imagem - iniciado...");
			String nome = arquivo.getOriginalFilename();
			Path caminhoArquivo = Paths.get("imagens/" + nome);
			Imagem imagem = new Imagem();
			imagem.setId(id); // associa o id do produto ao id da imagem
			imagem.setNome(arquivo.getOriginalFilename());
			imagem.setCaminho(caminhoArquivo.toString());
			imagem.setArquivo(arquivo.getBytes());
			logger.info(">>>>>> servico salvar imagem - arquivo getSize => " + arquivo.getSize());
			// **********************************************************
			// salva no disco e no db
			// ***********************************************************
			Files.write(caminhoArquivo, arquivo.getBytes());
			return Optional.of(imagemRepository.save(imagem));
		}
		else {
			logger.info(">>>>>> servico salvar imagem - id nao encontrado");
			return Optional.empty();
		}
	}

	public List<Imagem> getAll() {
		return imagemRepository.findAll();
	}

	public byte[] getImagem(String nomeArquivo) {
		Imagem dbImagem = imagemRepository.findByNome(nomeArquivo).get();
		return dbImagem.getArquivo();
	}
}
