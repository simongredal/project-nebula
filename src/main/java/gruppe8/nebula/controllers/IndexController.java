// Authors Simon Gredal
package gruppe8.nebula.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/contact")
    public String contact(Model model){
        return "contact";
    }
}
