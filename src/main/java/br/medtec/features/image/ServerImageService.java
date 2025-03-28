package br.medtec.features.image;

import br.medtec.exceptions.MEDExecption;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@ApplicationScoped
@Slf4j
public class ServerImageService implements ImageService {

    private static final String UPLOAD_DIR = "uploads/medicines/";

    public String saveImage(String imageBase64, String medicineName) {
        try {
            if (imageBase64 == null || imageBase64.isEmpty()) {
                return null;
            }

            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String imageName = medicineName.replaceAll("\\s+", "_") + ".jpg";
            File file = new File(UPLOAD_DIR + imageName);


            byte[] imageBytes = Base64.getDecoder().decode(imageBase64);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(imageBytes);
            }

            return "uploads/medicines/" + imageName;
        } catch (IOException e) {
            log.error("Erro ao salvar imagem", e);
            throw new MEDExecption("Não foi possível salvar a imagem");
        }
    }

    @Override
    public String getImage(String imagePath) {
        try {
            byte[] imageBytes = Files.readAllBytes(Path.of(imagePath));
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            log.error("Erro ao ler imagem", e);
            throw new MEDExecption("Não foi possível ler a imagem");
        }
    }

}
