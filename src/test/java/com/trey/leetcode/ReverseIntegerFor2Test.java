package com.trey.leetcode;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Auther: trey_stao@163.com
 * @Date: 2018/6/4 15:56
 * @Description:
 */
public class ReverseIntegerFor2Test {

    @Test
    public void reverse() {
        ReverseIntegerFor2 test = new ReverseIntegerFor2();
        Assert.assertEquals(-102,test.reverse(-201));
        Assert.assertEquals(21,test.reverse(120));
        Assert.assertEquals(123,test.reverse(321));
    }
}