package uk.gov.hmcts.reform.iacasemigration.domain.entities;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StrategicCaseFlagType {

    COMPLEX_CASE("complexCase", "Complex Case"),
    DETAINED_INDIVIDUAL("detainedIndividual", "Detained individual"),
    FOREIGN_NATIONAL_OFFENDER("foreignNationalOffender", "Foreign national offender"),
    RRO_ANONYMISATION("rroAnonymisation", "RRO (Restricted Reporting Order / Anonymisation)"),
    UNACCEPTABLE_CUSTOMER_BEHAVIOUR("unacceptableCustomerBehaviour", "Unacceptable/disruptive customer behaviour"),
    UNACCOMPANIED_MINOR("unaccompaniedMinor", "Unaccompanied minor"),


    @JsonEnumDefaultValue
    UNKNOWN("unknown", "Unknown");

    @JsonValue
    private final String id;
    private final String readableText;

    StrategicCaseFlagType(String id, String readableText) {
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
