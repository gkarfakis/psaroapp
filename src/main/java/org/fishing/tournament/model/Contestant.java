package org.fishing.tournament.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Contestant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;
    // TODO: 2/4/2023 unique constraint for fisherman contest 
    @ManyToOne(fetch = FetchType.LAZY)
    private Fisherman fisherman;

    @ManyToOne(fetch = FetchType.LAZY)
    private Contest contest;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Sector sector;

//    @OneToOne(mappedBy = "contestant", cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY, optional = false)
//    private SectorPosition sectorPosition;

    private String sector;
    private String position ="0";

    private int seniority = 0;




}
