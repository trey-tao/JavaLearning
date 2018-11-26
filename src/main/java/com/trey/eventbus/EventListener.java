package com.trey.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.Getter;

/**
 * @FileName: EventListener.java
 * @Description: EventListener.java类说明
 * @Author: tao.shi
 * @Date: 2018/11/26 17:56
 */
@Getter
public class EventListener {
    public int lastMessage = 0;

    @Subscribe
    public void listen(TestEvent event) {
        lastMessage = event.getMessage();
    System.out.println("Message:" + lastMessage);
    }

      public static void main(String[] args) {

          EventBus eventBus = new EventBus("test");

          EventListener listener = new EventListener();

          eventBus.register(listener);

          eventBus.post(new TestEvent(100));
          eventBus.post(new TestEvent(200));

          System.out.println("Last Message:" + listener.getLastMessage());
      }
}
