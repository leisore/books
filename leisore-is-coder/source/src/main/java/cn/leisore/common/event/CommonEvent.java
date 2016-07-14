package cn.leisore.common.event;

import java.util.EventObject;

public class CommonEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    private CommonEventType type;
    private long timestamp;
    private Object userData;
    private String message;

    public CommonEvent(Object source) {
        super(source);
    }

    public CommonEvent(Object source, CommonEventType type, long timestamp, Object userData) {
        this(source, type, timestamp, userData, null);
    }

    public CommonEvent(Object source, CommonEventType type, long timestamp, Object userData, String message) {
        super(source);
        this.type = type;
        this.timestamp = timestamp;
        this.userData = userData;
        this.message = message;
    }

    public CommonEventType getType() {
        return type;
    }

    public void setType(CommonEventType type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Object getUserData() {
        return userData;
    }

    public void setUserData(Object userData) {
        this.userData = userData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CommonEvent [type=").append(type)
            .append(", source=").append(source)    
            .append(", timestamp=").append(timestamp)
            .append(", userData=").append(userData)
            .append(", message=").append(message)
            .append("]");
        return builder.toString();
    }
}
