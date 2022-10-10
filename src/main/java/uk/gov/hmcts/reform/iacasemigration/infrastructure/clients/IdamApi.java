package uk.gov.hmcts.reform.iacasemigration.infrastructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import uk.gov.hmcts.reform.iacasemigration.infrastructure.clients.model.idam.Token;
import uk.gov.hmcts.reform.iacasemigration.infrastructure.clients.model.idam.UserInfo;
import uk.gov.hmcts.reform.iacasemigration.infrastructure.config.DisableHystrixFeignConfiguration;
import uk.gov.hmcts.reform.iacasemigration.infrastructure.config.FeignConfiguration;

import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@FeignClient(
    name = "idam-api",
    url = "${idam.api.url}",
    configuration = {FeignConfiguration.class, DisableHystrixFeignConfiguration.class}
)
public interface IdamApi {

    @GetMapping(value = "/o/userinfo", produces = "application/json", consumes = "application/json")
    UserInfo userInfo(@RequestHeader(AUTHORIZATION) String userToken);

    @PostMapping(value = "/o/token", produces = "application/json", consumes = "application/x-www-form-urlencoded")
    Token token(@RequestBody Map<String, ?> form);

}
