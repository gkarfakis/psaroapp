package org.fishing.tournament.controllers;

import org.fishing.tournament.model.*;
import org.fishing.tournament.payloads.GenerateSectorsPayload;
import org.fishing.tournament.repositories.ContestRepo;
import org.fishing.tournament.repositories.ContestantRepo;
import org.fishing.tournament.services.ClubService;
import org.fishing.tournament.services.ContestantService;
import org.fishing.tournament.services.FishermanService;
import org.fishing.tournament.services.SectorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class SectorController {

    private final ContestRepo contestRepo;
    private final ContestantRepo contestantRepo;
    private final ContestantService contestantService;
    private final ClubService clubService;
    private final FishermanService fishermanService;
    private final SectorService sectorService;

    public SectorController(ContestRepo contestRepo, ContestantRepo contestantRepo, ContestantService contestantService, ClubService clubService, FishermanService fishermanService, SectorService sectorService) {
        this.contestRepo = contestRepo;
        this.contestantRepo = contestantRepo;
        this.contestantService = contestantService;
        this.clubService = clubService;
        this.fishermanService = fishermanService;
        this.sectorService = sectorService;
    }

    @PostMapping("/sectorize")
    public String create(ModelMap mm, HttpServletRequest request) {
        Long contestId = Long.valueOf(request.getParameter("contestId"));
        int positionsNum = Integer.valueOf(request.getParameter("positionsNum"));

        Contest contest = contestRepo.findById(contestId).get();

        GenerateSectorsPayload generateSectorsPayload = sectorService.generateSectors(contest, positionsNum);
        int sectorsNum = generateSectorsPayload.getSectorsNum();
        int techSectorPositions = generateSectorsPayload.getTechSectorPositions();

        List<Contestant> allContestants = contestantService.getAllContestantsForContest(contest);
        List<Club> clubs = clubService.getAllClubsAlphabetic();
        List<Fisherman> fishermen = fishermanService.getFishermenAlphabetically();

        mm.addAttribute("sectorsNum", sectorsNum);
        mm.addAttribute("techSectorPositions", techSectorPositions);
        mm.addAttribute("contestants", allContestants);
        mm.addAttribute("clubs", clubs);
        mm.addAttribute("fishermen", fishermen);
        mm.addAttribute("positionsNum", positionsNum);

        return "contestants";

    }

    @PostMapping("/sectorize2")
    public void create(@RequestParam("contestId") Long contestId, @RequestParam("positionsNum") int positionsNum ) {

        Contest contest = contestRepo.findById(contestId).get();

        GenerateSectorsPayload generateSectorsPayload = sectorService.generateSectors(contest, positionsNum);
        int sectorsNum = generateSectorsPayload.getSectorsNum();
        int techSectorPositions = generateSectorsPayload.getTechSectorPositions();

        System.out.println(sectorsNum + "------" + techSectorPositions);

    }

    @PostMapping("/lottery")
    public String lottery(
            @RequestParam("contestId") Long contestId,
            @RequestParam("positionsNum") int positionsNum
//            ModelMap mm, HttpServletRequest request
    ) {
//        Long contestId = Long.valueOf(request.getParameter("contestId"));
//        int positionsNum = Integer.valueOf(request.getParameter("positionsNum"));
//        int sectorsNum = Integer.valueOf(request.getParameter("sectorsNum"));
//        List<Sector> sectors = createSectors(contestId);

        // get sectors
        List<Sector> sectors = sectorService.getSectorsForContest(contestId, false);
        Sector technicalSector = sectorService.getTechnicalSector(contestId);

        // get contest
        Contest contest = contestRepo.findById(contestId).get();

        // get club-contestants map
        List<Contestant> allContestants = contest.getContestants();
        Map<Club, List<Contestant>> clubsMap = allContestants.stream()
                .collect(Collectors.groupingBy(Contestant::getClub)).entrySet()
                .stream()
                .sorted((Comparator.comparingInt(e -> e.getValue().size())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));


        List<Club> reverseOrderedKeys = new ArrayList<>(clubsMap.keySet());
        Collections.reverse(reverseOrderedKeys);
        for (int i = 1; i < sectors.size() + 1; i++) {
            for (Club club : reverseOrderedKeys) {
                System.out.println(club.getClubName() + ":" + clubsMap.get(club).size());

                int finalI = i;
                List<Contestant> psarades = clubsMap.get(club).stream().filter(x -> x.getSeniority() == finalI).collect(Collectors.toList());
                Random randomizer = new Random();
                Contestant klirotheis = psarades.get(randomizer.nextInt(psarades.size()));
                klirotheis.setSector(String.valueOf(i));
                contestantRepo.save(klirotheis);
            }
        }


//        List<Club> clubs = clubService.getAllClubsAlphabetic();
//        List<Fisherman> fishermen = fishermanService.getFishermenAlphabetically();
//        int sectorsNum = contestants / positionsNum;
//        System.out.println("sectorsNum: " + sectorsNum);
//        int techSectorPositions = contestants % positionsNum;
//        System.out.println("techSectorPositions: " + techSectorPositions);
//
//        mm.addAttribute("sectorsNum", sectorsNum);
//        mm.addAttribute("techSectorPositions", techSectorPositions);
//        mm.addAttribute("contestants", allContestants);
//        mm.addAttribute("clubs", clubs);
//        mm.addAttribute("fishermen", fishermen);
//        mm.addAttribute("positionsNum", positionsNum);

        return "contestants";

    }

//    Comparator<List<Contestant>> lengthComparator = new Comparator<List<Contestant>>() {
//        public int compare(List<Contestant> a, List<Contestant> b) {
//            return a.size() - b.size();
//            // size() is always nonnegative, so this won't have crazy overflow bugs
//        }
//    };
}
