package com.catalogo.api.catalogoLivros;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "API Catálogo de livros", version = "1.0", description = "API para cadastro livros, editoras e autores"))
public class CatalogoLivrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoLivrosApplication.class, args);
	}

}
