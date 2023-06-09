package hu.ponte.hr.services;

import hu.ponte.hr.dto.ImageMeta;
import hu.ponte.hr.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageStore {

    private final ImageRepository imageRepository;
    private final SignService signService;

    /**
     * Saving the image, and returning the ID of it.
     *
     * @param file
     * @return ID of the file saved to database.
     * @throws IOException if the file is corrupted, and cannot get the bytes of it.
     */
    public String saveImage(final MultipartFile file) throws IOException {
        ImageMeta imageMeta = ImageMeta.builder()
                .size(file.getSize())
                .name(file.getOriginalFilename())
                .mimeType(file.getContentType())
                .digitalSign(signService.signAndEncodeImage(file.getBytes()))
                .build();
        return imageRepository.save(imageMeta).getId();
    }

    public List<ImageMeta> listImages() {
        return imageRepository.findAll();
    }

    public Optional<byte[]> getImage(String id) {
        Optional<ImageMeta> optionalImageMeta = imageRepository.findById(id);
        if (optionalImageMeta.isEmpty()) {
            log.info("Could not find any image with id {}", id);
            return Optional.empty();
        }
        ImageMeta imageMeta = optionalImageMeta.get();
        return Optional.of(signService.decodeImage(imageMeta.getDigitalSign()));
    }
}
