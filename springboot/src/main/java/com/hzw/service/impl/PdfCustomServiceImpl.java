package com.hzw.service.impl;

import com.hzw.entity.AdmissionCard;
import com.hzw.entity.Person;
import com.hzw.service.PdfCustomService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class PdfCustomServiceImpl implements PdfCustomService {


    @Override
    public void generatorAdmissionCard(AdmissionCard admissionCard, HttpServletResponse response) throws IOException {
        InputStream templatePath = new ClassPathResource("static/xh2.pdf").getInputStream();
        if (templatePath==null){
            System.out.println("模本维null");
        }
        // 生成导出PDF的文件名称
        String fileName = admissionCard.getName() + "小黄.pdf";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        // 设置响应头
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + fileName);
        OutputStream out = null;
        ByteArrayOutputStream bos = null;
        PdfStamper stamper = null;
        PdfReader reader = null;
        try {
            // 保存到本地
            // out = new FileOutputStream(fileName);
            // 输出到浏览器端
          //  out = response.getOutputStream();
            // 读取PDF模板表单
            reader =new PdfReader(templatePath);
           // reader = new PdfReader(path + templateName);
            // 字节数组流，用来缓存文件流
            bos = new ByteArrayOutputStream();
            // 根据模板表单生成一个新的PDF
            stamper = new PdfStamper(reader, bos);
            // 获取新生成的PDF表单
            AcroFields form = stamper.getAcroFields();
            // 给表单生成中文字体，这里采用系统字体，不设置的话，中文显示会有问题
            BaseFont font = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            form.addSubstitutionFont(font);
            // 装配数据
            Map<String, Object> data = new HashMap<>(15);
            data.put("projectName", admissionCard.getProjectName());
            data.put("name", admissionCard.getName());
            data.put("phone", admissionCard.getPhone());
            data.put("team", admissionCard.getTeam());
            data.put("workType", admissionCard.getWorkType());
            data.put("bookName",admissionCard.getBookName());
            data.put("code",admissionCard.getCode());
            data.put("plane",admissionCard.getPlane());
            data.put("time",admissionCard.getTime());
            data.put("codeOne", admissionCard.getCodeOne());
            data.put("codeTwo", admissionCard.getCodeTwo());
            data.put("codeThere", admissionCard.getCodeThere());
            data.put("codeFour", admissionCard.getCodeFour());
            data.put("codeFive",admissionCard.getCodeFive());
            data.put("codeSix",admissionCard.getCodeSix());
            // 遍历data，给pdf表单赋值
            for(String key : data.keySet()){
                // 图片要单独处理
                if("codeOne".equals(key)||"codeTwo".equals(key)||"codeThere".equals(key)||"codeFour".equals(key)||"codeSix".equals(key)||"codeFive".equals(key)){
                    int pageNo = form.getFieldPositions(key).get(0).page;
                    Rectangle signRect = form.getFieldPositions(key).get(0).position;
                    float x = signRect.getLeft();
                    float y = signRect.getBottom();
                    String studentImage = data.get(key).toString();
                    //根据路径或Url读取图片
                    Image image = Image.getInstance(studentImage);
                    //获取图片页面
                    PdfContentByte under = stamper.getOverContent(pageNo);
                    //图片大小自适应
                    image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                    //添加图片
                    image.setAbsolutePosition(x, y);
                    under.addImage(image);
                }
                // 设置普通文本数据
                else {
                    form.setField(key, data.get(key).toString());
                }
            }
            // 表明该PDF不可修改
            stamper.setFormFlattening(true);
            // 关闭资源
            stamper.close();
            // 将ByteArray字节数组中的流输出到out中（即输出到浏览器）
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();
            log.info("*****************************PDF导出成功*********************************");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
                if (reader != null) {
                    reader.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private static boolean isValidImageUrl(String url) {
        // A simple check to ensure the URL is valid. In a real scenario, you should do a more thorough check.
        return url != null && !url.isEmpty();
    }
}
