package com.bookverseApp.bookverse.images;

import com.bookverseApp.bookverse.exceptions.ImageNotFoundException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin(origins = "*")
public class ImageResource {
    @GetMapping("images/{imgName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imgName) {
        String imagePath = "src/main/resources/static/images/" + imgName + ".jpeg";
        Path filePath = Paths.get(imagePath);
        if (!filePath.toFile().exists()) {
            imagePath = "src/main/resources/static/images/" + imgName + ".png";
            filePath = Paths.get(imagePath);
            if(!filePath.toFile().exists()){
                throw new ImageNotFoundException(imgName);
            }
        }
        Resource photo = (Resource) new FileSystemResource(imagePath);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return ResponseEntity.ok()
                .headers(headers)
                .body(photo);
    }
}
