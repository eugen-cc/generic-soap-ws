package cc.eugen.backend.soap.xdataservice.repository;

import cc.eugen.backend.soap.xdataservice.entity.XMetadataEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface XMetadataRepository extends CrudRepository<XMetadataEntity, Long> {
    Optional<XMetadataEntity> findByDataKeyAndDataValue(@Param("dataKey") String key, @Param("dataValue") String value);
}
