package org.fishing.tournament.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@ToString(exclude = "positions")
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private String sectorName;

    private int positionsNum;

    private Long contestId;

    private boolean technical = false;

//    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Contestant> contestants = new HashSet<>();

    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SectorPosition> positions = new ArrayList<>();

    public void addPosition(SectorPosition position) {
        positions.add(position);
        position.setSector(this);
    }

    public void removePosition(SectorPosition position) {
        positions.remove(position);
        position.setSector(null);
    }
}
