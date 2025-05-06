package com.architecture.backend_architecture.dto;

import lombok.Data;

@Data
public class AdjuntoDTO {
    private Integer id;
    private String fileName;

    public AdjuntoDTO(Integer id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }
}
