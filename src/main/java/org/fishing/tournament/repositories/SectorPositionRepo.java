package org.fishing.tournament.repositories;

import org.fishing.tournament.model.SectorPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorPositionRepo extends JpaRepository<SectorPosition, Long> {

    List<SectorPosition> findBySector_SectorName(String sectorName);

    SectorPosition findBySector_IdAndPosition(Long sectorId, String position);
}
