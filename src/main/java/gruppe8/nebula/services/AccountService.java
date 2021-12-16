// Authors Simon Gredal & Malthe Gram
package gruppe8.nebula.services;

import gruppe8.nebula.entities.AccountEntity;
import gruppe8.nebula.models.Account;
import gruppe8.nebula.repositories.AccountRepository;
import gruppe8.nebula.requests.AccountCreationRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final Argon2PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, Argon2PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Boolean register(AccountCreationRequest request) {
        AccountEntity account = new AccountEntity(
                null,
                request.email(),
                passwordEncoder.encode(request.password()),
                request.name()
        );

        return accountRepository.createAccount(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = getAccountByEmail(username);
        if (account==null)  { throw new UsernameNotFoundException("account with email " + username + " not found"); }
        return account;
    }

    public Account getAccountByEmail(String email) {
        AccountEntity entity = accountRepository.getAccountByEmail(email);
        return Account.of(entity);
    }

    public List<Account> loadAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        for (AccountEntity entity : accountRepository.getAllAccounts()) {
            accounts.add( Account.of(entity) );
        }
        return accounts;
    }
}
