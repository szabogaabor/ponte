package hu.ponte.hr.services;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageStore {

    private final ImageRepository imageRepository;
    private final SignService signService;

    public String saveImage(final MultipartFile file) throws IOException {
        ImageMeta imageMeta = ImageMeta.builder()
                .size(file.getSize())
                .name(file.getName())
                .mimeType(file.getContentType())
                .digitalSign(signService.signImage(file.getBytes()))
                .build();
        return imageRepository.save(imageMeta).getId();
    }

    public List<ImageMeta> listImages() {
        return imageRepository.findAll();
    }

    public Optional<byte[]> getImage(String id) {
        Optional<ImageMeta> optionalImageMeta = imageRepository.findById(id);
        if (optionalImageMeta.isEmpty()) {
            return Optional.empty();
        }
        ImageMeta imageMeta = optionalImageMeta.get();
        String digitalSign = imageMeta.getDigitalSign();
        return Optional.of(signService.decodeImage(digitalSign.getBytes()));
    }
}
