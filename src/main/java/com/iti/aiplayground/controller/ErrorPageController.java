package com.iti.aiplayground.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController implements ErrorController {
    private final ErrorAttributes errorAttributes;

    public ErrorPageController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        ErrorAttributeOptions options = ErrorAttributeOptions.defaults()
                .including(ErrorAttributeOptions.Include.MESSAGE);
        var attributes = errorAttributes.getErrorAttributes(request, options);
        Throwable error = errorAttributes.getError(request);
        model.addAttribute("status", attributes.get("status"));
        model.addAttribute("error", attributes.get("error"));
        model.addAttribute("message", attributes.get("message"));
        model.addAttribute("rootCause", getRootCauseMessage(error));
        return "error";
    }

    private String getRootCauseMessage(Throwable error) {
        if (error == null) {
            return "Unknown root cause.";
        }
        Throwable root = error;
        while (root.getCause() != null) {
            root = root.getCause();
        }
        return root.getMessage() != null ? root.getMessage() : root.toString();
    }
}
