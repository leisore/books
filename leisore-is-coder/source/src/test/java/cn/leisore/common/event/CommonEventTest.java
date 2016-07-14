package cn.leisore.common.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class CommonEventTest {

    @Test
    public void testDefaultCommonEventBroadcaster() {
        class PingPongEventListener implements CommonEventListener {
            private List<CommonEvent> events = new ArrayList<>();

            @Override
            public void handleEvent(CommonEvent event) {
                events.add(event);
            }
        }
        PingPongEventListener allEventListener = new PingPongEventListener();
        PingPongEventListener pingEventListener = new PingPongEventListener();
        PingPongEventListener pongEventListener = new PingPongEventListener();

        CommonEventBroadcaster broadcaster = new DefaultCommonEventBroadcaster();
        broadcaster.addEventListener(allEventListener);
        broadcaster.addEventListener(pingEventListener, new CommonEventFilter() {
            @Override
            public boolean isCareEvent(CommonEvent event) {
                return event.getType() == CommonEventType.PING;
            }
        });
        broadcaster.addEventListener(pongEventListener, new CommonEventFilter() {
            @Override
            public boolean isCareEvent(CommonEvent event) {
                return event.getType() == CommonEventType.PONG;
            }
        });

        List<CommonEventListener> eventListeners = Arrays.asList(broadcaster.getEventListeners());

        Assert.assertEquals(3, eventListeners.size());
        Assert.assertTrue(eventListeners.contains(allEventListener));
        Assert.assertTrue(eventListeners.contains(pingEventListener));
        Assert.assertTrue(eventListeners.contains(pongEventListener));

        CommonEvent event1 = new CommonEvent(this, CommonEventType.PING, System.currentTimeMillis(), "ping1");
        CommonEvent event2 = new CommonEvent(this, CommonEventType.PING, System.currentTimeMillis(), "ping2");
        CommonEvent event3 = new CommonEvent(this, CommonEventType.PONG, System.currentTimeMillis(), "pong1");

        broadcaster.broadcastEvent(event1);
        broadcaster.broadcastEvent(event2);
        broadcaster.broadcastEvent(event3);

        Assert.assertEquals(3, allEventListener.events.size());
        Assert.assertEquals(2, pingEventListener.events.size());
        Assert.assertEquals(1, pongEventListener.events.size());

        Assert.assertEquals("ping2", pingEventListener.events.get(1).getUserData());
        Assert.assertEquals("pong1", pongEventListener.events.get(0).getUserData());
    }
}
