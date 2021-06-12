package org.shikalenko.xmlanbind.impl.pojo;

import org.shikalenko.xmlanbind.annotations.Attribute;
import org.shikalenko.xmlanbind.annotations.Element;

public class OptionObject {
    private String value;
    private String msgId;
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getMsgId() {
        return msgId;
    }
    @Attribute("msg-id")
    @Element("msg-id")
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    
}
