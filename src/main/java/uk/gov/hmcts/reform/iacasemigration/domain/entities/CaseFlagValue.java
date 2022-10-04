package uk.gov.hmcts.reform.iacasemigration.domain.entities;

import java.time.LocalDateTime;
import java.util.List;

public class CaseFlagValue {
    private String name;
    private String status;
    private String flagCode;
    private LocalDateTime dateTimeCreated;
    private boolean hearingRelevant;
    private List<CaseFlagPath> caseFlagPath;
}
