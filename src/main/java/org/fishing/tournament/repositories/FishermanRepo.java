package org.fishing.tournament.repositories;

import org.fishing.tournament.model.Fisherman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishermanRepo extends JpaRepository<Fisherman, Long> {
}
