package com.fatec.produto.service;



import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fatec.produto.model.Produto;
import com.fatec.produto.model.ProdutoRepository;

@Configuration
public class LoadDatabase {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	ImagemServico servico;
	@Bean
	CommandLineRunner initDatabase(ProdutoRepository repository) {
		return args -> {
			Produto produto1 = new Produto("Eletrobomba 110V para Maquina de Lavar e Lava Louças", 51.66, 12);
			Produto produto2 = new Produto("Tirante Original Brastemp E Consul De 7 A 12 Kg 11cm", 3.90,20);
			Produto produto3 = new Produto("Termoatuador Lavadora Colormaq Electrolux GE", 29.70,40);
			repository.saveAll(Arrays.asList(produto1, produto2, produto3));
			logger.info (">>>>> loaddatabase -> registro de produto iniciado ...");
			Path path = Paths.get("c:/temp/produto1.jpg");
			InputStream arquivo = Files.newInputStream(path);
			byte[] arquivo2 = arquivo.readAllBytes();
			logger.info (">>>>> loaddatabase -> tamanho do arquivo => " + arquivo2.length);
			ArquivoDeImagem multPartFile = new ArquivoDeImagem(arquivo2);
		
			servico.salvar(multPartFile, 1);
		};
	}
}
