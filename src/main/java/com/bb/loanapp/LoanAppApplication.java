package com.bb.loanapp;

import com.bb.loanapp.user.model.Role;
import com.bb.loanapp.customer.CustomerRepository;
import com.bb.loanapp.user.UserRepository;
import com.bb.loanapp.customer.model.Customer;
import com.bb.loanapp.user.model.User;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
@AllArgsConstructor
@ConfigurationPropertiesScan
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class LoanAppApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(LoanAppApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        User adminUser = new User();
        adminUser.setFirstname("super_admin");
        adminUser.setLastname("super_admin");
        adminUser.setEmail("super_admin@email.com");
        adminUser.setPassword(passwordEncoder.encode("password"));
        adminUser.setRole(Role.ROLE_SUPER_ADMIN);
        userRepository.save(adminUser);

        Customer customer1 = new Customer();
        customer1.setName("Customer1");
        customer1.setSurname("Customer1");
        customer1.setCreditLimit(BigDecimal.valueOf(1_000_000));

        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setName("Customer2");
        customer2.setSurname("Customer2");
        customer2.setCreditLimit(BigDecimal.valueOf(10_000_000));

        customerRepository.save(customer2);


    }
}
