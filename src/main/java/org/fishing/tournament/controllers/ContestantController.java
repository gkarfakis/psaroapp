package org.fishing.tournament.controllers;

import org.fishing.tournament.model.Club;
import org.fishing.tournament.model.Contest;
import org.fishing.tournament.model.Contestant;
import org.fishing.tournament.model.Fisherman;
import org.fishing.tournament.repositories.ClubRepo;
import org.fishing.tournament.repositories.ContestRepo;
import org.fishing.tournament.repositories.ContestantRepo;
import org.fishing.tournament.repositories.FishermanRepo;
import org.fishing.tournament.services.ContestantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ContestantController {

    private final ContestRepo contestRepo;
    private final ClubRepo clubRepo;
    private final FishermanRepo fishermanRepo;
    private final ContestantRepo contestantRepo;
    private final ContestantService contestantService;

    public ContestantController(ContestRepo contestRepo, ClubRepo clubRepo, FishermanRepo fishermanRepo, ContestantRepo contestantRepo, ContestantService contestantService) {
        this.contestRepo = contestRepo;
        this.clubRepo = clubRepo;
        this.fishermanRepo = fishermanRepo;
        this.contestantRepo = contestantRepo;
        this.contestantService = contestantService;
    }

    @PostMapping("/contestant")
    public String createContest(ModelMap mm, HttpServletRequest request) {
        Long contestId = Long.valueOf(request.getParameter("contestId"));
        Contest contest = contestRepo.findById(contestId).get();
        Long clubId = Long.valueOf(request.getParameter("clubId"));
        Club club = clubRepo.findById(clubId).get();
        Long fishermanId = Long.valueOf(request.getParameter("fishermanId"));
        Fisherman fisherman = fishermanRepo.findById(fishermanId).get();

        Contestant contestant = new Contestant();
        contestant.setClub(club);
        contestant.setFisherman(fisherman);
        contestant.setContest(contest);
        contestant = contestantRepo.save(contestant);

        List<Contestant> contestants = contestantService.getAllContestantsForContest(contest);
        List<Club> clubs = clubRepo.findAll();
        List<Fisherman> fishermen = fishermanRepo.findAll();

//        mm.addAttribute("contest", contest);
        mm.addAttribute("contest", contest);
        mm.addAttribute("contestants", contestants);
        mm.addAttribute("clubs", clubs);
        mm.addAttribute("fishermen", fishermen);
        mm.addAttribute("totalNum", contestants.size());
        return "contestants";
    }
}
