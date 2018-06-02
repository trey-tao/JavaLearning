package com.trey.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Java NIO 相关测试程序
 * @Auther: trey_stao@163.com
 * @Date: 2018/5/28 14:01
 * @Description:
 */
public class NIODemo {

    private final static String PATH = "config/nio/";

    /**
     * 创建目录及文件，如果目录存在，及不创建目录；如果文件存在，则删除文件重新创建
     * @param fileName
     * @return
     */
    public String createFileAndPath(String fileName) throws IOException {
        //先判断目录是否存在，不存在则创建目录
        String directory = PATH + System.currentTimeMillis();
        Path path = Paths.get(directory);
        if(!Files.exists(path)) {
            //目录不存在，则创建此目录
            Files.createDirectory(path);
        }
        Path filePath = Paths.get(directory,fileName);
        if(Files.exists(filePath)) {
            //文件存在，则删除此文件
            Files.delete(filePath);
        }
        Files.createFile(filePath);

        return filePath.toString();
    }

}
