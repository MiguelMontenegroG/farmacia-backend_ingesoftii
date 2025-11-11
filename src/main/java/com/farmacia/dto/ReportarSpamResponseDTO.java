package com.farmacia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportarSpamResponseDTO {
    private String id;
    private Integer reportesSpam;
    private boolean flaggedAsSpam;

    public ReportarSpamResponseDTO(String id, Integer reportesSpam, boolean flaggedAsSpam) {
        this.id = id;
        this.reportesSpam = reportesSpam;
        this.flaggedAsSpam = flaggedAsSpam;
    }
}