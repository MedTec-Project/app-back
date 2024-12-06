package br.medtec.features.comorbidade;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ComorbidadeService {



    public Comorbidade cadastrarComorbidade(ComorbidadeDTO comorbidadeDTO) {
        Comorbidade comorbidade = comorbidadeDTO.toEntity();
        c
    }
}
