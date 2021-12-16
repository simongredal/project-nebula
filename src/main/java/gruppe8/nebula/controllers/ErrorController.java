// Authors Malthe Gram
package gruppe8.nebula.controllers;

/* Documentation for the error handler
 https://www.baeldung.com/spring-boot-custom-error-page

 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private final Logger logger;

    public ErrorController() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @RequestMapping("/error")
    public String errorHandler(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // Returns the page which was tried to access
        logger.info((String) request.getAttribute("javax.servlet.error.request_uri"));

        return switch (String.valueOf(status)) {
            case "400","401","403","404" -> "error-404";
            case "500","503" -> "error-500";

            default -> "index";
        };

    }
}

