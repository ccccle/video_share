package com.cle.video_share_backend.utils;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.*;

import java.io.*;
import java.util.UUID;

public class FfmpegUtil {
    //把其他格式的视频转换为MP4格式
    public static String videoToMP4(InputStream inputStream) {
        String outputVideoPath = UUID.randomUUID()+".mp4"; // 输出视频文件的路径

        try {

            String[] cmd = {
                    "ffmpeg",
                    "-i", "pipe:0", // 使用管道作为输入
                    "-analyzeduration", "2147483647", // 设置适当的值
                    "-probesize", "2147483647", // 设置适当的值
                    "-c:v", "libx264",
                    "-c:a", "aac",
                    outputVideoPath
            };

            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // 将输入流数据写入FFmpeg的标准输入流
            Thread inputThread = new Thread(() -> {
                try {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        process.getOutputStream().write(buffer, 0, bytesRead);
                    }
                    process.getOutputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            inputThread.start();

            // 读取FFmpeg输出
            InputStream ffmpegInputStream = process.getInputStream();
            java.util.Scanner scanner = new java.util.Scanner(ffmpegInputStream).useDelimiter("\\A");
            String ffmpegOutput = scanner.hasNext() ? scanner.next() : "";

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("转换完成: " + outputVideoPath);
            } else {
                System.err.println("转换失败: " + ffmpegOutput);
            }

            return outputVideoPath;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFirstFrame(String videoPath) {
        String outputImagePath = UUID.randomUUID() + ".png"; // 输出视频文件的路径
        // 使用 ffmpeg 获取视频封面
        String cmd = "ffmpeg -i " + videoPath + " -ss 00:00:01 -vframes 1 -y -loglevel quiet " + outputImagePath;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return outputImagePath;
    }

}



