package org.fishing.tournament.controllers;

import org.fishing.tournament.model.Club;
import org.fishing.tournament.model.Fisherman;
import org.fishing.tournament.repositories.FishermanRepo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class FishermanController {
    
    private final FishermanRepo fishermanRepo;

    public FishermanController(FishermanRepo fishermanRepo) {
        this.fishermanRepo = fishermanRepo;
    }


    @GetMapping("/fishermen")
    public String getFishermen(ModelMap mm) {
        List<Fisherman> fishermen = fishermanRepo.findAll();
        mm.addAttribute("fishermen", fishermen);
        return "fishermen";
    }

    @PostMapping("/fisherman")
    public String create(ModelMap mm, HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        try{
            fishermanRepo.save(new Fisherman(name, surname));
        }
        catch (DataIntegrityViolationException e){
            System.out.println("Fisherman full name already exists.");
        }
        catch (Exception e){
            System.out.println(e);
        }
        return getFishermen(mm);
    }

    @PostMapping("/deleteFisherman")
    public String deleteFisherman(ModelMap mm, HttpServletRequest request) {
        Long fishermanId = Long.valueOf(request.getParameter("fishermanId"));
        try {
            Fisherman fisherman = fishermanRepo.findById(fishermanId).get();
            if (fisherman.getContestants().isEmpty())
                fishermanRepo.delete(fisherman);
            else
                System.out.println("Cannot delete fisherman because it has child records (contestants)");
        } catch (Exception e) {
            System.out.println(e);
        }
        return getFishermen(mm);
    }
}
