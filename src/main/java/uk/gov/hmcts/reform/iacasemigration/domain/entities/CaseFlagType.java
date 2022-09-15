package uk.gov.hmcts.reform.iacasemigration.domain.entities;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CaseFlagType {

    ANONYMITY("anonymity", "Anonymity"),
    COMPLEX_CASE("complexCase", "Complex case"),
    DEPORT("deport", "Deport"),
    DETAINED_IMMIGRATION_APPEAL("detainedImmigrationAppeal", "Detained immigration appeal"),
    FOREIGN_NATIONAL_OFFENDER("foreignNationalOffender", "Foreign national offender"),
    POTENTIALLY_VIOLENT_PERSON("potentiallyViolentPerson", "Potentially violent person"),
    UNACCEPTABLE_CUSTOMER_BEHAVIOUR("unacceptableCustomerBehaviour", "Unacceptable customer behaviour"),
    UNACCOMPANIED_MINOR("unaccompaniedMinor", "Unaccompanied minor"),
    SET_ASIDE_REHEARD("setAsideReheard", "Set aside - Reheard"),
    S94B_OUT_OF_COUNTRY("s94bOutOfCountry", "S94B Out of Country"),
    APPEAL_ON_HOLD("appealOnHold", "Appeal on hold"),

    @JsonEnumDefaultValue
    UNKNOWN("unknown", "Unknown");

    @JsonValue
    private final String id;
    private final String readableText;

    CaseFlagType(String id, String readableText) {
        this.id = id;
        this.readableText = readableText;
    }

    public String getReadableText() {
        return readableText;
    }

    @Override
    public String toString() {
        return id;
    }
}
