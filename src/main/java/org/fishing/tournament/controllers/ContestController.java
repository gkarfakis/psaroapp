package org.fishing.tournament.controllers;

import org.fishing.tournament.model.Contest;
import org.fishing.tournament.repositories.ContestRepo;
import org.fishing.tournament.services.ClubService;
import org.fishing.tournament.services.ContestantService;
import org.fishing.tournament.services.FishermanService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ContestController {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final ContestRepo contestRepo;
    private final ClubService clubService;
    private final FishermanService fishermanService;
    private final ContestantService contestantService;

    public ContestController(ContestRepo contestRepo, ClubService clubService, FishermanService fishermanService, ContestantService contestantService) {
        this.contestRepo = contestRepo;
        this.clubService = clubService;
        this.fishermanService = fishermanService;
        this.contestantService = contestantService;
    }

    @GetMapping("/contests")
    public String getContests(ModelMap mm) {

        List<Contest> contests = new ArrayList<>(getAllContestsDesc());
        mm.addAttribute("contests", contests);
        return "contests";
    }

    private List<Contest> getAllContestsDesc() {
        return contestRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(Contest::getContestDate).reversed())
                .collect(Collectors.toList());
    }

    @PostMapping("/contest")
    public String createContest(ModelMap mm, HttpServletRequest request) {
        String date = request.getParameter("date");
        String description = request.getParameter("description");
        try {
            Contest contest = new Contest(LocalDate.parse(date, formatter));
            contest.setDescription(description);
            // TODO: 1/30/2023 ask if contest date is unique
            contestRepo.save(contest);
        } catch (DateTimeParseException e) {
            System.out.println("Incorrect date format. Please use 'dd/MM/yyyy'.");
            mm.addAttribute("message", "Incorrect date format. Please use 'dd/MM/yyyy'.");
        } catch (Exception e) {
            mm.addAttribute("message", e.getMessage() != null ? e.getMessage() : "Error creating contest.");
        }
        return getContests(mm);
    }

    @GetMapping("/editContest")
    public String editContestForm(ModelMap mm, HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("contestId"));
        try {
            Contest contest = contestRepo.findById(id).get();
            String date  = contest.getContestDate().format(formatter);
            mm.addAttribute("contest", contest);
            mm.addAttribute("date", date);
        } catch (Exception e) {
            mm.addAttribute("message", e.getMessage() != null ? e.getMessage() : "Error editing contest.");
        }
        return "contest";
    }

    @GetMapping("/contest")
    public String editContest(ModelMap mm, HttpServletRequest request) {
        String description = request.getParameter("description");
        String date = request.getParameter("date");
        Long id = Long.valueOf(request.getParameter("contestId"));
        try {
            Contest contest = contestRepo.findById(id).get();
            contest.setContestDate(LocalDate.parse(date, formatter));
            contest.setDescription(description);
            contestRepo.save(contest);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Contest date already exists.");
            mm.addAttribute("message", "Contest date already exists.");
        } catch (Exception e) {
            mm.addAttribute("message", e.getMessage() != null ? e.getMessage() : "Error editing contest.");
        }
        return getContests(mm);
    }

    @PostMapping("/deleteContest")
    public String deleteContest(ModelMap mm, HttpServletRequest request) {
        Long contestId = Long.valueOf(request.getParameter("contestId"));
        try {
            Contest contest = contestRepo.findById(contestId).get();
            if (contest.getContestants().isEmpty())
                contestRepo.delete(contest);
            else {
                System.out.println("Cannot delete contest because it has child records (contestants)");
                mm.addAttribute("message", "Cannot delete contest because it has child records (contestants)");
            }
        } catch (Exception e) {
            mm.addAttribute("message", e.getMessage() != null ? e.getMessage() : "Error deleting contest.");
        }
        return getContests(mm);
    }
}
