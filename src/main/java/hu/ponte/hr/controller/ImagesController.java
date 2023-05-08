package hu.ponte.hr.controller;


import hu.ponte.hr.services.ImageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("api/images")
public class ImagesController {

    @Autowired
    private ImageStore imageStore;

    @GetMapping("meta")
    public List<ImageMeta> listImages() {
        return imageStore.listImages();
    }

    @GetMapping("preview/{id}")
    public @ResponseBody ResponseEntity<byte[]> getImage(@PathVariable("id") String id, HttpServletResponse response) {
        Optional<byte[]> optionalImage = imageStore.getImage(id);
        return optionalImage.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

}
