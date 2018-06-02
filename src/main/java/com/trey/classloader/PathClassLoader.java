package com.trey.classloader;

import java.io.*;

/**
 * 自定义路径下的class文件加载
 * @Auther: trey_stao@163.com
 * @Date: 2018/5/31 19:34
 * @Description:
 */
public class PathClassLoader extends ClassLoader {
    private String classPath;

    public PathClassLoader(String classPath) {
        this.classPath = classPath;
    }

    private String packageName = "com.trey.classloader.Test";

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if(packageName.startsWith(name)) {
            byte[] classData = getData(name);
            if(classData == null) {
                throw new ClassNotFoundException();
            } else {
                System.out.println("当前类加载器:"+this.getClass().getClassLoader().toString());
                return defineClass(name, classData, 0, classData.length);
            }
        } else {
            return super.loadClass(name);
        }
    }

    private byte[] getData(String name) {
        String path = classPath + File.separatorChar + name.replace('.',File.separatorChar) + ".class";
        try {
            InputStream is = new FileInputStream(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int num = 0;
            while ((num = is.read(buffer)) != -1) {
                stream.write(buffer, 0, num);
            }
            return stream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
