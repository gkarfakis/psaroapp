package org.fishing.tournament.repositories;

import org.fishing.tournament.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepo extends JpaRepository<Club, Long> {

}
