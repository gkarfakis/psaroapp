package org.fishing.tournament.controllers;

import org.fishing.tournament.model.Club;
import org.fishing.tournament.repositories.ClubRepo;
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
public class ClubController {

    private final ClubRepo clubRepo;

    public ClubController(ClubRepo clubRepo) {
        this.clubRepo = clubRepo;
    }

    @GetMapping("/clubs")
    public String getClubs(ModelMap mm) {
        List<Club> clubs = clubRepo.findAll().stream()
                .sorted(Comparator.comparing(Club::getClubName))
                .collect(Collectors.toList());
        mm.addAttribute("clubs", clubs);
        return "clubs";
    }

    @PostMapping("/club")
    public String createClub(ModelMap mm, HttpServletRequest request) {
        String clubName = request.getParameter("clubName");
        try {
            clubRepo.save(new Club(clubName));
        } catch (DataIntegrityViolationException e) {
            System.out.println("Club name already exists.");
            mm.addAttribute("message", "Club name already exists.");
        } catch (Exception e) {
            mm.addAttribute("message", e.getMessage() != null ? e.getMessage() : "Error creating club.");
        }
        return getClubs(mm);
    }

    @GetMapping("/editClub")
    public String editClubForm(ModelMap mm, HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("clubId"));
        try {
            Club club = clubRepo.findById(id).get();
            mm.addAttribute("club", club);
        } catch (Exception e) {
            mm.addAttribute("message", e.getMessage() != null ? e.getMessage() : "Error editing club.");
        }
        return "club";
    }

    @GetMapping("/club")
    public String editclub(ModelMap mm, HttpServletRequest request) {
        String name = request.getParameter("clubName");
        Long id = Long.valueOf(request.getParameter("clubId"));
        try {
            Club club = clubRepo.findById(id).get();
            club.setClubName(name);
            clubRepo.save(club);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Club name already exists.");
            mm.addAttribute("message", "Club name already exists.");
        } catch (Exception e) {
            mm.addAttribute("message", e.getMessage() != null ? e.getMessage() : "Error editing club.");
        }
        return getClubs(mm);
    }

    @PostMapping("/deleteClub")
    public String deleteClub(ModelMap mm, HttpServletRequest request) {
        Long clubId = Long.valueOf(request.getParameter("clubId"));
        try {
            Club club = clubRepo.findById(clubId).get();
            if (club.getContestants().isEmpty())
                clubRepo.delete(club);
            else {
                System.out.println("Cannot delete club because it has child records (contestants)");
                mm.addAttribute("message", "Cannot delete club because it has child records (contestants)");
            }
        } catch (Exception e) {
            mm.addAttribute("message", e.getMessage() != null ? e.getMessage() : "Error deleting club.");
        }
        return getClubs(mm);
    }
}
