package hu.ponte.hr.controller;

import lombok.*;

import javax.persistence.*;

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
    @Column(length = 5000000)
    private String digitalSign;

}
