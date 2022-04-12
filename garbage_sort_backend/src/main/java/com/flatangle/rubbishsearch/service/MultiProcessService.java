package com.flatangle.rubbishsearch.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MultiProcessService {
    public  BufferedReader bufferedReader = null;
    public BufferedReader errorReader = null;
    public Process proc = null;
    public MultiProcessService()
    {
        try {
            // 执行命令返回执行的子进程对象
//            proc = new ProcessBuilder("C:\\workspace\\python\\ConvNeXt\\dist\\process\\process.exe",
//                    "-m=C:\\workspace\\python\\ConvNeXt\\weights\\best_model.pth").start();
            proc = new ProcessBuilder("D:\\TrashC\\yolov5\\process\\process.exe",
                    "-m=D:\\TrashC\\yolov5\\yolov5s.pt",
                    "-y=D:\\TrashC\\yolov5\\data\\coco128.yaml",
                    "--save_dir=C:\\Users\\86187\\Desktop\\0","-p=95279527hyz", "-d=resource", "-t=multi_resource").start();

            // 获取子进程的错误流
            errorReader = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            // 获取子进程的输入流
            bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
//
//    public BufferedReader getInputStream()
//    {
//        return this.bufferedReader;
//    }

    public void Start(){
        MultiProcessService p = new MultiProcessService();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String line = null;
                    while ((line = p.errorReader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (Exception ex) {
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String line = null;
                    while ((line = p.bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (Exception ex) {
                }
            }
        };

        t1.start();
        t2.start();

        while(t1.isAlive() || t2.isAlive()){}

        try {
            p.bufferedReader.close();
            p.errorReader.close();
            p.proc.destroy();
        } catch (Exception ex) {}


    }
}
