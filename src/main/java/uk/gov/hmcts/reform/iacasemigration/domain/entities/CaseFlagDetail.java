package uk.gov.hmcts.reform.iacasemigration.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class CaseFlagDetail {
    private String id;
    private CaseFlagValue caseFlagValue;
}
