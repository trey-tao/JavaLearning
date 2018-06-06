package com.trey.leetcode;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * Course Schedule II
 *      入参：课程数，和上课的要求，上课要求比如上0课，必须要先上1课，表达式为：[1,0]
 *      出参：int[] 上完所有课的顺序，返回一个即可。如果不存在，则返回空
 *
 * Example 1:
 *
 * Input: 2, [[1,0]]
 * Output: [0,1]
 * Explanation: There are a total of 2 courses to take. To take course 1 you should have finished
 *              course 0. So the correct course order is [0,1] .
 *
 * Example 2:
 *
 * Input: 4, [[1,0],[2,0],[3,1],[3,2]]
 * Output: [0,1,2,3] or [0,2,1,3]
 * Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both
 *              courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0.
 *              So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3] .
 * @Auther: trey_stao@163.com
 * @Date: 2018/6/4 17:23
 * @Description:
 */
public class CourseScheduleIIFor210 {

    /**
     * 思路：
     *      有向图问题，查找完整路径
     *      利用邻向矩阵实现
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {

        int[][] graph = new int[numCourses][numCourses];

        int[] indegree = new int[numCourses];

        //初始化树
        for(int i = 0 ; i < prerequisites.length; i++) {
            int[] temp = prerequisites[i];
            graph[temp[1]][temp[0]] = 1;
        }

        Queue<Integer> queue = new LinkedList<>();
        //统计每门课的入度，即是否有前置依赖
        for(int i = 0; i < numCourses; i++) {
            for(int j = 0; j < numCourses; j++) {
                if(graph[j][i] == 1) {
                    indegree[i]++;
                }
            }
            if(indegree[i] == 0) {
                queue.offer(i);
            }
        }
        List<Integer> list = new ArrayList<>();
        //排序
        while(!queue.isEmpty()) {
            int i = queue.poll();
            list.add(i);
            if(list.size() > numCourses) {
                return new int[0];
            }
            //需要将该节点相关的边都移除
            for(int j = 0; j < numCourses;j++) {
                if(graph[i][j] == 1) {
                    if(--indegree[j] == 0) {
                        queue.offer(j);
                    }
                }
            }
        }
        if(list.size() < numCourses) {
            return new int[0];
        }
        return list.stream().mapToInt(i->i).toArray();
    }

    /**
     * 网友高赞答案分析
     *      思路分析：
     *          1.先初始化构建二维数组，应该就是一个邻接表，与我的方法其实是一致的
     *              区别在于构建一个邻接链表的数组adjs，没有用邻接矩阵
     *          2.采用深度优秀遍历或者广度优先遍历进行排序
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public int[] findOrderForBest(int numCourses, int[][] prerequisites) {
        int[] incLinkCounts = new int[numCourses];
        List<List<Integer>> adjs = new ArrayList<>(numCourses);
        initialiseGraph(incLinkCounts, adjs, prerequisites);
        //return solveByBFS(incLinkCounts, adjs);
        return solveByDFS(adjs);
    }

    /**
     * 初始化图
     * @param incLinkCounts
     * @param adjs
     * @param prerequisites
     */
    private void initialiseGraph(int[] incLinkCounts, List<List<Integer>> adjs, int[][] prerequisites){
        int n = incLinkCounts.length;
        while (n-- > 0) adjs.add(new ArrayList<>());
        for (int[] edge : prerequisites) {
            incLinkCounts[edge[0]]++;
            adjs.get(edge[1]).add(edge[0]);
        }
    }

    /**
     * 广度优先遍历
     *      与我的解法类似：获取到每节课序号中入度为0的课
     *      对于每门入度为0的课，插入结果数组order，然后将与这门课相关的依赖都去除
     * @param incLinkCounts
     * @param adjs
     * @return
     */
    private int[] solveByBFS(int[] incLinkCounts, List<List<Integer>> adjs){
        int[] order = new int[incLinkCounts.length];
        Queue<Integer> toVisit = new ArrayDeque<>();
        for (int i = 0; i < incLinkCounts.length; i++) {
            if (incLinkCounts[i] == 0) toVisit.offer(i);
        }
        int visited = 0;
        while (!toVisit.isEmpty()) {
            int from = toVisit.poll();
            order[visited++] = from;
            for (int to : adjs.get(from)) {
                incLinkCounts[to]--;
                if (incLinkCounts[to] == 0) toVisit.offer(to);
            }
        }
        return visited == incLinkCounts.length ? order : new int[0];
    }

    /**
     * 深度优先遍历
     *      思路：
     *      深度优先遍历
     * @param adjs
     * @return
     */
    private int[] solveByDFS(List<List<Integer>> adjs) {
        BitSet visited = new BitSet(adjs.size());
        BitSet onStack = new BitSet(adjs.size());
        Deque<Integer> order = new ArrayDeque<>();
        for (int i = adjs.size() - 1; i >= 0; i--) {
            if (visited.get(i) == false && hasOrder(i, adjs, visited, onStack, order) == false) return new int[0];
        }
        int[] orderArray = new int[adjs.size()];
        for (int i = 0; !order.isEmpty(); i++) orderArray[i] = order.pop();
        return orderArray;
    }

    /**
     * 深度遍历，利用visited标记已走过的节点，onStack存储从from节点开始走的节点
     * @param from
     * @param adjs
     * @param visited
     * @param onStack
     * @param order
     * @return
     */
    private boolean hasOrder(int from, List<List<Integer>> adjs, BitSet visited, BitSet onStack, Deque<Integer> order) {
        visited.set(from);
        onStack.set(from);
        for (int to : adjs.get(from)) {
            if (visited.get(to) == false) {
                if (hasOrder(to, adjs, visited, onStack, order) == false) return false;
            } else if (onStack.get(to) == true) {
                return false;
            }
        }
        onStack.clear(from);
        order.push(from);
        return true;
    }

    public static void main(String[] args) {
        CourseScheduleIIFor210 c = new CourseScheduleIIFor210();
        //int numCourses = 4;
        int[][] prerequisites = {{1,0},{2,0},{3,1},{3,2}};
        int numCourses = 4;
        //int[][] prerequisites = {{1,0},{1,2}};
        System.out.println(JSON.toJSONString(c.findOrderForBest(numCourses,prerequisites)));
    }

}
