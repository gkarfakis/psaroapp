package org.fishing.tournament.services;

import org.fishing.tournament.model.Fisherman;
import org.fishing.tournament.repositories.FishermanRepo;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FishermanService {

    private final FishermanRepo fishermanRepo;

    public FishermanService(FishermanRepo fishermanRepo) {
        this.fishermanRepo = fishermanRepo;
    }

    public List<Fisherman> getFishermenAlphabetically() {
        return fishermanRepo.findAll().
                stream().sorted(Comparator.comparing(Fisherman::getFishermanSurName))
                .collect(Collectors.toList());
    }
}
