package uk.gov.hmcts.reform.iacasemigration.infrastructure.clients;

public class CommonDataServiceResponseException extends RuntimeException {

    public CommonDataServiceResponseException(
        String message,
        Throwable cause
    ) {
        super(message, cause);
    }
}
