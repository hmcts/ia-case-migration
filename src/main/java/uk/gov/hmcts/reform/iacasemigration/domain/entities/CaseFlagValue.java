package uk.gov.hmcts.reform.iacasemigration.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.ccd.field.YesOrNo;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class CaseFlagValue {
    private String name;
    private String status;
    private String flagCode;
    private LocalDateTime dateTimeCreated;
    private YesOrNo hearingRelevant;
    @JsonProperty("path")
    private List<CaseFlagPath> caseFlagPath;
}
