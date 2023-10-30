package br.edu.ifrn.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifrn.todolist.domain.user.DadosAutenticacao;
import br.edu.ifrn.todolist.domain.user.User;
import br.edu.ifrn.todolist.infra.security.DadosTokenJWT;
import br.edu.ifrn.todolist.service.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
  
  @Autowired
  private AuthenticationManager manager;

  
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  

  @Autowired
  TokenService tokenService;

  @PostMapping
  public ResponseEntity<Object> efetuarLogin(@RequestBody DadosAutenticacao dados){
    var token = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
    var authentication = manager.authenticate(token);
    var tokenJWT = tokenService.gerarToken((User) authentication.getPrincipal());
    return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
  }

   
  @GetMapping
  public ResponseEntity<String> getSenhaBcrypt(@RequestBody String senha){
    String senhaBrypt = bCryptPasswordEncoder.encode(senha);
    return ResponseEntity.ok(senhaBrypt);
  }
}