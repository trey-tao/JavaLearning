package com.trey.classloader;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;


/**
 * @Auther: trey_stao@163.com
 * @Date: 2018/6/1 17:57
 * @Description:
 */
public class PathClassLoaderTest {

    @Test
    public void name() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        PathClassLoader classLoader = new PathClassLoader("E:\\projects\\JavaLearningDemo\\target\\classes");
        Class<?> clazz = classLoader.loadClass("com.trey.classloader.Test");
        //com.trey.classloader.Test test = (com.trey.classloader.Test) clazz.newInstance();
        com.trey.classloader.Test test  = (com.trey.classloader.Test) clazz.getDeclaredConstructor(int.class).newInstance(10);

        Assert.assertEquals(10,(int)test.getNumber());
    }
}