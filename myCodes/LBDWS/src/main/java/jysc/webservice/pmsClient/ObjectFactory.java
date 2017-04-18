
package jysc.webservice.pmsClient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.shlanbao.tzsc.data.webservice.server.impl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _WCTSendOrderStatusChanges_QNAME = new QName("http://impl.server.webservice.data.tzsc.shlanbao.com/", "WCTSendOrderStatusChanges");
    private final static QName _WCTSendEqpRealTimeDataResponse_QNAME = new QName("http://impl.server.webservice.data.tzsc.shlanbao.com/", "WCTSendEqpRealTimeDataResponse");
    private final static QName _WCTSendEqpRealTimeData_QNAME = new QName("http://impl.server.webservice.data.tzsc.shlanbao.com/", "WCTSendEqpRealTimeData");
    private final static QName _WCTSendOrderStatusChangesResponse_QNAME = new QName("http://impl.server.webservice.data.tzsc.shlanbao.com/", "WCTSendOrderStatusChangesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.shlanbao.tzsc.data.webservice.server.impl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WCTSendOrderStatusChangesResponse }
     * 
     */
    public WCTSendOrderStatusChangesResponse createWCTSendOrderStatusChangesResponse() {
        return new WCTSendOrderStatusChangesResponse();
    }

    /**
     * Create an instance of {@link WCTSendEqpRealTimeData }
     * 
     */
    public WCTSendEqpRealTimeData createWCTSendEqpRealTimeData() {
        return new WCTSendEqpRealTimeData();
    }

    /**
     * Create an instance of {@link WCTSendEqpRealTimeDataResponse }
     * 
     */
    public WCTSendEqpRealTimeDataResponse createWCTSendEqpRealTimeDataResponse() {
        return new WCTSendEqpRealTimeDataResponse();
    }

    /**
     * Create an instance of {@link WCTSendOrderStatusChanges }
     * 
     */
    public WCTSendOrderStatusChanges createWCTSendOrderStatusChanges() {
        return new WCTSendOrderStatusChanges();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WCTSendOrderStatusChanges }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.server.webservice.data.tzsc.shlanbao.com/", name = "WCTSendOrderStatusChanges")
    public JAXBElement<WCTSendOrderStatusChanges> createWCTSendOrderStatusChanges(WCTSendOrderStatusChanges value) {
        return new JAXBElement<WCTSendOrderStatusChanges>(_WCTSendOrderStatusChanges_QNAME, WCTSendOrderStatusChanges.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WCTSendEqpRealTimeDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.server.webservice.data.tzsc.shlanbao.com/", name = "WCTSendEqpRealTimeDataResponse")
    public JAXBElement<WCTSendEqpRealTimeDataResponse> createWCTSendEqpRealTimeDataResponse(WCTSendEqpRealTimeDataResponse value) {
        return new JAXBElement<WCTSendEqpRealTimeDataResponse>(_WCTSendEqpRealTimeDataResponse_QNAME, WCTSendEqpRealTimeDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WCTSendEqpRealTimeData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.server.webservice.data.tzsc.shlanbao.com/", name = "WCTSendEqpRealTimeData")
    public JAXBElement<WCTSendEqpRealTimeData> createWCTSendEqpRealTimeData(WCTSendEqpRealTimeData value) {
        return new JAXBElement<WCTSendEqpRealTimeData>(_WCTSendEqpRealTimeData_QNAME, WCTSendEqpRealTimeData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WCTSendOrderStatusChangesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.server.webservice.data.tzsc.shlanbao.com/", name = "WCTSendOrderStatusChangesResponse")
    public JAXBElement<WCTSendOrderStatusChangesResponse> createWCTSendOrderStatusChangesResponse(WCTSendOrderStatusChangesResponse value) {
        return new JAXBElement<WCTSendOrderStatusChangesResponse>(_WCTSendOrderStatusChangesResponse_QNAME, WCTSendOrderStatusChangesResponse.class, null, value);
    }

}
