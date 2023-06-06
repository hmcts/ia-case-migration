package uk.gov.hmcts.reform.iacasemigration.domain.entities.commondata;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CaseFlagDto {
    private List<Flag> flags;
}
