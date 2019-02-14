package com.example.demoswagger.controller;

import com.example.demoswagger.util.FileStreamConvertUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@RestController
public class FileController {
//    @Value("${filePath}")
//    private String filePath;

    @GetMapping("/downLoadFile")
    public void downLoadFile(HttpServletResponse response) {
        System.out.println("aaaaaaa");
        //pdf转图片二进制流
        List<byte[]> pdfToImages = FileStreamConvertUtil.pdfToImage("C:/image/Docker.pdf");

        InputStream inputStream = null;
        OutputStream toClient = null;
        byte[]  buffer = null;
        if(pdfToImages.size() > 0) { //判断文件父目录是否存在
            try {

                for (int i=0;i<pdfToImages.size();i++) {
                    buffer = pdfToImages.get(i);
                    if (null != buffer && buffer.length > 0) {
                        String filename= i +".jpg";
                        // 清空response
                        response.reset();
                        // 设置response的Header
                        response.addHeader("Content-Disposition","attachment;filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
                        response.addHeader("Content-Length", "" + buffer.length);
                        response.setContentType("image/jpeg");
                        toClient = response.getOutputStream();
                        toClient.write(buffer);
                        toClient.flush();
                        toClient.close();
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException("下载错误!",e);
            }
        }
    }

    /**
     * 从输入流中获取数据
     * @param
     * @return
     * @throws Exception
     */
    private byte[] readInputStream(InputStream fis) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=fis.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        fis.close();
        return outStream.toByteArray();
    }
}
