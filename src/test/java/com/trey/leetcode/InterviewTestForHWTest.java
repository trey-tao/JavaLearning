package com.trey.leetcode;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Auther: trey_stao@163.com
 * @Date: 2018/6/4 14:29
 * @Description:
 */
public class InterviewTestForHWTest {

    @Test
    public void test1() {
       InterviewTestForHW hw = new InterviewTestForHW();
       String string =  hw.test("DESC", "132,0843,642");
       Assert.assertEquals("348,246,123",string);
    }
}