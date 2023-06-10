package com.fatec.produto.service;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fatec.produto.model.Produto;
import com.fatec.produto.model.ProdutoRepository;

@Configuration
public class LoadDatabase {
	Logger logger = LogManager.getLogger(this.getClass());
	@Bean
	CommandLineRunner initDatabase(ProdutoRepository repository) {
		return args -> {
			Produto produto = new Produto("Eletrobomba 110V para Maquina de Lavar e Lava Louças", 51.66, 12);
			repository.save(produto);
			logger.info (">>>>> loaddatabase -> registro incluido");
		};
	}
}
