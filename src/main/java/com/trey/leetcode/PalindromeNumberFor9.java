package com.trey.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 回文数判断
 *      有个要求：不将整数转为字符串处理
 * Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.
 *
 * Example 1:
 *
 * Input: 121
 * Output: true
 * Example 2:
 *
 * Input: -121
 * Output: false
 * Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
 * Example 3:
 *
 * Input: 10
 * Output: false
 * Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
 *      Coud you solve it without converting the integer to a string?
 * @Auther: trey_stao@163.com
 * @Date: 2018/6/7 08:52
 * @Description:
 */
public class PalindromeNumberFor9 {
    /**
     * 思路：
     *      先获取每一位的数字，然后存入一个临时数组；
     *      对这个数组里的地位和高位进行比较是否都相同
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        if (x < 0 || x == 10) {
            return false;
        }
        if(x < 10) {
            return true;
        }
        //获取低位到高位的所有数字
        List<Integer> list = new ArrayList<>();
        while (x > 0) {
            list.add(x%10);
            x = x/10;
        }
        //遍历每一位数字，前后比较
        for(int i = 0; i < list.size()/2 + 1; i++) {
            if(list.get(i) != list.get(list.size()-i-1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 网友高赞方法：
     *      思路分析：
     *          核心思路：对于x，从利用while循环，再构建一个整数rev，为x的反转整数
     *          如果两个整数相同，说明是回文数。
     *          上面的思路少了一步，就是对于x 为类似10，100的情况，
     *              用上述反转获取到的值为1，因为高位的0会过滤掉
     *          所以作者在刚开的边界条件判断时，如果出现x%10 == 0，则直接false返回。
     * @param x
     * @return
     */
    public boolean isPalindromeForBest(int x) {
        if (x<0 || (x!=0 && x%10==0)) return false;
        int rev = 0;
        while (x>rev){
            rev = rev*10 + x%10;
            x = x/10;
        }
        return (x==rev || x==rev/10);
    }

    public static void main(String[] args) {
        PalindromeNumberFor9 palindromeNumberFor9 = new PalindromeNumberFor9();
        palindromeNumberFor9.isPalindromeForBest(100);
    }
}
