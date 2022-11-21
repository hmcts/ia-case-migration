package uk.gov.hmcts.reform.iacasemigration.migration;

import static uk.gov.hmcts.reform.iacasemigration.domain.entities.AsylumCaseFieldDefinition.*;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.AsylumCase;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.CaseFlag;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.ccd.field.IdValue;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "migration.migrateLegacyCaseFlag", havingValue = "true")
@Component
public class LegacyCaseFlagMigration implements DataMigrationStep {

    @Override
    public void apply(AsylumCase asylumCase, Long id) {
        log.info("Applying case flag data migration steps for case: [{}] - Started", id);
        migrateCaseFlagInternal(asylumCase, id);
        log.info("Applying case flag data migration steps for case: [{}] - Completed", id);
    }

    private void migrateCaseFlagInternal(AsylumCase asylumCase, Long id) {
        log.info("Attempting to add migrate existing case flags for case: [{}]", id);

        Optional<List<IdValue<CaseFlag>>> maybeExistingCaseFlags = asylumCase.read(CASE_FLAGS);
        log.info("Existing list of tactical case flags: [{}]", maybeExistingCaseFlags);

        if (maybeExistingCaseFlags.isPresent() && !maybeExistingCaseFlags.get().isEmpty()) {

            log.info("Migrating tactic case flags to legacyCaseField");
            asylumCase.write(LEGACY_CASE_FLAGS, maybeExistingCaseFlags);

            log.info("Removing data from caseFlags field");
            asylumCase.clear(CASE_FLAGS);

            log.info("Successfully migrated case flag for case: [{}]", id);
        }
        else {
            log.info("No tactical case flags exists for case: [{}]", id);
        }

        log.info("Finished processing case: [{}]", id);
    }
}
