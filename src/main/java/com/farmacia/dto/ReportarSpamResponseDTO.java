package com.farmacia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportarSpamResponseDTO {
    private String id;
    private Integer reportesSpam;
    private boolean flaggedAsSpam;
}