package br.medtec.features.symptom;

import br.medtec.generics.JpaGenericRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JpaSymptomRepository extends JpaGenericRepository<Symptom> {


    public JpaSymptomRepository() {
        super(Symptom.class);
    }

}
