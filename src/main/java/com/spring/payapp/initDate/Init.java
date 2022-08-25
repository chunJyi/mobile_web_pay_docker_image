
        package com.spring.payapp.initiData;

        import java.time.LocalDate;
        import java.util.Random;

        import com.spring.payapp.domain.entity.Account;
        import com.spring.payapp.domain.entity.Gender;
        import com.spring.payapp.domain.entity.Usr;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.CommandLineRunner;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.security.crypto.password.PasswordEncoder;

        import com.spring.payapp.domain.repository.IAccountRepo;
        import com.spring.payapp.domain.repository.IUserRepo;
        import com.spring.payapp.domain.repository.IWalletRepo;

        import lombok.Data;

/**
 * @author chun
 * @package payApp
 * @time 8:59:12 PM
 */
@Configuration
@Data
public class Init{
    @Autowired
    private IUserRepo userRepo;
    @Autowired
    private IAccountRepo accountRepo;

    @Autowired
    private PasswordEncoder encode;

    @Autowired
    private IWalletRepo walletRepo;


    @Bean
    public CommandLineRunner getCommandLineRunner(){

        return args -> {
              Account a = Account.builder().email("root@gmail.com") .accountID(1)
              .password(encode.encode("root")) .createdDate(LocalDate.now())
              .updatedDate(LocalDate.now()) .role(Account.Role.ROLE_ADMIN) .enable(true) .build();
              accountRepo.save(a);
              Usr u = new Usr(1, "root", "098765432", a, Gender.Male);
              userRepo.save(u);
        };
    }



}

