package org.fishing.tournament.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateSectorsPayload {
    private int sectorsNum;
    private int techSectorPositions;
}
