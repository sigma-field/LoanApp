package com.bb.loanapp.user;

import java.util.List;
import java.util.Optional;

import com.bb.loanapp.user.model.Role;
import com.bb.loanapp.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
