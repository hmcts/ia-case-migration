package uk.gov.hmcts.reform.iacasemigration.migration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.AsylumCase;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.CaseFlag;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.CaseFlagType;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.StrategicCaseFlag;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.ccd.field.IdValue;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.commondata.CaseFlagDto;
import uk.gov.hmcts.reform.iacasemigration.infrastructure.clients.RdCommonData;
import uk.gov.hmcts.reform.iacasemigration.util.CaseFlagMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static uk.gov.hmcts.reform.iacasemigration.domain.entities.AsylumCaseFieldDefinition.*;
import static uk.gov.hmcts.reform.iacasemigration.util.CaseFlagMapper.CASE_FLAG;
import static uk.gov.hmcts.reform.iacasemigration.util.CaseFlagMapper.PARTY_FLAG;


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
        log.info("Attempting to add migrate tactical case flag for case: [{}]", id);

        Optional<List<IdValue<CaseFlag>>> maybeExistingCaseFlags = asylumCase.read(CASE_FLAGS);
        log.info("Existing list of tactical case flags: [{}]", maybeExistingCaseFlags);

        if (maybeExistingCaseFlags.isPresent() && ! maybeExistingCaseFlags.get().isEmpty()) {

            log.info("Starting to migrate tactical case flags...");
            CaseFlagDto caseFlagDto = rdCommonData.retrieveStrategicCaseFlags();

            List<CaseFlagType> tacticalCaseFlagTypes = getExistingCaseFlagListElements(maybeExistingCaseFlags.get());

            log.info("processing caseLevelFlags...");
            StrategicCaseFlag caseLevelFlags =
                CaseFlagMapper.convertTacticalToStrategicFlags(caseFlagDto.getFlags().get(0), tacticalCaseFlagTypes, CASE_FLAG);

            log.info("Writing caseLevelFlags: [{}]", caseLevelFlags.getDetails());
            asylumCase.write(CASE_LEVEL_FLAGS, caseLevelFlags);

            log.info("processing appellantLevelFlags...");
            StrategicCaseFlag appellantLevelFlags =
                CaseFlagMapper.convertTacticalToStrategicFlags(caseFlagDto.getFlags().get(0), tacticalCaseFlagTypes, PARTY_FLAG);

            log.info("Writing appellantLevelFlags: [{}]", appellantLevelFlags.getDetails());
            asylumCase.write(APPELLANT_LEVEL_FLAGS, appellantLevelFlags);

            asylumCase.size();
        }
    }

    private List<CaseFlagType> getExistingCaseFlagListElements
        (List<IdValue<CaseFlag>> existingCaseFlags) {
        requireNonNull(existingCaseFlags, "existingCaseFlags must not be null");
        return existingCaseFlags
            .stream()
            .map(idValue -> idValue.getValue().getCaseFlagType())
            .collect(Collectors.toList());
    }
}
