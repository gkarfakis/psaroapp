package org.fishing.tournament.controllers;

import org.fishing.tournament.model.*;
import org.fishing.tournament.repositories.ContestRepo;
import org.fishing.tournament.repositories.ContestantRepo;
import org.fishing.tournament.repositories.SectorPositionRepo;
import org.fishing.tournament.repositories.SectorRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class LotteryController {

    private final ContestRepo contestRepo;
    private final ContestantRepo contestantRepo;
    private final SectorRepo sectorRepo;
    private final SectorPositionRepo sectorPositionRepo;

    public LotteryController(ContestRepo contestRepo, ContestantRepo contestantRepo, SectorRepo sectorRepo, SectorPositionRepo sectorPositionRepo) {
        this.contestRepo = contestRepo;
        this.contestantRepo = contestantRepo;
        this.sectorRepo = sectorRepo;
        this.sectorPositionRepo = sectorPositionRepo;
    }


    @PostMapping("/restlottery")
    public List<Contestant> runLottery(@RequestParam("contestId") Long contestId) {
        Contest contest = contestRepo.findById(contestId).get();
        int sectorsNum = sectorRepo.findByContestId(contestId).size();
        List<Contestant> allContestants = contest.getContestants();
        int limaNum = (int) allContestants.stream().filter(x -> x.getSeniority() != 0).count();
        int limaGroups = limaNum / sectorsNum;
        // get clubs contestants map
        Map<Club, List<Contestant>> clubsMap = getMapForContestantsAsc(allContestants);

        List<Club> reverseOrderedKeys = new ArrayList<>(clubsMap.keySet());
        Collections.reverse(reverseOrderedKeys);

        // get all positions
        List<String> positions = getAllSectorPositions(contestId);

        assignPositionsToSeniorContestants(sectorsNum, reverseOrderedKeys, contestId, clubsMap, positions);

        List<Contestant> limaList = getLimaList(contest);
        Map<Club, List<Contestant>> limaMap = getMapForContestantsAsc(limaList);

        List<Club> limaReverseKeys = new ArrayList<>(limaMap.keySet());
        Collections.reverse(limaReverseKeys);

        assignPositionsToLima(sectorsNum, limaReverseKeys, contestId, limaMap, positions);

        List<String> techPositions = getTechPositions(contestId);
        assignLeftOversToTechnical(contest, positions, techPositions);


        return contestantRepo.findAll();
    }

    private void assignLeftOversToTechnical(Contest contest, List<String> positions, List<String> techPositions) {
        List<Contestant> leftOvers = contestantRepo.findByContest(contest).stream()
                .filter(x -> x.getPosition().equals("0"))
                .collect(Collectors.toList());

        if (!positions.isEmpty()) {
            for (Iterator<String> iterator = positions.iterator(); iterator.hasNext(); ) {
                String position = iterator.next();
                iterator.remove();
                Contestant contestant = getRandomContestantListElement(leftOvers);
                if (contestant != null) {
                    leftOvers.remove(contestant);
                    contestant.setPosition(position);
                    contestantRepo.save(contestant);
                }
            }
        }

        if (!techPositions.isEmpty()) {
            for (Iterator<String> iterator = techPositions.iterator(); iterator.hasNext(); ) {
                String position = iterator.next();
                iterator.remove();
                Contestant contestant = getRandomContestantListElement(leftOvers);
                if (contestant != null) {
                    leftOvers.remove(contestant);
                    contestant.setPosition(position);
                    contestantRepo.save(contestant);
                }
            }
        }
    }

    private List<String> getTechPositions(Long contestId) {
        return sectorRepo.findByContestIdAndTechnical(contestId, true)
                .get(0).getPositions()
                .stream()
                .map(SectorPosition::getPosition)
                .collect(Collectors.toList());

    }

    private void assignPositionsToLima(int sectorsNum, List<Club> limaReverseKeys, Long contestId, Map<Club, List<Contestant>> limaMap, List<String> positions) {
        for (int i = 1; i <= sectorsNum; i++) {
            for (Club club : limaReverseKeys) {
                // get Set of Sector names
                List<String> sectorNames = getNonTechnicalSectorNames(contestId);
                List<Contestant> clubLima = limaMap.get(club);
                if (!clubLima.isEmpty()) {
                    for (Contestant contestant : clubLima) {
                        String randomSector = getRandomStringListElement(sectorNames);
                        if (!randomSector.equals("null")) {
                            Long sectorId = sectorRepo.findByContestIdAndSectorName(contestId, randomSector).getId();
                            sectorNames.remove(randomSector);
                            List<String> positionNames = getPotitionNames(randomSector).stream()
                                    .filter(x -> positions.contains(x))
                                    .collect(Collectors.toList());
                            String randomPosition = getRandomStringListElement(positionNames);
                            if (!randomPosition.equals("null")) {
                                positions.remove(randomPosition);
                                SectorPosition position = sectorPositionRepo.findBySector_IdAndPosition(sectorId, randomPosition);
                                contestant.setPosition(position.getPosition());
                                contestantRepo.save(contestant);
                            }
                        }
                    }
                }
            }
        }
    }

    private Map<Club, List<Contestant>> getMapForContestantsAsc(List<Contestant> contestants) {
        return contestants.stream()
                .collect(Collectors.groupingBy(Contestant::getClub)).entrySet()
                .stream()
                .sorted((Comparator.comparingInt(e -> e.getValue().size())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private List<Contestant> getLimaList(Contest contest) {
        return contest.getContestants()
                .stream()
                .filter(c -> c.getPosition().equals("0"))
                .collect(Collectors.toList());
    }

    private void assignPositionsToSeniorContestants(int sectorsNum,
                                                    List<Club> reverseOrderedKeys,
                                                    Long contestId,
                                                    Map<Club, List<Contestant>> clubsMap,
                                                    List<String> positions) {
        for (int i = 1; i <= sectorsNum; i++) {
            for (Club club : reverseOrderedKeys) {
                // get Set of Sector names
                List<String> sectorNames = getNonTechnicalSectorNames(contestId);


                int finalI = i;
                List<Contestant> contestantsForSeniority = clubsMap.get(club).stream()
                        .filter(x -> x.getSeniority() == finalI)
                        .collect(Collectors.toList());

                for (Contestant contestant : contestantsForSeniority) {
                    String randomSector = getRandomStringListElement(sectorNames);
                    if (!randomSector.equals("null")) {
                        Long sectorId = sectorRepo.findByContestIdAndSectorName(contestId, randomSector).getId();
                        sectorNames.remove(randomSector);
                        List<String> positionNames = getPotitionNames(randomSector).stream()
                                .filter(x -> positions.contains(x))
                                .collect(Collectors.toList());
                        String randomPosition = getRandomStringListElement(positionNames);
                        if (!randomPosition.equals("null")) {
                            positions.remove(randomPosition);
                            SectorPosition position = sectorPositionRepo.findBySector_IdAndPosition(sectorId, randomPosition);
                            contestant.setPosition(position.getPosition());
                            contestantRepo.save(contestant);
                        }
                    }
                }
            }
        }
    }

    private List<String> getNonTechnicalSectorNames(Long contestId) {
        return sectorRepo.findByContestId(contestId).stream()
                .filter(s -> !s.isTechnical())
                .map(Sector::getSectorName)
                .collect(Collectors.toList());
    }

    private List<String> getTechnicalSectorName(Long contestId) {
        return sectorRepo.findByContestId(contestId).stream()
                .filter(s -> s.isTechnical())
                .map(Sector::getSectorName)
                .collect(Collectors.toList());
    }

    private List<String> getPotitionNames(String randomSector) {
        return sectorPositionRepo.findBySector_SectorName(randomSector).stream()
                .map(SectorPosition::getPosition)
                .collect(Collectors.toList());
    }


    private List<String> getAllSectorPositions(Long contestId) {
        List<SectorPosition> positions = new ArrayList<>();
        sectorRepo.findByContestId(contestId).forEach(x -> positions.addAll(x.getPositions()));
        return positions
                .stream()
                .map(SectorPosition::getPosition)
                .collect(Collectors.toList());
    }

    private String getRandomStringListElement(List<String> list) {
        if (list.isEmpty())
            return "null";
        return list.get(new Random().nextInt(list.size()));
    }

    private Contestant getRandomContestantListElement(List<Contestant> list) {
        if (list.isEmpty())
            return null;
        return list.get(new Random().nextInt(list.size()));
    }
}
