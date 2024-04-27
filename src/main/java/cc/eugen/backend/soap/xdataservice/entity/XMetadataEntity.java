package cc.eugen.backend.soap.xdataservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "XMetadata")
public class XMetadataEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dataId;

    @Column
    private String dataKey;

    @Column
    private String dataValue;

    @ManyToMany(mappedBy = "XMetadata", fetch = FetchType.EAGER)
    private Set<XDataEntity> XDataEntities = new HashSet<>();

}
