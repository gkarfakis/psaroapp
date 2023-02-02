package org.fishing.tournament.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
//@ToString(exclude = "contestant")
public class SectorPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sector sector;


//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "contestant_id")
//    private Contestant contestant;
}
