package uk.gov.hmcts.reform.iacasemigration.infrastructure.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.commondata.CaseFlagDto;

import java.util.Map;

import static java.util.Objects.requireNonNull;

@Service
@Slf4j
public class RdCommonData {

    private static final String SERVICE_AUTHORIZATION = "ServiceAuthorization";

    private final RestTemplate restTemplate;
    private final String cdaUrl;
    private final String cdCaseFlagsPath;
    private final String hmctsServiceId;

    public RdCommonData(RestTemplate restTemplate,
                        @Value("${rd_common_data_api_url}") String cdaUrl,
                        @Value("${rd_common_data_case_flags_path}") String cdCaseFlagsPath,
                        @Value("${migration.hmctsid}") String hmctsServiceId
    ) {
        this.restTemplate = restTemplate;
        this.cdaUrl = cdaUrl;
        this.cdCaseFlagsPath = cdCaseFlagsPath;
        this.hmctsServiceId = hmctsServiceId;
    }

    public CaseFlagDto retrieveStrategicCaseFlags() {
//        final String serviceAuthorizationToken = serviceAuthTokenGenerator.generate();
//        final String accessToken = userDetails.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
//        headers.set(HttpHeaders.AUTHORIZATION, accessToken);
//        headers.set(SERVICE_AUTHORIZATION, serviceAuthorizationToken);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<CaseFlagDto> response;
        try {
            response = restTemplate
                .exchange(
                    cdaUrl + cdCaseFlagsPath + hmctsServiceId,
                    HttpMethod.GET,
                    requestEntity,
                    CaseFlagDto.class
                );

        } catch (RestClientResponseException e) {
            throw new CommonDataServiceResponseException(
                "Error calling RD-Common-Data-Api: " + cdaUrl + cdCaseFlagsPath +hmctsServiceId,
                e
            );
        }

        log.info("Http status received from D-Common-Data-Api; {}", response.getStatusCodeValue());

        return response.getBody();
    }
}

