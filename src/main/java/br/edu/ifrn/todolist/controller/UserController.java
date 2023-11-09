package br.edu.ifrn.todolist.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.edu.ifrn.todolist.repository.UserRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import br.edu.ifrn.todolist.domain.user.User;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    
    @Autowired
    private UserRepository repository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid User user,
            UriComponentsBuilder uriBuilder) {
        user.setSenha(bCryptPasswordEncoder.encode(user.getSenha()));
        User userLocal = repository.save(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userLocal.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<User> atualizar(@RequestBody @Valid User user) {
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
