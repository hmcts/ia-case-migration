package uk.gov.hmcts.reform.iacasemigration.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CaseFlag {

    @NonNull
    CaseFlagType caseFlagType;
    @NonNull
    String caseFlagAdditionalInformation;

}
