package org.fishing.tournament.controllers;

import org.fishing.tournament.model.Contest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @GetMapping("/home")
    public String index(ModelMap mm) {
        return "index";
    }
}
