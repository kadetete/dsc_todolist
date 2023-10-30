package br.edu.ifrn.todolist.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.edu.ifrn.todolist.repository.UserRepository;
import br.edu.ifrn.todolist.domain.user.User;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;


@RestController
@RequestMapping("users")
public class UserController {
    
    @Autowired
    private UserRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> cadastrar(@RequestBody User user,
            UriComponentsBuilder uriBuilder) {
        User userLocal = repository.save(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userLocal.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<User> atualizar(@RequestBody User user) {
        User userLocal = repository.findById(
                user.getId()).get();

        userLocal.setNome(user.getNome());

        return ResponseEntity.ok(userLocal);
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> excluir(@PathVariable Long id) {
        var user = repository.getReferenceById(id);
        repository.delete(user);
        return ResponseEntity.noContent().build();
    }
}
