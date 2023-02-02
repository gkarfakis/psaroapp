package org.fishing.tournament.services;

import org.fishing.tournament.model.Contest;
import org.fishing.tournament.model.Sector;
import org.fishing.tournament.model.SectorPosition;
import org.fishing.tournament.payloads.GenerateSectorsPayload;
import org.fishing.tournament.repositories.SectorPositionRepo;
import org.fishing.tournament.repositories.SectorRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SectorService {

    private final SectorRepo sectorRepo;
    private final SectorPositionRepo positionRepo;

    public SectorService(SectorRepo sectorRepo, SectorPositionRepo positionRepo) {
        this.sectorRepo = sectorRepo;
        this.positionRepo = positionRepo;
    }

    public GenerateSectorsPayload generateSectors(Contest contest, int positionsNum) {
        int contestants = contest.getContestants().size();

        int sectorsNum = contestants / positionsNum;
        int techSectorPositions = contestants % positionsNum;
        List<Sector> sectors = new ArrayList<>();
        for (int i = 0; i < sectorsNum; i++) {
            Sector sector = new Sector();
            sector.setSectorName(mapNumberToLetter(i));
            sector.setContestId(contest.getId());
            sector.setPositionsNum(positionsNum);
            sector.setTechnical(false);
            sectors.add(sectorRepo.save(sector));
        }
        if (techSectorPositions > 0) {
            Sector sector = new Sector();
            sector.setSectorName(mapNumberToLetter(sectorsNum));
            sector.setContestId(contest.getId());
            sector.setPositionsNum(techSectorPositions);
            sector.setTechnical(true);
            sectors.add(sectorRepo.save(sector));
        }
        createSectorPositions(contest.getId());
        System.out.println(Arrays.toString(sectors.toArray()));
        return new GenerateSectorsPayload(sectorsNum, techSectorPositions);
    }

    private void createSectorPositions(Long id) {
        List<Sector> sectors = sectorRepo.findByContestId(id);
        for (Sector sector : sectors) {
            for (int i = 1; i < sector.getPositionsNum() + 1; i++) {
                SectorPosition position = new SectorPosition();
                position.setSector(sector);
                position.setPosition(sector.getSectorName() + i);
                positionRepo.save(position);
            }
        }
    }

    public List<Sector> getSectorsForContest(Long contestId, boolean technical) {
        return sectorRepo.findByContestIdAndTechnical(contestId, technical);
    }

    private String mapNumberToLetter(int i) {
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(i, i + 1);
    }

    public Sector getTechnicalSector(Long contestId) {
        Sector technicalSector = null;
        List<Sector> technicalSectors = getSectorsForContest(contestId, true);
        if (!technicalSectors.isEmpty())
            technicalSector = technicalSectors.get(0);
        return technicalSector;
    }
}
