package hu.ponte.hr.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zoltan
 */
@Builder
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String mimeType;
    private long size;
    @Column(length = 2_000_000)
    private String digitalSign;

}
