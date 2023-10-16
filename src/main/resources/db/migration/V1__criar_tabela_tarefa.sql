create table tarefa (
    id int auto_increment primary key,
    titulo varchar(100) not null,
    descricao varchar(250) not null,
    completo boolean not null
);