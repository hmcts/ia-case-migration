package uk.gov.hmcts.reform.iacasemigration.domain.entities;

import lombok.*;
import lombok.Value;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseFlag {

    @NonNull
    CaseFlagType caseFlagType;
    @NonNull
    String caseFlagAdditionalInformation;

}
