package com.fatec.produto.controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.produto.model.Produto;
import com.fatec.produto.service.ProdutoServico;

@RestController
@RequestMapping("/api/v1/produtos")
public class APIProdutoController {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	ProdutoServico servico;
	/**
	 * Retorna detalhes do produto ou nao encontrado se o codigo do produto nao existir 
	 * @param id - string enviado na interface do usuario
	 * @return - 200 OK ou 404 NOT_FOUND
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> consultaPorId(@PathVariable String id){
		logger.info(">>>>>> apicontroller consulta por id chamado");
		Optional<Produto> produto = servico.consultarPorId(id);
		if (produto.isEmpty()) {
			logger.info(">>>>>> apicontroller id not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(produto.get());
	}
}
