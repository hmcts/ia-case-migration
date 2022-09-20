package uk.gov.hmcts.reform.iacasemigration.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "uk.gov.hmcts.reform.iacasemigration", lazyInit = true)
@PropertySource("classpath:application.properties")
public class IaCaseMigrationConfig {
}
