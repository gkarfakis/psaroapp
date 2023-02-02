package org.fishing.tournament.repositories;

import org.fishing.tournament.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorRepo extends JpaRepository<Sector, Long> {

    List<Sector> findByContestIdAndTechnical(Long contestId, boolean technical);

    List<Sector> findByContestId(Long contestId);

    Sector findByContestIdAndSectorName(Long contestId, String contestName);
}
