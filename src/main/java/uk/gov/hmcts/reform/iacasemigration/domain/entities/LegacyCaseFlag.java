package uk.gov.hmcts.reform.iacasemigration.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegacyCaseFlag {

    @NonNull
    CaseFlagType caseFlagType;
    @NonNull
    String caseFlagAdditionalInformation;

}
