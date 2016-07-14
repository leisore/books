package cn.leisore.common.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCommonEventBroadcaster implements CommonEventBroadcaster {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCommonEventBroadcaster.class);

    private Set<ListenerPair> listeners = new CopyOnWriteArraySet<>();

    @Override
    public void broadcastEvent(CommonEvent event) {
        try {
            for (ListenerPair pair : listeners) {
                if (pair.isCareEvent(event)) {
                    pair.listener.handleEvent(event);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to broadcast event:" + event, e);
        }
    }

    @Override
    public void addEventListener(CommonEventListener listener) {
        listeners.add(new ListenerPair(listener, null));
    }

    @Override
    public void addEventListener(CommonEventListener listener, CommonEventFilter filter) {
        listeners.add(new ListenerPair(listener, filter));
    }

    @Override
    public void removeEventListener(CommonEventListener listener) {
        listeners.remove(new ListenerPair(listener, null));
    }

    @Override
    public CommonEventListener[] getEventListeners() {
        List<CommonEventListener> result = new ArrayList<>(listeners.size());
        for (ListenerPair pair : listeners) {
            result.add(pair.listener);
        }
        return result.toArray(new CommonEventListener[result.size()]);
    }

    private class ListenerPair implements CommonEventFilter {

        CommonEventListener listener;
        CommonEventFilter filter;

        ListenerPair(CommonEventListener listener, CommonEventFilter filter) {
            this.listener = listener;
            this.filter = filter;
        }

        @Override
        public boolean equals(Object obj) {
            return listener.equals(obj);
        }

        @Override
        public int hashCode() {
            return listener.hashCode();
        }

        @Override
        public boolean isCareEvent(CommonEvent event) {
            if (filter == null) {
                return true;
            }

            return filter.isCareEvent(event);
        }
    }
}
