package gruppe8.nebula.controllers;

import gruppe8.nebula.models.Account;
import gruppe8.nebula.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TaskController {
    private final AccountService accountService;
    private final Logger log;


    public TaskController(AccountService accountService){
        this.accountService = accountService;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping("/task")
    public String task(Authentication authentication, Model model) {
        Account account = (Account) authentication.getPrincipal();
        model.addAttribute("accountList",accountService.getAllAccounts());
        log.info("GET /teams: Account=%s".formatted(account));
        return "task";
    }
}
