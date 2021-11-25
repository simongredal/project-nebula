package gruppe8.nebula.services;

import gruppe8.nebula.models.Account;
import gruppe8.nebula.repositories.AccountRepository;
import gruppe8.nebula.requests.AccountCreationRequest;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final Argon2PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, Argon2PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Boolean register(AccountCreationRequest request) {
        Account account = new Account(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password())
        );

        return accountRepository.createAccount(account);
    }

    public Account getAccountByEmail(String email) {
        return accountRepository.getAccountByEmail(email);
    }
}
