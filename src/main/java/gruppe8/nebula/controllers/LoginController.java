package gruppe8.nebula.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {
    private final Logger log;

    public LoginController() {
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping("/login")
    public String login(@RequestParam Optional<String> error,
                        @RequestParam Optional<String> success,
                        Model model) {
        log.info("GET /login: Model=" + model);

        if (success.isPresent()) { return "redirect:/project_page"; }
        if (error.isPresent()) { model.addAttribute("loginError", true); }

        return "login";
    }
}