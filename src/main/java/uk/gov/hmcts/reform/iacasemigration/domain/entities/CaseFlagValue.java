package uk.gov.hmcts.reform.iacasemigration.domain.entities;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class CaseFlagValue {
    private StrategicCaseFlagType name;
    private String status;
    private String flagCode;
    private LocalDateTime dateTimeCreated;
    private boolean hearingRelevant;
    private List<CaseFlagPath> caseFlagPath;
}
