package gruppe8.nebula.SignupController;

import gruppe8.nebula.models.Account;
import gruppe8.nebula.repositories.AccountRepository;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignupService {
    private final AccountRepository accountRepository;
    private final Argon2PasswordEncoder passwordEncoder;

    public SignupService(AccountRepository accountRepository, Argon2PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Boolean register(SignupRequest request) {
        Account account = new Account(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password()));

        return accountRepository.createAccount(account);
        }
}
