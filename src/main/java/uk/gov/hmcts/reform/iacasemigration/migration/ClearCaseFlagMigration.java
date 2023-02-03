package uk.gov.hmcts.reform.iacasemigration.migration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.AsylumCase;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.CaseFlag;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.LegacyCaseFlag;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.StrategicCaseFlag;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.ccd.field.IdValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static uk.gov.hmcts.reform.iacasemigration.domain.entities.AsylumCaseFieldDefinition.CASE_FLAGS;
import static uk.gov.hmcts.reform.iacasemigration.domain.entities.AsylumCaseFieldDefinition.LEGACY_CASE_FLAGS;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "migration.clearCaseFlag", havingValue = "true")
@Component
public class ClearCaseFlagMigration implements DataMigrationStep {

    @Override
    public void apply(AsylumCase asylumCase, Long id) {
        log.info("Applying clear case flag data migration steps for case: [{}] - Started", id);
        migrateCaseFlagInternal(asylumCase, id);
        log.info("Applying clear case flag data migration steps for case: [{}] - Completed", id);
    }

    private void migrateCaseFlagInternal(AsylumCase asylumCase, Long id) {

        Optional<List<IdValue<CaseFlag>>> maybeExistingCaseFlags = asylumCase.read(CASE_FLAGS);

        if (maybeExistingCaseFlags.isPresent() && !maybeExistingCaseFlags.get().isEmpty()) {
            log.info("Case Flags Not Empty [{}] ", maybeExistingCaseFlags);
        }
        else {
            log.info("No tactical case flags exists for case [{}] proceeding to clear.", id);
            asylumCase.write(CASE_FLAGS, StrategicCaseFlag.builder().details(new ArrayList<>()).build());
        }

        log.info("Finished processing case: [{}]", id);
    }
}
