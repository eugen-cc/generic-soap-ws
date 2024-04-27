package cc.eugen.backend.soap.xdataservice;

import cc.eugen.backend.soap.xdataservice.entity.XMetadataEntity;
import cc.eugen.backend.soap.xdataservice.entity.XDataEntity;
import cc.eugen.backend.soap.xdataservice.repository.XDataRepository;
import cc.eugen.backend.soap.xdataservice.repository.XMetadataRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.session.JdbcSessionDataSourceScriptDatabaseInitializer;

import java.util.Random;
import java.util.random.RandomGenerator;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private XDataRepository xDataRepo;
    @Autowired
    private XMetadataRepository xMetadataRepo;

    private static final String LOREM_IPSUM = """
            Lorem ipsum dolor sit amet, consetetur sadipscing elitr, 
            sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, 
            sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.
            """;

    @PostConstruct
    public void prefillData() {

        log.info("PREFILLING TEST DATA ...");
        log.info("... PLEASE WAIT ...");
        for (int i = 0; i < 300; i++) {

            var randomInt = new Random().ints(1, 100).findFirst().getAsInt();
            log.debug("findRandomNumber");
            var random = xMetadataRepo.findByDataKeyAndDataValue("randomNumber", String.valueOf(randomInt)).orElseGet(() -> createRandomNumberMetadata(randomInt));
            log.debug("findAuthor");
            var created = xMetadataRepo.findByDataKeyAndDataValue("author", "Eugen").orElseGet(this::createAuthorMetadata);

            var xData = new XDataEntity();
            xData.setName("xData_" + (i + 1));
            xData.setDescription(LOREM_IPSUM.substring(randomInt));

            xData = xDataRepo.save(xData);
            xData.getXMetadata().add(random);
            xData.getXMetadata().add(created);
            xDataRepo.save(xData);

        }
        log.info("PREFIL COMPLETED");

    }

    private XMetadataEntity createAuthorMetadata() {
        log.debug("Create Author Eugen");
        var author = new XMetadataEntity();
        author.setDataKey("author");
        author.setDataValue("Eugen");
        return xMetadataRepo.save(author);
    }

    private XMetadataEntity createRandomNumberMetadata(int randomInt) {
        log.debug("create random entry for {}", randomInt);
        var xrandom = new XMetadataEntity();
        xrandom.setDataKey("randomNumber");
        xrandom.setDataValue("" + randomInt);
        return xMetadataRepo.save(xrandom);
    }
}
