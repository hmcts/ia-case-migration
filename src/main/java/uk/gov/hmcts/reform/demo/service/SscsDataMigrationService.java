package uk.gov.hmcts.reform.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.migration.service.DataMigrationService;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Slf4j
@Service("dataMigrationService")
@RequiredArgsConstructor
public class SscsDataMigrationService implements DataMigrationService<CaseDetails> {

    private static final String JURISDICTION = "SSCS";

    @Override
    public Predicate<CaseDetails> accepts() {
        return caseDetails ->
            caseDetails != null
            && caseDetails.getData() != null
            && JURISDICTION.equalsIgnoreCase(caseDetails.getJurisdiction());
    }

    @Override
    public CaseDetails migrate(Map<String, Object> data, Long id) {
        return (CaseDetails) data;
    }
}
