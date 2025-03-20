create table editora(
    id bigint not null auto_increment,
    nome VARCHAR(100) not null,
    cnpj VARCHAR(20) not null,
    email VARCHAR(100) not null,
    primary key(id)
);