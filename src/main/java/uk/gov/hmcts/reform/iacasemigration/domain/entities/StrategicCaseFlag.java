package uk.gov.hmcts.reform.iacasemigration.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class StrategicCaseFlag {
    String partyName;
    String roleOnCase;

    @JsonProperty("details")
    List<CaseFlagDetail> details;
}