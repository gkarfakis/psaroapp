package org.fishing.tournament.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "contestants")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    // TODO: 1/29/2023 add unique constraint db 
    private String clubName;

    public Club(String clubName) {
        this.clubName = clubName;
    }

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Contestant> contestants = new ArrayList<>();
}
