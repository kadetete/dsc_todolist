package br.edu.ifrn.todolist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifrn.todolist.domain.tarefa.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    Page<Tarefa> findAllByTituloTrue(Pageable pageable);
    Tarefa findByTitulo(String titulo);
}
