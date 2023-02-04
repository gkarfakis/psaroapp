package org.fishing.tournament.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "contestants")
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private LocalDate contestDate;

    private String description;

    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contestant> contestants = new ArrayList<>();

    public Contest(LocalDate contestDate) {
        this.contestDate = contestDate;
    }

    public Contest(Long id, LocalDate contestDate) {
        this.id = id;
        this.contestDate = contestDate;
    }

//    public void addClub(Club club) {
//        clubs.add(club);
//        club.getContests().add(this);
//    }
//
//    public void removeClub(Club club) {
//        clubs.remove(club);
//        club.getContests().remove(this);
//    }

}
