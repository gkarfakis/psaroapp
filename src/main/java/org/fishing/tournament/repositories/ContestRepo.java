package org.fishing.tournament.repositories;

import org.fishing.tournament.model.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestRepo extends JpaRepository<Contest, Long> {
}
