package br.edu.ifrn.todolist.repository;

import br.edu.ifrn.todolist.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long>{

    UserDetails findByEmail(String email);
    
}