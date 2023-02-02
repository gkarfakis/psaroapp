package org.fishing.tournament.services;

import org.fishing.tournament.model.Club;
import org.fishing.tournament.repositories.ClubRepo;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubService {

    private final ClubRepo clubRepo;

    public ClubService(ClubRepo clubRepo) {
        this.clubRepo = clubRepo;
    }

    public List<Club> getAllClubsAlphabetic() {
        return clubRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(Club::getClubName))
                .collect(Collectors.toList());
    }
}
