package uk.gov.hmcts.reform.iacasemigration.migration;

import static uk.gov.hmcts.reform.iacasemigration.domain.entities.AsylumCaseFieldDefinition.*;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.AsylumCase;
import uk.gov.hmcts.reform.iacasemigration.exception.MigrationException;


@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "migration.migrateCaseNameInternal", havingValue = "true")
public class CaseNameInternalMigration implements DataMigrationStep {

    @Override
    public void apply(AsylumCase asylumCase, Long id) {
        log.info("Applying location data migration steps for case: [{}] - Started", id);
        addCaseNameInternal(asylumCase, id);
        log.info("Applying location data migration steps for case: [{}] - Completed", id);
    }

    private void addCaseNameInternal(AsylumCase asylumCase, Long id) {
        log.info("  Attempting to add caseNameHmctsInternal for case: [{}]", id);

        Optional<Object> appellantGivenNamesToBeConcatenated = asylumCase.read(APPELLANT_GIVEN_NAMES);
        log.info("  first name: [{}]", appellantGivenNamesToBeConcatenated);
        Optional<Object> appellantFamilyNameToBeConcatenated = asylumCase.read(APPELLANT_FAMILY_NAME);
        log.info("  last name: [{}]", appellantFamilyNameToBeConcatenated);

        if (appellantGivenNamesToBeConcatenated.isEmpty() || appellantFamilyNameToBeConcatenated.isEmpty()) {
            throw new MigrationException("");
        }

        String expectedCaseName = null;

        expectedCaseName = getCaseName(appellantGivenNamesToBeConcatenated.get().toString(), appellantFamilyNameToBeConcatenated.get().toString());

        log.info("Adding caseNameHmctsInternal for case: [{}]", id);
        asylumCase.write(CASE_NAME_HMCTS_INTERNAL, expectedCaseName);

        asylumCase.read(CASE_NAME_HMCTS_INTERNAL);
    }

    public static String getCaseName(String appealReferenceNumberToBeConcatenated, String appellantFamilyNameToBeConcatenated) {

        String appellantNameForDisplay = appealReferenceNumberToBeConcatenated + " " + appellantFamilyNameToBeConcatenated;

        return appellantNameForDisplay.replaceAll("\\s+", " ").trim();
    }
}
