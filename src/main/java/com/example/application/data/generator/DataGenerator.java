package com.example.application.data.generator;

import com.example.application.data.entity.ServicePackage;
import com.example.application.data.service.ServicePackageRepository;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(ServicePackageRepository servicePackageRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (servicePackageRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 100 Service Package entities...");
            ExampleDataGenerator<ServicePackage> servicePackageRepositoryGenerator = new ExampleDataGenerator<>(
                    ServicePackage.class, LocalDateTime.of(2021, 12, 6, 0, 0, 0));
            servicePackageRepositoryGenerator.setData(ServicePackage::setId, DataType.ID);
            servicePackageRepositoryGenerator.setData(ServicePackage::setIcon, DataType.BOOK_IMAGE_URL);
            servicePackageRepositoryGenerator.setData(ServicePackage::setName, DataType.TWO_WORDS);
            servicePackageRepository.saveAll(servicePackageRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }

}