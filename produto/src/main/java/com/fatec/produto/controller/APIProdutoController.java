package com.fatec.produto.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.produto.model.Imagem;
import com.fatec.produto.model.Produto;
import com.fatec.produto.service.ImagemServico;
import com.fatec.produto.service.ProdutoServico;

@RestController
@RequestMapping("/api/v1/produtos")
public class APIProdutoController {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	ProdutoServico servicoProduto;
	@Autowired
	ImagemServico servicoImagem;

	/**
	 * Retorna (i) detalhes do produto ou (ii) nao encontrado se o codigo do produto
	 * nao existir
	 * 
	 * @param id - string enviado na interface do usuario
	 * @return - 200 OK ou 404 NOT_FOUND
	 */
	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/{id}")
	public ResponseEntity<Object> consultaPorId(@PathVariable String id) {
		logger.info(">>>>>> apicontroller consulta por id chamado");
		Optional<Produto> produto = servicoProduto.consultarPorId(id);
		if (produto.isEmpty()) {
			logger.info(">>>>>> apicontroller id not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(produto.get());
	}

	@CrossOrigin // desabilita o cors do spring security
	@GetMapping
	public ResponseEntity<Object> consultaTodos() {
		logger.info(">>>>>> apicontroller consulta todos");

		return ResponseEntity.status(HttpStatus.OK).body(servicoProduto.consultaTodos());
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<String> upload(@RequestParam MultipartFile file, @RequestParam String id) {
		logger.info(">>>>>> api upload iniciada...");
		try {
			logger.info(">>>>>> api manipula file upload chamou servico salvar");
			long codProduto = Long.parseLong(id);
			Optional<Imagem> i = servicoImagem.salvar(file, codProduto);
			if (i.isPresent()) {
				return ResponseEntity.ok().body("Imagem enviada com sucesso");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id invalido não localizado");
			}
		} catch (FileNotFoundException e) {
			logger.info(">>>>>> api manipula file upload arquivo não encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arquivo nao encontrado");
		} catch (FileUploadException e) {
			logger.info(">>>>>> api manipula file upload erro no upload");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao enviar o arquivo");
		} catch (IOException e) {
			logger.info(">>>>>> api manipula file upload erro de i/o => " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha erro de I/O");
		} catch (NumberFormatException e) {
			logger.info(">>>>>> api manipula file upload erro de i/o => " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id invalido");
		}
	}
}
