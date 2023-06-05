package uk.gov.hmcts.reform.iacasemigration.util;

import uk.gov.hmcts.reform.iacasemigration.domain.entities.*;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.ccd.field.IdValue;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.ccd.field.YesOrNo;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.commondata.Flag;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.commondata.FlagDetail;
import uk.gov.hmcts.reform.iacasemigration.exception.MigrationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static uk.gov.hmcts.reform.iacasemigration.domain.entities.CaseFlagType.*;

public class CaseFlagMapper {

    public static final String CASE_FLAG = "Case";
    public static final String PARTY_FLAG = "Party";
    public static final String ACTIVE = "Active";

    private static final List<String> caseLevelFlagEnums = List.of(
        ANONYMITY.toString(),
        COMPLEX_CASE.toString());
    private static final List<String> appellantLevelFlagsEnums = List.of(
        UNACCOMPANIED_MINOR.toString(),
        UNACCEPTABLE_CUSTOMER_BEHAVIOUR.toString(),
        POTENTIALLY_VIOLENT_PERSON.toString(),
        FOREIGN_NATIONAL_OFFENDER.toString(),
        DETAINED_IMMIGRATION_APPEAL.toString());


    public static StrategicCaseFlag convertTacticalToStrategicFlags(Flag dtoFlags,
                                                                    List<IdValue<LegacyCaseFlag>> LegacyCaseFlagsIdValues,
                                                                    List<CaseFlagType> caseFlagTypes,
                                                                    String caseLevel) {

        List<FlagDetail> dtoLevelFlags = dtoFlags.getFlagDetails()
            .stream()
            .filter(fd -> fd.getName().equals(caseLevel))
            .flatMap(dt1 -> dt1.getChildFlags().stream())
            .collect(Collectors.toList());

        List<LegacyCaseFlag> legacyCaseFlags = LegacyCaseFlagsIdValues.stream()
            .map(IdValue::getValue)
            .collect(Collectors.toList());

        List<LegacyCaseFlag> legacyCaseFlagsToProcess = new ArrayList<>();

        List<CaseFlagDetail> details = new ArrayList<>();

        if (caseLevel.equals(CASE_FLAG)) {
            legacyCaseFlagsToProcess = legacyCaseFlags.stream()
                .filter(legacyCaseFlag -> caseLevelFlagEnums.contains(legacyCaseFlag.getLegacyCaseFlagType().toString()))
                .collect(Collectors.toList());
        } else {
            legacyCaseFlagsToProcess = legacyCaseFlags.stream()
                .filter(legacyCaseFlag -> appellantLevelFlagsEnums.contains(legacyCaseFlag.getLegacyCaseFlagType().toString()))
                .collect(Collectors.toList());
        }

        for (LegacyCaseFlag legacyCaseFlag : legacyCaseFlagsToProcess) {
            details.add(buildStrategicCaseFlagDetail(dtoLevelFlags, legacyCaseFlag));
        }

        return StrategicCaseFlag.builder().details(details).build();
    }

    private static CaseFlagDetail buildStrategicCaseFlagDetail(List<FlagDetail> dtoLevelFlags, LegacyCaseFlag legacyCaseFlag) {
        StrategicCaseFlagType strategicFlag = convertToStrategicFlagType(legacyCaseFlag.getLegacyCaseFlagType());

        if (strategicFlag.toString().equals(StrategicCaseFlagType.UNKNOWN.toString())){
            throw new MigrationException("Couldn't map Tactical flag type to Strategic flag type === " + legacyCaseFlag.getLegacyCaseFlagType());
        }

        FlagDetail dtoFlagDetail = dtoLevelFlags.stream().filter(f -> f.getFlagCode().equals(strategicFlag.getFlagCode())).findAny()
            .orElseThrow(() -> new MigrationException("Couldn't find Strategic flag type from REF DATA === " + strategicFlag));

        List<CaseFlagPath> listOfPath = dtoFlagDetail.getPath().stream().map(p -> new CaseFlagPath(null, p)).collect(Collectors.toList());

        CaseFlagValue newStrategicFlagValue = CaseFlagValue.builder()
            .name(dtoFlagDetail.getName())
            .status(ACTIVE)
            .flagCode(dtoFlagDetail.getFlagCode())
            .flagComment(legacyCaseFlag.getLegacyCaseFlagAdditionalInformation())
            .dateTimeCreated(LocalDateTime.now())
            .hearingRelevant(dtoFlagDetail.getHearingRelevant() ? YesOrNo.YES : YesOrNo.NO)
            .caseFlagPath(listOfPath)
            .build();

        return new CaseFlagDetail(null, newStrategicFlagValue);
    }

    private static StrategicCaseFlagType convertToStrategicFlagType(CaseFlagType tacticalCaseFlag) {
        switch(tacticalCaseFlag.toString()) {
            case "anonymity":
                return StrategicCaseFlagType.RRO_ANONYMISATION;
            case "complexCase":
                return StrategicCaseFlagType.COMPLEX_CASE;
            case "unaccompaniedMinor":
                return StrategicCaseFlagType.UNACCOMPANIED_MINOR;
            case "unacceptableCustomerBehaviour":
            case "potentiallyViolentPerson":
                return StrategicCaseFlagType.UNACCEPTABLE_CUSTOMER_BEHAVIOUR;
            case "foreignNationalOffender":
                return StrategicCaseFlagType.FOREIGN_NATIONAL_OFFENDER;
            case "detainedImmigrationAppeal":
                return StrategicCaseFlagType.DETAINED_INDIVIDUAL;
            default:
                return StrategicCaseFlagType.UNKNOWN;
        }
    }
}
