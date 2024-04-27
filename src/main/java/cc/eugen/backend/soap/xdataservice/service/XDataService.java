package cc.eugen.backend.soap.xdataservice.service;

import cc.eugen.backend.soap.xdataservice.entity.XDataEntity;
import cc.eugen.backend.soap.xdataservice.generated.MetadataType;
import cc.eugen.backend.soap.xdataservice.generated.XData;
import cc.eugen.backend.soap.xdataservice.repository.XDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@Slf4j
public class XDataService {

    @Autowired
    private XDataRepository repository;

    @Value("${webservice.read.timeout.ms:0}")
    private long delay;

    private void delay() {
        if (delay == 0) return;

        log.debug("Custom delay. Wait for {} ms.", delay);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public XData getById(long id) {
        delay();
        return repository.findById(id).map(this::mapData).orElseThrow();
    }

    public List<XData> getAll() {
        delay();
        final var list = new ArrayList<XData>();
        repository.findAll().forEach(entity -> list.add(mapData(entity)));
        return list;
    }

    public List<XData> getByXMetadata(String key, String value) {
        delay();
        final var list = new ArrayList<XData>();
        repository.findByXMetadataKeyAndValue(key, value).forEach(entity -> list.add(mapData(entity)));

        return list;
    }

    private XData mapData(XDataEntity entity) {
        final var data = new XData();
        data.setId(entity.getId());
        data.setName(entity.getName());
        data.setDescription(entity.getDescription());
        entity.getXMetadata().stream().map(xMetadata -> {

            var kv = new MetadataType();
            kv.setKey(xMetadata.getDataKey());
            kv.setValue(xMetadata.getDataValue());

            return kv;
        }).forEach(mapped -> data.getXMetadata().add(mapped));
        return data;
    }

    private static XMLGregorianCalendar getCurrentTimestamp() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date());
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            log.error("Cannot  get Date", e);
            return null;
        }

    }

}
