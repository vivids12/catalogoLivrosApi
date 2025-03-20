create table livro(
      id bigint not null auto_increment,
      titulo VARCHAR(100) not null,
      genero VARCHAR(100) not null,
      autor_id bigint not null,
      editora_id bigint not null,
      foreign key (autor_id) references autor(id),
      foreign key (editora_id) references editora(id),
      primary key(id)
);