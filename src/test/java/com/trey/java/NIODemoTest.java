package com.trey.java;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * @Auther: trey_stao@163.com
 * @Date: 2018/5/28 14:30
 * @Description:
 */
public class NIODemoTest {


    @Test
    public void createFileAndPath() {
        NIODemo demo = new NIODemo();
        try {
            String path = demo.createFileAndPath("Test.txt");
            System.out.println(path);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}