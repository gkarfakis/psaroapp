package org.fishing.tournament.repositories;

import org.fishing.tournament.model.Contest;
import org.fishing.tournament.model.Contestant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestantRepo extends JpaRepository<Contestant, Long> {

    List<Contestant> findByContest(Contest contest);
}
