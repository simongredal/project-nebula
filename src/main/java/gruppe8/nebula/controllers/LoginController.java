package gruppe8.nebula.controllers;

import gruppe8.nebula.requests.SignupRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(@RequestParam Optional<String> error,
                        @RequestParam Optional<String> signupSuccess,
                        @RequestParam Optional<String> success,
                        Model model) {
            if (success.isPresent()) {
                return "redirect:/project_page";
            }
            if (error.isPresent()) {
                model.addAttribute("error", true);
            }
            if (signupSuccess.isPresent()) {
                model.addAttribute("signupSuccess", true);
            }
            return "login";
        }

    }


