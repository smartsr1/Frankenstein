package it.frankenstein.data.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
public class AnalyzeController {
	
	@RequestMapping("/index")
    public String index() {
        return "home";
    }
}
