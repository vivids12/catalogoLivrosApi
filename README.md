# API de Livros, Autores e Editoras

## Sobre a API
Esta API foi desenvolvida utilizando **Java** com **Spring Boot** e tem como objetivo gerenciar livros, autores e editoras. A API conta com segurança implementada via **Spring Security**, documentação interativa com **Swagger** e utiliza o banco de dados **H2** para armazenamento dos dados.

## Tecnologias Utilizadas
- **Java 21**
- **Spring Boot 3**
- **Spring Security**
- **Spring Data JPA**
- **H2 Database**
- **Swagger/OpenAPI**

## Configuração do Ambiente
### Pré-requisitos
Para executar este projeto, você precisará ter instalado:
- **JDK 21** ou superior
- **Maven** (caso deseje compilar e rodar via linha de comando)

## Segurança
A API implementa autenticação via **JWT (JSON Web Token)**. Para acessar os endpoints protegidos, é necessário incluir o token no cabeçalho das requisições

## Banco de Dados H2
A API utiliza um banco de dados H2 em memória. Para acessar a interface web do H2:
- **URL**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL**: `jdbc:h2:file:./BD/catalogoLivros`
- **Usuário**: `admin`
- **Senha**: `123`

## Documentação Swagger
A documentação interativa da API pode ser acessada em:
- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

