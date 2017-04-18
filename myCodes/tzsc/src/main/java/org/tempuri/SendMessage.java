
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="strConnectorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strDisServerHost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strMessageType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strMessageSchema" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strNull" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "strConnectorName",
    "strDisServerHost",
    "strMessage",
    "strMessageType",
    "strMessageSchema",
    "strNull"
})
@XmlRootElement(name = "SendMessage")
public class SendMessage {

    protected String strConnectorName;
    protected String strDisServerHost;
    protected String strMessage;
    protected String strMessageType;
    protected String strMessageSchema;
    protected String strNull;

    /**
     * 获取strConnectorName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrConnectorName() {
        return strConnectorName;
    }

    /**
     * 设置strConnectorName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrConnectorName(String value) {
        this.strConnectorName = value;
    }

    /**
     * 获取strDisServerHost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrDisServerHost() {
        return strDisServerHost;
    }

    /**
     * 设置strDisServerHost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrDisServerHost(String value) {
        this.strDisServerHost = value;
    }

    /**
     * 获取strMessage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrMessage() {
        return strMessage;
    }

    /**
     * 设置strMessage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrMessage(String value) {
        this.strMessage = value;
    }

    /**
     * 获取strMessageType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrMessageType() {
        return strMessageType;
    }

    /**
     * 设置strMessageType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrMessageType(String value) {
        this.strMessageType = value;
    }

    /**
     * 获取strMessageSchema属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrMessageSchema() {
        return strMessageSchema;
    }

    /**
     * 设置strMessageSchema属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrMessageSchema(String value) {
        this.strMessageSchema = value;
    }

    /**
     * 获取strNull属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrNull() {
        return strNull;
    }

    /**
     * 设置strNull属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrNull(String value) {
        this.strNull = value;
    }

}
