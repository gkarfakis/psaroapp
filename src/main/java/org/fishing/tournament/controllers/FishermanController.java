package org.fishing.tournament.controllers;

import org.fishing.tournament.model.Club;
import org.fishing.tournament.model.Fisherman;
import org.fishing.tournament.repositories.ClubRepo;
import org.fishing.tournament.repositories.FishermanRepo;
import org.fishing.tournament.services.ClubService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FishermanController {

    private final FishermanRepo fishermanRepo;
    private final ClubRepo clubRepo;
    private final ClubService clubService;

    public FishermanController(FishermanRepo fishermanRepo, ClubRepo clubRepo, ClubService clubService) {
        this.fishermanRepo = fishermanRepo;
        this.clubRepo = clubRepo;
        this.clubService = clubService;
    }


    @GetMapping("/fishermen")
    public String getFishermen(ModelMap mm) {
        List<Fisherman> fishermen = fishermanRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(Fisherman::getFishermanSurName))
                .collect(Collectors.toList());
        List<Club> clubs = clubService.getAllClubsAlphabetic();
        mm.addAttribute("fishermen", fishermen);
        mm.addAttribute("clubs", clubs);
        return "fishermen";
    }

    @PostMapping("/fisherman")
    public String createFisherman(ModelMap mm, HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        Long clubId = Long.valueOf(request.getParameter("clubId"));
        try {
            Club club = clubRepo.findById(clubId).get();
            Fisherman fisherman = new Fisherman(name, surname);
            fisherman.setClub(club);
            fishermanRepo.save(fisherman);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Fisherman full name already exists.");
            mm.addAttribute("message", "Fisherman full name already exists.");
        } catch (Exception e) {
            mm.addAttribute("message", e.getMessage() != null ? e.getMessage() : "Error creating fisherman.");
        }
        return getFishermen(mm);
    }

    @GetMapping("/editFisherman")
    public String editFishermanForm(ModelMap mm, HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("fishermanId"));
        try {
            Fisherman fisherman = fishermanRepo.findById(id).get();
            List<Club> clubs = clubService.getAllClubsAlphabetic();
            mm.addAttribute("fisherman", fisherman);
            mm.addAttribute("clubs", clubs);
        } catch (Exception e) {
            mm.addAttribute("message", e.getMessage() != null ? e.getMessage() : "Error editing fisherman.");
        }
        return "fisherman";
    }

    @GetMapping("/fisherman")
    public String editFisherman(ModelMap mm, HttpServletRequest request) {
        String name = request.getParameter("fishermanName");
        String surname = request.getParameter("fishermanSurName");
        Long clubId = Long.valueOf(request.getParameter("clubId"));
        Long id = Long.valueOf(request.getParameter("fishermanId"));
        try {
            Club club = clubRepo.findById(clubId).get();
            Fisherman fisherman = fishermanRepo.findById(id).get();
            fisherman.setClub(club);
            fisherman.setFishermanName(name);
            fisherman.setFishermanSurName(surname);
            fishermanRepo.save(fisherman);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Fisherman full name already exists.");
            mm.addAttribute("message", "Fisherman full name already exists.");
        } catch (Exception e) {
            mm.addAttribute("message", e.getMessage() != null ? e.getMessage() : "Error editing fisherman.");
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
            else {
                System.out.println("Cannot delete fisherman because it has child records (contestants)");
                mm.addAttribute("message", "Cannot delete fisherman because it has child records (contestants)");
            }
        } catch (Exception e) {
            mm.addAttribute("message", e.getMessage() != null ? e.getMessage() : "Error creating fisherman.");
        }
        return getFishermen(mm);
    }
}
