package org.fishing.tournament.controllers;

import org.fishing.tournament.model.Club;
import org.fishing.tournament.model.Contest;
import org.fishing.tournament.model.Contestant;
import org.fishing.tournament.model.Fisherman;
import org.fishing.tournament.repositories.ClubRepo;
import org.fishing.tournament.repositories.ContestRepo;
import org.fishing.tournament.repositories.ContestantRepo;
import org.fishing.tournament.services.ClubService;
import org.fishing.tournament.services.ContestantService;
import org.fishing.tournament.services.FishermanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ContestantController {

    private final ContestRepo contestRepo;
    private final ClubRepo clubRepo;
    private final FishermanService fishermanService;
    private final ContestantRepo contestantRepo;
    private final ContestantService contestantService;
    private final ClubService clubService;

    public ContestantController(ContestRepo contestRepo, ClubRepo clubRepo, FishermanService fishermanService, ContestantRepo contestantRepo, ContestantService contestantService, ClubService clubService) {
        this.contestRepo = contestRepo;
        this.clubRepo = clubRepo;
        this.fishermanService = fishermanService;
        this.contestantRepo = contestantRepo;
        this.contestantService = contestantService;
        this.clubService = clubService;
    }

    @GetMapping("/contest/{id}")
    public String getContest(@PathVariable Long id, ModelMap mm) {
        Optional<Contest> opt = contestRepo.findById(id);
        opt.ifPresent(contest -> {
            mm.addAttribute("contest", contest);
            mm.addAttribute("contestants", contestantService.getAllContestantsForContest(contest)
                    .stream().sorted(Comparator.comparing(Contestant::getClub).reversed())
                    .collect(Collectors.toList()));
            List<Contestant> contestants = contestantService.getAllContestantsForContest(contest);
            mm.addAttribute("totalNum", contestants.size());
        });

        mm.addAttribute("clubs", clubService.getAllClubsAlphabetic());
        mm.addAttribute("fishermen", fishermanService.getFishermenAlphabetically().stream()
                .filter(x -> x.getClub() == null)
                .collect(Collectors.toList()));
        return "contestants";
    }

    @PostMapping("/contestant")
    public String createContest(ModelMap mm, HttpServletRequest request) {
        Long contestId = Long.valueOf(request.getParameter("contestId"));
        Contest contest = contestRepo.findById(contestId).get();
        Long clubId = Long.valueOf(request.getParameter("clubId"));
        Club club = clubRepo.findById(clubId).get();
        List<Fisherman> clubMembers = club.getFishermen();

        clubMembers.forEach(f -> {
            Contestant contestant = new Contestant();
            contestant.setClub(club);
            contestant.setFisherman(f);
            contestant.setContest(contest);
            contestantRepo.save(contestant);
        });
        return getContest(contestId, mm);
    }
}
