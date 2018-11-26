package com.trey.eventbus;

import lombok.Getter;

/**
 * @FileName: TestEvent.java
 * @Description: TestEvent.java类说明
 * @Author: tao.shi
 * @Date: 2018/11/26 17:54
 */
@Getter
public class TestEvent {
    private int message;
    public TestEvent(int message) {
        this.message = message;
    System.out.println(
            "event message:" + message
    );
    }

}
