package gruppe8.nebula.controllers;


import gruppe8.nebula.services.AccountService;
import gruppe8.nebula.requests.AccountCreationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final AccountService signupService;
    private final Logger log;

    public SignupController(AccountService signupService) {
        this.signupService = signupService;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping
    public String signup(Model model) {
        log.info("GET /signup: Model=" + model);

        return "signup";
    }

    @PostMapping
    public RedirectView addUser(AccountCreationRequest request, RedirectAttributes redirectAttributes) {
        log.info("POST /signup: SignupRequest=" + request.toString());

        Boolean signupWasSuccessful = signupService.register(request);
        redirectAttributes.addFlashAttribute("signupWasSuccessful", signupWasSuccessful);

        if (signupWasSuccessful) { return new RedirectView("/login"); }
        return new RedirectView("/signup");
    }

}
