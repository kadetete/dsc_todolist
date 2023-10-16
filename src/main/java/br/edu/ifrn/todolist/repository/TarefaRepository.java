package br.edu.ifrn.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifrn.todolist.domain.tarefa.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
}
