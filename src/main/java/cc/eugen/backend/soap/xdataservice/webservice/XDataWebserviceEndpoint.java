package cc.eugen.backend.soap.xdataservice.webservice;

import cc.eugen.backend.soap.xdataservice.generated.*;
import cc.eugen.backend.soap.xdataservice.service.XDataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Endpoint
@AllArgsConstructor
@Slf4j
public class XDataWebserviceEndpoint {

    private XDataService service;

    private static final String NAMESPACE_URI = "http://eugen.cc/spring-boot-soap-xdata";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getXDataRequest")
    @ResponsePayload
    public GetXDataResponse getXData(@RequestPayload GetXDataRequest request) {
        final var response = new GetXDataResponse();
        response.getXData().addAll(service.getAll());
        response.setResponseTime(getCurrentTimestamp());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getXDataByIdRequest")
    @ResponsePayload
    public GetXDataByIdResponse getXDataById(@RequestPayload GetXDataByIdRequest request) {
        final var response = new GetXDataByIdResponse();
        response.setXData(service.getById(request.getId()));
        response.setResponseTime(getCurrentTimestamp());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getXDataByMetadataRequest")
    @ResponsePayload
    public GetXDataByMetadataResponse getByXMetadata(@RequestPayload GetXDataByMetadataRequest request) {
        log.info("getByMetadata");
        final var response = new GetXDataByMetadataResponse();
        final var requestData = request.getXMetadata();
        response.getXData().addAll(service.getByXMetadata(requestData.getKey(), requestData.getValue()));
        response.setResponseTime(getCurrentTimestamp());
        return response;
    }

    private static XMLGregorianCalendar getCurrentTimestamp() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date());
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            return null;
        }

    }

}
