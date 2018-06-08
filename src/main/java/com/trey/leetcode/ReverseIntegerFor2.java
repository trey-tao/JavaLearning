package com.trey.leetcode;


/**
 * Reverse Integet
 *      Given a 32-bit signed integer, reverse digits of an integer.
 *      Note:
 *          1.整数的符号始终在前面，例如：-123 变为 -321
 *          2.边界条件：0；翻转后的整数可能溢出
 *          3.无效数字需要屏蔽:例如：120 变为21
 * @Auther: trey_stao@163.com
 * @Date: 2018/6/2 14:15
 * @Description:
 */
public class ReverseIntegerFor2 {

    public int reverse(int x) {
        // 边界条件的判断很重要
        if (x == 0) {
            return 0;
        }
        StringBuilder s = new StringBuilder(String.valueOf(x));
        /**
         * 思路1：利用正则对字符创s直接去除-号  和 000
         */

        /**
         * 思路2：翻转字符串，判断第一位，为0，则舍弃，不为0结束判断
         * 根据b的情况，在开头加上-
         */
        s = s.reverse();
        boolean b = x < 0 ? true : false;
        while ("0".equals(s.substring(0, 1))) {
            s.deleteCharAt(0);
        }
        if (b) {
            s.insert(0, "-");
            s.deleteCharAt(s.length() - 1);
        }
        /**
         * !对于翻转后的整数可能超过了int的范围，需要捕获返回0
         */
        try {
            return Integer.valueOf(s.toString());
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 网友推荐解法
     *      思路理解：
     *      循环获取最低位的数，依次*10+上一位的数，此过程，自动处理开头为0的无效数
     *
     * @param x
     * @return
     */
    public int reverseForBest(int x)
    {
        int result = 0;

        while (x != 0)
        {
            int tail = x % 10;
            int newResult = result * 10 + tail;
            if ((newResult - tail) / 10 != result)
            { return 0; }
            result = newResult;
            x = x / 10;
        }

        return result;
    }

}
