package com.hzw.utils;

import cn.afterturn.easypoi.word.WordExportUtil;
import com.hzw.exception.GlobalExceptionMyHandler;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PdfUtil {
    private static final Logger log = LoggerFactory.getLogger(PdfUtil.class);
    // 利用模板生成pdf

    public static byte[] generatePdfToLocal(Map<String, Object> data,Map<String,byte[]> map) {
        // 模板路径
        InputStream templatePath = null;
        try {
            templatePath = new ClassPathResource("static/tz.pdf").getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (templatePath==null){
            return null;
        }
        PdfReader reader;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            // 设置输出字体
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            reader = new PdfReader(templatePath);// 读取pdf模板
            log.info("开始读取pdf模板....");
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            form.addSubstitutionFont(bf);

            // 遍历data，给pdf表单赋值
            for (String key : map.keySet()) {
                byte[] bytes = map.get(key);
               // 图片要单独处理
                if("cardIdImgFront".equals(key)||"cardIdImgBack".equals(key)||"workPermitImg".equals(key)||"workPermitImgCopy".equals(key)||"workPermitVerify".equals(key)||"workerHeaderPhoto".equals(key)){
                    int pageNo = form.getFieldPositions(key).get(0).page;
                    Rectangle signRect = form.getFieldPositions(key).get(0).position;
                    float x = signRect.getLeft();
                    float y = signRect.getBottom();

           //         String studentImage = data.get(key).toString();
//                    File file = new File(studentImage);
//                    byte[] bytes = File2byte(file);
//                    根据路径或Url读取图片
                    if (bytes == null) {
                        throw new GlobalExceptionMyHandler("图片 bytes are empty");
                    }

                    Image image = Image.getInstance(bytes);
                    //  Image image = Image.getInstance(studentImage);
                    //获取图片页面
                    PdfContentByte under = stamper.getOverContent(pageNo);
                    //图片大小自适应
                    image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                    //添加图片
                    image.setAbsolutePosition(x, y);
                    under.addImage(image);
                }
            }

            for(String key : data.keySet()){
                    form.setFieldProperty(key, "textfont", bf, null);
                    form.setFieldProperty(key, "textSize", new Float(10), null);
                    form.setField(key, data.get(key).toString());
            }

            // 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
            stamper.setFormFlattening(true);
            stamper.close();
            return bos.toByteArray();
        } catch (IOException e) {
            log.error("生成pdf失败",e);
            return null;
        } catch (DocumentException e) {
            log.error("生成pdf失败",e);
            return null;
        } finally
        {
                try {
                    if(null != templatePath)
                        templatePath.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    public static void addFileToZip(Map<String, byte[]> pdfs, String fileName, HttpServletResponse response, String fileType) {
        // Ensure pdfs is not empty
        if (pdfs.isEmpty()) {
            throw new GlobalExceptionMyHandler(fileType+"map集合为空");
        }
        try {
            // 设置响应头
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8")+".zip");
            // 获取响应的输出流
            ServletOutputStream out = response.getOutputStream();
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(out)) {
                // 循环遍历pdf并将它们添加到ZIP文件中
                for (Map.Entry<String, byte[]> entry : pdfs.entrySet()) {
                    String pdfName = entry.getKey();
                    byte[] pdfContent = entry.getValue();
                    // 为每个PDF创建一个ZIP条目
                    zipOutputStream.putNextEntry(new ZipEntry(pdfName + "."+fileType));
                    log.info("开始将"+fileType+"内容写入Zip.......");
                    // 将PDF内容写入ZIP文件
                    zipOutputStream.write(pdfContent);
                    log.info("写入Zip结束........");
                    zipOutputStream.closeEntry();
                }
            }
            // 关闭响应输出流
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new GlobalExceptionMyHandler("生成zip异常");

        }
    }

	private static void  createPdf(byte[] pdfData ,HttpServletResponse response,String fileName){
		try {
			// 设置响应头
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8")+".pdf");
			//设置字符编码
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new GlobalExceptionMyHandler(e.getMessage());
		}
		try (OutputStream out = response.getOutputStream()) {
			out.write(pdfData);
		} catch (IOException e) {
			// 处理输出流操作中的异常
			e.printStackTrace();
			throw new GlobalExceptionMyHandler("生成pdf异常");
		}

	}

    /**
     * 使用模板doc导出，表单加图片
     * @param params
     * @return
     */
    public static byte[] exportWord(Map<String, Object> params) {
        try {
            String templatePath=null;
                String tempDirPath = "/data/file/docx";
                // 检查临时目录是否存在，如果不存在，则创建它
                File tempDir = new File(tempDirPath);
                if (!tempDir.exists()) {
                    boolean created = tempDir.mkdirs();
                    if (!created) {
                        log.error("无法创建临时目录：" + tempDirPath);
                        // 处理无法创建临时目录的异常
                    }
                }
             // 检查模板文件是否已存在于临时目录，如果不存在则从Classpath中复制到临时目录
                File templateFile = new File(tempDir, "jxxj.docx");
                if (!templateFile.exists()) {
                    log.info("模板文件不存在于临时目录，将从Classpath中复制。");
                    try {
                        ClassPathResource resource = new ClassPathResource("static/jxxj.docx");
                        InputStream inputStream = resource.getInputStream();
                        if (ObjectUtils.isEmpty(inputStream)){
                            log.info("获取模板inputStream为空.......");
                        }
                        try (FileOutputStream outputStream = new FileOutputStream(templateFile)) {
                            byte[] buffer = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }
                            log.error("写入临时目录结束:"+tempDirPath);
                        }
                    } catch (IOException e) {
                        log.error("无法复制模板文件到临时目录：" + e.getMessage());
                        // 处理异常，例如记录错误日志或抛出异常
                        throw new GlobalExceptionMyHandler("无法复制模板文件到临时目录：" + e.getMessage());
                    }
                    templatePath = templateFile.getAbsolutePath();
                } else {
                    log.error("使用现有模板文件：" + templateFile.getAbsolutePath());
                    templatePath = templateFile.getAbsolutePath();
                }
            log.error("开启读取doc模板,最终模版路径："+templatePath);
           XWPFDocument doc = WordExportUtil.exportWord07(templatePath, params);
            log.info("读取doc模板结束.....");
            // 创建一个字节数组输出流
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
           // 将XWPFDocument写入字节数组输出流
            doc.write(byteArrayOutputStream);
           // 获取字节数组
            byte[] documentBytes = byteArrayOutputStream.toByteArray();
            log.error("返回doc文件字节数组：", Arrays.toString(documentBytes));
            return documentBytes;
        } catch (Exception e) {
          throw new GlobalExceptionMyHandler("生成doc文件失败"+e.getMessage());
        }
    }



    /**
     * 删除零时生成的文件
     */
    public static void delFileWord(String filePath, String fileName) {
        File file = new File(filePath + fileName);
        File file1 = new File(filePath);
        file.delete();
        file1.delete();
    }

    public InputStream getObject(String realPath) throws GlobalExceptionMyHandler {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(realPath);
            return inputStream;
        } catch (FileNotFoundException var6) {
            throw new GlobalExceptionMyHandler("文件不存在");
        } catch (Exception var7) {
            log.error("读取文件失败", var7);
            throw new GlobalExceptionMyHandler("读取文件失败");
        }
    }


    public byte[] getByte(String realPath) {
        FileInputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            inputStream = new FileInputStream(realPath);
            if (ObjectUtils.isEmpty(inputStream)){
                throw new GlobalExceptionMyHandler("获取特种人员证件照片失败！");

            }
            log.error("获取照片输入流");
            // 使用ByteArrayOutputStream来将输入流中的数据转换为字节数组
            byteArrayOutputStream = new ByteArrayOutputStream();
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
            log.info("返回图片字节数组...");
            return byteArray;
        } catch (Exception e) {
            throw new GlobalExceptionMyHandler("图片查看失败"+e);
        }finally {
            try {
                if (inputStream!=null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static byte[] File2byte(File tradeFile) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
