package hu.ponte.hr.repositories;

import hu.ponte.hr.dto.ImageMeta;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<ImageMeta, String> {
}
