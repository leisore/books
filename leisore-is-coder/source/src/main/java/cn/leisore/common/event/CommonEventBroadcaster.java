package cn.leisore.common.event;

public interface CommonEventBroadcaster {

    void broadcastEvent(CommonEvent event);

    void addEventListener(CommonEventListener listener);

    void addEventListener(CommonEventListener listener, CommonEventFilter filter);

    void removeEventListener(CommonEventListener listener);
    
    CommonEventListener[] getEventListeners();
}
