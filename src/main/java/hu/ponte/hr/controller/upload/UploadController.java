package hu.ponte.hr.controller.upload;

import hu.ponte.hr.services.ImageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequestMapping("api/file")
@RequiredArgsConstructor
public class UploadController {

    private final ImageStore imageStore;

    @PostMapping(value = "post")
    @ResponseBody
    public ResponseEntity<String> handleFormUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String id = imageStore.saveImage(file);
        return ResponseEntity.ok(id);
    }
}
