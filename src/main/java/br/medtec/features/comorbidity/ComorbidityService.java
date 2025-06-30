package br.medtec.features.comorbidity;

import br.medtec.features.image.ImageService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ComorbidityService {

    private final ComorbidityRepository repository;

    private final ImageService imageService;

    public ComorbidityService(ComorbidityRepository repository, ImageService imageService) {
        this.repository = repository;
        this.imageService = imageService;
    }

    @Transactional
    public ComorbidityDTO registerComorbidity(ComorbidityDTO comorbidityDTO) {
        Comorbidity comorbidity = comorbidityDTO.toEntity();
        repository.save(comorbidity);
        return comorbidity.toDTO();
    }

    @Transactional
    public ComorbidityDTO updateComorbidity(ComorbidityDTO comorbidityDTO, String oid) {
        Comorbidity comorbidity = repository.findByOid(oid);
        comorbidity.validateUser();
        comorbidityDTO.toEntity(comorbidity);
        repository.save(comorbidity);
        return comorbidity.toDTO();
    }

    @Transactional
    public void deleteComorbidity(String oid) {
        Comorbidity comorbidity = repository.findByOid(oid);
        comorbidity.validateUser();
        repository.delete(comorbidity);
    }

    @Transactional
    public List<ComorbidityDTO> getComorbidity() {
        List<ComorbidityDTO> comorbidities = repository.findAllByUser();
        comorbidities.forEach(this::getImage);
        return comorbidities;
    }

    private void getImage(ComorbidityDTO comorbidityDTO) {
        String iconPath = "src/main/resources/static/icons/" + comorbidityDTO.getIcon() + ".svg";
        comorbidityDTO.setImage(imageService.convertToBase64(iconPath));
    }


}
