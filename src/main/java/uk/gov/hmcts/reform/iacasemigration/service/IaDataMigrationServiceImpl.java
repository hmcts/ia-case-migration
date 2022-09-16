package uk.gov.hmcts.reform.iacasemigration.service;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.iacasemigration.migration.DataMigrationStep;
import uk.gov.hmcts.reform.migration.service.DataMigrationService;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.AsylumCase;

@Slf4j
@Service("dataMigrationService")
@RequiredArgsConstructor
public class IaDataMigrationServiceImpl implements DataMigrationService<AsylumCase> {

    private static final String JURISDICTION = "IA";
    private final IaCcdConvertService iaCcdConvertService;
    private final List<DataMigrationStep> dataMigrationSteps;

    @Override
    public Predicate<CaseDetails> accepts() {
        return caseDetails ->
            caseDetails != null
            && caseDetails.getData() != null
            && JURISDICTION.equalsIgnoreCase(caseDetails.getJurisdiction());
    }

    @Override
    public AsylumCase migrate(Map<String, Object> data) {
        AsylumCase asylumCase = iaCcdConvertService.getCaseData(data);

        if (dataMigrationSteps != null && !dataMigrationSteps.isEmpty()) {
            dataMigrationSteps
                .forEach(s -> s.apply(asylumCase));
        }

        return asylumCase;
    }
}
