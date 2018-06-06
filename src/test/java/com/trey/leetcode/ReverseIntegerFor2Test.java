package com.trey.leetcode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Auther: trey_stao@163.com
 * @Date: 2018/6/4 15:56
 * @Description:
 */
public class ReverseIntegerFor2Test {

    private ReverseIntegerFor2 test;

    @Before
    public void setUp() throws Exception {
        test = new ReverseIntegerFor2();
    }

    @Test
    public void reverse() {
        Assert.assertEquals(-102,test.reverse(-201));
        Assert.assertEquals(21,test.reverse(120));
        Assert.assertEquals(123,test.reverse(321));
    }

    @Test
    public void reverseForBest() {
        Assert.assertEquals(-102,test.reverseForBest(-201));
    }
}