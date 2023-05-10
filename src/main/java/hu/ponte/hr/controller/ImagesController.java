package hu.ponte.hr.controller;


import hu.ponte.hr.dto.ImageMeta;
import hu.ponte.hr.services.ImageStore;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/images")
@RequiredArgsConstructor
public class ImagesController {

    private final ImageStore imageStore;

    @GetMapping("meta")
    public List<ImageMeta> listImages() {
        return imageStore.listImages();
    }

    @GetMapping(name = "preview/{id}", produces = "image/png")
    public @ResponseBody ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
        Optional<byte[]> optionalImage = imageStore.getImage(id);
        return optionalImage.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

}
