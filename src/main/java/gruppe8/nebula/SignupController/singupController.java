package gruppe8.nebula.SignupController;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class singupController {

    @GetMapping("/signup")
    public String signupPage(){
        return "signup";
    }
}
