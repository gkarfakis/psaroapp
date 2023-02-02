package org.fishing.tournament.services;

import org.fishing.tournament.model.Contest;
import org.fishing.tournament.model.Contestant;
import org.fishing.tournament.repositories.ContestantRepo;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContestantService {

    private final ContestantRepo contestantRepo;

    public ContestantService(ContestantRepo contestantRepo) {
        this.contestantRepo = contestantRepo;
    }

    public List<Contestant> getAllContestantsForContest(Contest contest) {
        return contestantRepo.findByContest(contest)
//                .stream().sorted(Comparator.comparing(Contestant::getFisherman))
//                .collect(Collectors.toList())
                ;
    }
}
