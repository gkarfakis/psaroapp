package org.fishing.tournament.controllers;

import org.fishing.tournament.model.Contest;
import org.fishing.tournament.repositories.ContestRepo;
import org.fishing.tournament.services.ClubService;
import org.fishing.tournament.services.ContestantService;
import org.fishing.tournament.services.FishermanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public String login(ModelMap mm) {

        List<Contest> contests = new ArrayList<>(getAllContestsDesc());
        mm.addAttribute("contests", contests);
        return "contests";
    }

    @GetMapping("/contest/home")
    public String redirectToHome() {
        return "index";
    }

    private List<Contest> getAllContestsDesc() {
        return contestRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(Contest::getContestDate).reversed())
                .collect(Collectors.toList());
    }

    @GetMapping("/contest/{id}")
    public String getContest(@PathVariable Long id, ModelMap mm) {
        Optional<Contest> opt = contestRepo.findById(id);
        opt.ifPresent(contest -> {
            mm.addAttribute("contest", contest);
            mm.addAttribute("contestants", contestantService.getAllContestantsForContest(contest));
        });

        mm.addAttribute("clubs", clubService.getAllClubsAlphabetic());
        mm.addAttribute("fishermen", fishermanService.getFishermenAlphabetically());
        return "contestants";
    }

    @PostMapping("/contest")
    public String createContest(ModelMap mm, HttpServletRequest request) {
        String date = request.getParameter("date");
        Contest contest = new Contest(LocalDate.parse(date, formatter));
        // TODO: 1/30/2023 ask if contest date is unique
        contestRepo.save(contest);
        List<Contest> contests = getAllContestsDesc();
        mm.addAttribute("contests", contests);
        return "contests";
    }
}
