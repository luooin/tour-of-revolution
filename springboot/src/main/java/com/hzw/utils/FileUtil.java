package com.hzw.utils;

import com.hzw.exception.GlobalExceptionHandler;
import com.hzw.exception.GlobalExceptionMyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.io.*;

/**
 * @author xiaohuang
 * @date 2024/1/14
 */
public class FileUtil {
    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    public InputStream getObject(String file,String fileName) throws GlobalExceptionMyHandler {
        FileInputStream inputStream = null;

        try {
            String realPath = "D/data/file" + File.separator + file+File.separator+fileName;
            inputStream = new FileInputStream(realPath);
            return inputStream;
        } catch (FileNotFoundException var6) {
            throw new GlobalExceptionMyHandler("文件不存在");
        } catch (Exception var7) {
            log.error("读取文件失败", var7);
            throw new GlobalExceptionMyHandler("读取文件失败");
        }
    }
    public byte[] getByte(String file,String fileName) {
        try {
            log.info("开始读取图片输入流....");
            InputStream inputStream = getObject(file,fileName);
            log.info("读取图片结束....");
            if (ObjectUtils.isEmpty(inputStream)){
                throw new GlobalExceptionMyHandler("inputStream照片输入流空！！");
            }
            log.error("获取照片输入流");
            // 使用ByteArrayOutputStream来将输入流中的数据转换为字节数组
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            // 关闭输入流
            inputStream.close();
            // 获取字节数组
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            // 关闭ByteArrayOutputStream
            byteArrayOutputStream.close();
            return byteArray;
        } catch (Exception e) {
            throw new GlobalExceptionMyHandler("图片查看失败"+e.getMessage());
        }

    }
}
