package br.medtec.features.comorbidade;

import br.medtec.generics.JpaGenericRepository;

public class JpaComorbidadeRepository extends JpaGenericRepository<Comorbidade> implements ComorbidadeRepository {

    public JpaComorbidadeRepository() {
        super(Comorbidade.class);
    }


}
