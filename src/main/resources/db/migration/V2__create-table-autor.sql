create table autor(
    id bigint not null auto_increment,
    nome VARCHAR(100) not null,
    email VARCHAR(100) not null,
    telefone VARCHAR(12) not null,
    cpf VARCHAR(14) not null,
    primary key(id)
);