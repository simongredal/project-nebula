package gruppe8.nebula.controllers;

import gruppe8.nebula.models.Account;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String index(Authentication authentication, Model model) {
        Account account = authentication == null ? null : (Account) authentication.getPrincipal();

        model.addAttribute("account", account);
        return "index";
    }
}
