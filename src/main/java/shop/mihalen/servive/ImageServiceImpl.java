package shop.mihalen.servive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import shop.mihalen.entity.ImageModel;
import shop.mihalen.repository.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService{
    @Autowired
    private ImageRepository imageRepository;
    @Override
    public ResponseEntity<?> removeImage(Long id) {
        ImageModel existingImage = imageRepository.findById(id).orElse(null);
        if(existingImage == null){
            return ResponseEntity.badRequest().body("Image not found");
        }
        imageRepository.deleteById(id);
        return ResponseEntity.ok("Image removed successfully");
 }
    @Override
    public ResponseEntity<byte[]> findById(Long id) {
        ImageModel existingImage = imageRepository.findById(id).orElse(null);
        if(existingImage == null){
            return ResponseEntity.badRequest().body(new byte[0]);
        }
        byte[] image = existingImage.getPicByte();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(existingImage.getType())); // Convert the String to MediaType
        headers.setContentLength(image.length);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

}
