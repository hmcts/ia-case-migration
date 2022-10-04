package uk.gov.hmcts.reform.iacasemigration.migration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.AsylumCase;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.ccd.field.IdValue;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.commondata.CaseFlag;
import uk.gov.hmcts.reform.iacasemigration.exception.MigrationException;
import uk.gov.hmcts.reform.iacasemigration.infrastructure.clients.RdCommonData;

import java.util.List;
import java.util.Optional;

import static uk.gov.hmcts.reform.iacasemigration.domain.entities.AsylumCaseFieldDefinition.*;


@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "migration.migrateCaseFlagsInternal", havingValue = "true")
public class CaseFlagInternalMigration implements DataMigrationStep {

    private final RdCommonData rdCommonData;

    @Override
    public void apply(AsylumCase asylumCase, Long id) {
        log.info("Applying case flag data migration steps for case: [{}] - Started", id);
        addCaseFlagInternal(asylumCase, id);
        log.info("Applying case flag data migration steps for case: [{}] - Completed", id);
    }

    private void addCaseFlagInternal(AsylumCase asylumCase, Long id) {
        log.info("  Attempting to add migrate tactical case flag for case: [{}]", id);

        Optional<List<IdValue<CaseFlag>>> maybeExistingCaseFlags = asylumCase.read(CASE_FLAGS);
        log.info("  case flags: [{}]", maybeExistingCaseFlags);
        CaseFlag caseFlag = rdCommonData.retrieveStrategicCaseFlags();
        caseFlag.getFlags();

//        Optional<Object> appellantGivenNamesToBeConcatenated = asylumCase.read(APPELLANT_GIVEN_NAMES);
//        log.info("  first name: [{}]", appellantGivenNamesToBeConcatenated);
//        Optional<Object> appellantFamilyNameToBeConcatenated = asylumCase.read(APPELLANT_FAMILY_NAME);
//        log.info("  last name: [{}]", appellantFamilyNameToBeConcatenated);
//
//        if (appellantGivenNamesToBeConcatenated.isEmpty() || appellantFamilyNameToBeConcatenated.isEmpty()) {
//            throw new MigrationException("");
//        }
//
//        String expectedCaseName = null;
//
//        expectedCaseName = getCaseName(appellantGivenNamesToBeConcatenated.get().toString(), appellantFamilyNameToBeConcatenated.get().toString());
//
//        log.info("Adding caseNameHmctsInternal for case: [{}]", id);
//        asylumCase.write(CASE_NAME_HMCTS_INTERNAL, expectedCaseName);
//
//        asylumCase.read(CASE_NAME_HMCTS_INTERNAL);
    }
}
