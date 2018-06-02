package com.trey.leetcode;

/**
 * Example :
 * Input:
 *  S = "cba"
 *  T = "abcd"
 * Output: "cbad"
 * Explanation:
 *  "a", "b", "c" appear in S, so the order of "a", "b", "c" should be "c", "b", and "a".
 *  Since "d" does not appear in S, it can be at any position in T. "dcba", "cdba", "cbda" are also valid outputs.
 * @Auther: trey_stao@163.com
 * @Date: 2018/6/1 15:11
 * @Description:
 */
public class CustomSortStringFor791 {

    public String customSortString(String S, String T) {
        /**
         * 思路：
         * 		1、先需要确认T中哪些字符不存在于S中，不存在的字符直接提出来，塞入一个字符串
         * 		2、剩余的字符都是S中存在，但可能出现重复，将这些字符按个数塞入一个长度为26的数组里
         * 		3，遍历S，对于每个字符，判断数组对应位置的个数是否大于0，大于0，则添加到字符串尾端。
         */
        char[] tempArray = new char[26];
        StringBuilder result = new StringBuilder();
        for(char t : T.toCharArray()) {
            if(S.indexOf(String.valueOf(t)) == -1) {
                result.append(t);
            } else {
                tempArray[t - 'a']++;
            }
        }
        for(char s : S.toCharArray()) {
            int count = tempArray[s -'a'];
            while ( count-- > 0) {
                result.append(s);
            }
        }

        return result.toString();
    }

    /**
     * 网友实现的最多赞的方法
     * @param S
     * @param T
     * @return
     */
    public String BestForCustomSortString(String S, String T) {
        int[] count = new int[26];
        for (char c : T.toCharArray()) { ++count[c - 'a']; }  // count each char in T.
        StringBuilder sb = new StringBuilder();
        for (char c : S.toCharArray()) {
            while (count[c - 'a']-- > 0) { sb.append(c); }    // sort chars both in T and S by the order of S.
        }
        for (char c = 'a'; c <= 'z'; ++c) {
            while (count[c - 'a']-- > 0) { sb.append(c); }   // group chars in T but not in S.
        }
        return sb.toString();
    }

}
