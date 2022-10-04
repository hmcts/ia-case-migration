package uk.gov.hmcts.reform.iacasemigration.domain.entities.commondata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Flag {
    @JsonProperty("FlagDetails")
    private List<FlagDetail> flagDetails;
}
