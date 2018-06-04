package com.trey.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 面试算法题
 * @Auther: trey_stao@163.com
 * @Date: 2018/6/4 11:01
 * @Description:
 */
public class InterviewTestForHW {

    /**
     * 功能要求如下：
     *      输入：
     *          orderString：DESC、AESC（枚举字符串，降序或者升序）
     *          string：按逗号拼接的整数字符串
     *      输出：
     *          每个逗号拼接的整数，每一位进行升序排列，并且屏蔽掉无效数字（比如0123 应该为 123）
     * 举例：
     *      输入：DESC 231,4210,329
     *      输出：123,124,239
     * @param orderString
     * @param string
     * @return
     */
    public String test(String orderString, String string) {
        /**
         * 思路：
         *      TODO 要用java的 List进行实现
         *      1.先实现一个排序算法sort()，输入为int[],输出为int[]
         *      2.对string按逗号分隔，对每个整数提取每一位的数字，组成int[]，然后调用sort()方法
         *      3.根据orderString，对按逗号分隔的整数进行排序
         *      4.输出
         */
        List<String> strings = Arrays.asList(string.split(","));
        List<Integer> results = new ArrayList<>();
        for( String s : strings) {
            List<Integer> list = new ArrayList<>(s.toCharArray().length);
            for(int i = 0; i< s.length(); i++){
                list.add(Integer.valueOf(String.valueOf(s.charAt(i))));
            }
            //升序排列
            Collections.sort(list);
            while(list.get(0) == 0) {
                list.remove(0);
            }
            StringBuilder temp = new StringBuilder();
            for(int i : list) {
                temp.append(i);
            }
            results.add(Integer.valueOf(temp.toString()));
        }
        //升序排列
        Collections.sort(results);

        if("DESC".equals(orderString)) {
            Collections.reverse(results);
        }
        StringBuilder str = new StringBuilder();
        for(int i : results) {
            str.append(i).append(",");
        }
        str.deleteCharAt(str.length()-1);

        return str.toString();
    }

    /**************快排实现，暂时没用上********************/

    private int Partition(int arr[],int low,int high)
    {
        int point = arr[(low+high)/2];
        while(low <high)
        {
            while(low<high && arr[high] >=point)
                high--;
            swap(arr,low,high);
            while(low<high && arr[low] <= point)
                low++;
            swap(arr,low,high);
        }
        return low;
    }

    private void quickSort(int arr[],int left, int right)
    {
        int index;
        if(left < right)
        {
            index = Partition(arr,left,right);
            quickSort(arr,left,index-1);
            quickSort(arr,index+1,right);

        }
    }

    private void swap(int k[],int low, int high) {
        int temp = k[low];
        k[low] = k[high];
        k[high] = temp;
    }

}
