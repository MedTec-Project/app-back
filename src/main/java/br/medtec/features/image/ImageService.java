package br.medtec.features.image;

public interface ImageService {
    String saveImage(String imageBase64, String medicineName);
     String getImage(String imagePath);
     String convertToBase64(String imagePath);
}
