package com.trey.test;

import java.util.List;

/**
 * 中位数
 * @Auther: trey_stao@163.com
 * @Date: 2018/5/25 09:17
 * @Description:
 */
public class MedianDemo {

    /**
     * 给定一组数据，每个数据都有一定权值，请写算法计算中位数
     * @param tagets 给定的数据
     */
    public double generateMedianValue(List<Target> tagets) {
        /**
         * FIXME 未完成
         * 1 遍历targets，数值*权值，生成每个Target的realValue
         * 2 对于List<Target>按照realValue进行排序
         * 3 获取List<Target>的长度，奇数取中间的value值；偶数取中间两个value的平均值
         * 4 返回value值
         */
        return 0;
    }


    public static class Target {
        /**
         * 数值
         */
        private double value;
        /**
         * 权值
         */
        private double weight;

        /**
         * 带权值的值
         */
        private double realValue;

        public Target(double value, double weight) {
            this.value = value;
            this.weight = weight;
        }
        /**
         * 省略get和set方法
         */

    }
}
