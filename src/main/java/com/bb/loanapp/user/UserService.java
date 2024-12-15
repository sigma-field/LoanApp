package com.bb.loanapp.user;


import com.bb.loanapp.exception.model.ResourceAlreadyExistsException;
import com.bb.loanapp.exception.model.ResourceNotFoundException;
import com.bb.loanapp.user.model.Role;
import com.bb.loanapp.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("user", "username", username));
        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public long createAdminUser(User user) {
        user.setRole(Role.ROLE_ADMIN);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("user", "email", user.getEmail());
        }
        return this.save(user).getId();
    }
}
