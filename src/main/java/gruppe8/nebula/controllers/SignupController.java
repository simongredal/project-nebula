package gruppe8.nebula.controllers;


import gruppe8.nebula.services.SignupService;
import gruppe8.nebula.requests.SignupRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class SignupController {
    private final SignupService signupService;
    private final Logger log;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        log.info("GET /signup: Model=" + model);

        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView addUser(SignupRequest request, RedirectAttributes redirectAttributes) {
        log.info("POST /signup: SignupRequest=" + request.toString());

        Boolean signupWasSuccessful = signupService.register(request);
        redirectAttributes.addFlashAttribute("signupWasSuccessful", signupWasSuccessful);

        if (signupWasSuccessful) { return new RedirectView("/login"); }
        return new RedirectView("/signup");
    }

}
