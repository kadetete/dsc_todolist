package br.edu.ifrn.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import br.edu.ifrn.todolist.domain.tarefa.Tarefa;
import br.edu.ifrn.todolist.repository.TarefaRepository;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity criar(@RequestBody Tarefa tarefa,
                                UriComponentsBuilder uriBuilder){
        Tarefa tarefaLocal = repository.save(tarefa);
        var uri = uriBuilder.path("/tarefas/{id}").
                  buildAndExpand(tarefaLocal.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    
    @GetMapping
    public ResponseEntity<Page<Tarefa>> listar(@PageableDefault(size=4,
    sort = {"titulo"}) Pageable paginacao) {
        var tarefas = repository.findAll(paginacao);
        return ResponseEntity.ok(tarefas);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var tarefa = repository.getReferenceById(id);
        repository.delete(tarefa);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Tarefa> atualizar(@RequestBody Tarefa tarefa){
        Tarefa tarefaLocal = repository.findById(tarefa.getId()).get();

        tarefaLocal.setTitulo(tarefa.getTitulo());

        return ResponseEntity.ok(tarefaLocal);
    }
}
