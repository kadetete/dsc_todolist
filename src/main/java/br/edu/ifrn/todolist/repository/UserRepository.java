package br.edu.ifrn.todolist.repository;

import br.edu.ifrn.todolist.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    
}