package hu.ponte.hr.repositories;

import hu.ponte.hr.controller.ImageMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageMeta, String> {
}
