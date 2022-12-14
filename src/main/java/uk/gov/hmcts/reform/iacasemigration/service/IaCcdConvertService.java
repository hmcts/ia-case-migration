package uk.gov.hmcts.reform.iacasemigration.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.iacasemigration.domain.entities.AsylumCase;
import uk.gov.hmcts.reform.iacasemigration.service.exceptions.CcdDataDeserializationException;

import java.util.Map;

@Service
public class IaCcdConvertService {
    private static final Logger LOG = LoggerFactory.getLogger(IaCcdConvertService.class);

    public AsylumCase getCaseData(Map<String, Object> dataMap) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            AsylumCase asylumCase = mapper.convertValue(dataMap, AsylumCase.class);
            return asylumCase;

        } catch (Exception ex) {
            CcdDataDeserializationException ccdDeserializationException =
                new CcdDataDeserializationException("Error occurred when mapping case data to AsylumCase", ex);
            LOG.error("Error occurred when mapping case data to AsylumCase", ccdDeserializationException);
            throw ccdDeserializationException;
        }
    }

}
