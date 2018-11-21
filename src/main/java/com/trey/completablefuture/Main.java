package com.trey.completablefuture;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @FileName: Main.java
 * @Description: Main.java类说明
 * @Author: tao.shi
 * @Date: 2018/11/21 16:04
 */
public class Main {

    private static Random rand = new Random();

    private static CompletableFuture<NameListResult> queryName(String name) {
    CompletableFuture<NameListResult> completableFuture =
        CompletableFuture.supplyAsync(
            () -> {
              NameListResult nameListResult = new NameListResult();
              if (name.equals("bobo")) {
                return null;
              }
              try {
                Thread.sleep(rand.nextInt(5000) + 2000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              nameListResult.setNameListInfoCode(name);
              Map<String, Object> map = new HashMap<>();
              map.put("name", name);
              map.put("age", rand.nextInt(100));
              nameListResult.setResponseCodeAndData(map);
              System.out.println("---完成测试任务，name " + name);
              return nameListResult;
            });
        return completableFuture;
    }

    /**
     * 主要测试的功能为 多线程并发调用服务后的一系列处理
     *      1、 完成多线程调用服务
     *      2、 服务拼接能力
     *      3、 空返回对象过滤能力
     * @param names
     * @return
     */
    private static List<NameListResult> queryThirdNameList(List<String> names) {
        List<CompletableFuture<NameListResult>> completableFutures =
                names.stream().map(dataService -> queryName(dataService)).collect(Collectors.toList());
        CompletableFuture<Void> allFutures =
                CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]));
        CompletableFuture<List<NameListResult>> allCompletableFuture = allFutures.thenApply(future -> completableFutures.stream()
                .map(completableFuture -> completableFuture.join())
                .collect(Collectors.toList()));
        CompletableFuture<List<NameListResult>> listCompletableFuture =
                allCompletableFuture.thenApply(nameListResults -> nameListResults.stream().filter(Objects::nonNull).collect(Collectors.toList()));

        try {
            List<NameListResult> nameListResults = listCompletableFuture.get();
            return nameListResults;
        } catch (InterruptedException e) {
            System.out.println("异步查询出错" + e);
            return Collections.EMPTY_LIST;
        } catch (ExecutionException e) {
            System.out.println("异步查询出错" + e);
            return Collections.EMPTY_LIST;
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    static
    class NameListResult implements Serializable {
        private static final long serialVersionUID = -3651524848798566216L;

        private Map<String, Object> responseCodeAndData;
        private String nameListInfoCode;
    }

  public static void main(String[] args) throws Exception {
    List<String> names= new ArrayList<>();
    names.add("taotao");
    names.add("doudou");
    names.add("bobo");
    names.add("okok");
    List<NameListResult> list = queryThirdNameList(names);
    list.forEach(i -> System.out.println(JSON.toJSONString(i)));
  }
}
