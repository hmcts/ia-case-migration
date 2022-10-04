package uk.gov.hmcts.reform.iacasemigration.domain.entities.commondata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class FlagDetail {
    @JsonIgnore
    private Integer id;
    private String name;
    private Boolean hearingRelevant;
    private Boolean flagComment;
    private String flagCode;
    @JsonIgnore
    private Integer cateGoryId;
    @JsonProperty("isParent")
    private Boolean parent;
    @JsonProperty("Path")
    private List<String> path;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder.Default
    private List<FlagDetail> childFlags = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer listOfValuesLength;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ListOfValue> listOfValues;
}
