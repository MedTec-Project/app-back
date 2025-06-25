package br.medtec.features.history;

import lombok.Getter;

public enum HistoryType {
    INSERT("Inserção"), UPDATE("Atualização"), DELETE("Deleção");

    @Getter
    private String description;

    HistoryType(String description) {
        this.description = description;
    }
}
