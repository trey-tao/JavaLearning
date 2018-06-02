package com.trey.classloader;

/**
 * @Auther: trey_stao@163.com
 * @Date: 2018/5/31 13:58
 * @Description:
 */
public class ClassLoaderDemo {

    public void test() {
        System.out.println(this.getClass().getClassLoader().getResource("").toExternalForm());
    }

    public static void main(String[] args) {
        String name = "com.trey.es.ElasticsearchDemo";
        String path = name.replace('.','/').concat(".class");
        //System.out.println(path);
        ClassLoaderDemo demo = new ClassLoaderDemo();
        demo.test();
    }
}
