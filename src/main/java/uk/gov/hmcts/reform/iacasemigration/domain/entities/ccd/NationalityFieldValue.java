package uk.gov.hmcts.reform.iacasemigration.domain.entities.ccd;

public class NationalityFieldValue {
    private String code;

    public NationalityFieldValue(String code) {
        this.code = code;
    }

    private NationalityFieldValue() {

    }

    public String getCode() {
        return code;
    }
}
