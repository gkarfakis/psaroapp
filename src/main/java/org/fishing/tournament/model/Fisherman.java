package org.fishing.tournament.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "contestants")
public class Fisherman  implements Comparable<Fisherman> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private String fishermanName;
    private String fishermanSurName;

    // TODO: 1/29/2023 unique constraint fisherman name 
    public Fisherman(String fishermanName, String fishermanSurName) {
        this.fishermanName = fishermanName;
        this.fishermanSurName = fishermanSurName;
    }

    @OneToMany(mappedBy = "fisherman", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contestant> contestants = new ArrayList<>();


    @Override
    public int compareTo(Fisherman f) {
        return this.fishermanSurName.compareTo(f.fishermanSurName);
    }
}
