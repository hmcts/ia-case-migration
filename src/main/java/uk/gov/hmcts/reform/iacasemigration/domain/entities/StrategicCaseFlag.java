package uk.gov.hmcts.reform.iacasemigration.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class StrategicCaseFlag {
    @JsonProperty("details")
    List<CaseFlagDetail> details;
}
