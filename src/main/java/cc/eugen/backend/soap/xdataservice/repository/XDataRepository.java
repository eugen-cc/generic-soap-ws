package cc.eugen.backend.soap.xdataservice.repository;

import cc.eugen.backend.soap.xdataservice.entity.XDataEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface XDataRepository extends CrudRepository<XDataEntity, Long> {
    @Query("SELECT d FROM XDataEntity d JOIN d.XMetadata ad WHERE ad.dataKey = :key AND ad.dataValue = :value")
    List<XDataEntity> findByXMetadataKeyAndValue(@Param("key") String key, @Param("value") String value);
}
