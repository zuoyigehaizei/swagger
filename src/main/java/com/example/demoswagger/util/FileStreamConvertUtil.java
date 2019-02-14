package com.example.demoswagger.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileStreamConvertUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileStreamConvertUtil.class);

    /**
     * 将pdf转换成图片二进制流
     * http://blog.csdn.net/chenjhit/article/details/54410025
     */
    public static List<byte[]> pdfToImage(String filePath) {
        List<byte[]> imagesStream = new ArrayList<byte[]>();
        try {
            PDDocument doc = PDDocument.load(new File(filePath));
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                ByteArrayOutputStream os = null;
                ImageOutputStream ios = null;
                try {
                    BufferedImage image = renderer.renderImage(i, 1.5f);//scale=dpi/75f
                    os = new ByteArrayOutputStream();

                    ImageWriter imageWriter = ImageIO.getImageWritersBySuffix("jpg").next();
                    ios = ImageIO.createImageOutputStream(os);
                    imageWriter.setOutput(ios);

                    IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image), null);

                    ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
                    imageWriteParam.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
                    imageWriteParam.setCompressionQuality(0.7f);//压缩率
                    imageWriter.write(imageMetaData, new IIOImage(image, null, null), imageWriteParam);

                    imagesStream.add(os.toByteArray());
                } finally {
                    if (os != null) {
                        os.close();
                    }
                    if (ios != null) {
                        ios.close();
                    }
                }

            }
        } catch (Exception e) {
            LOGGER.error("pdf To Image exception e");
        }
        return imagesStream;

    }

    /**
     * 根据url获取InputStream
     * @param path
     * @return
     */
    public static byte[] returnBitMap(String path) {
        URL url = null;
        InputStream is =null;
        HttpURLConnection conn=null;
        byte[] byteStream=null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            LOGGER.error("return bit map url exception e:{}",e.getLocalizedMessage());
        }
        try {
            conn = (HttpURLConnection) url.openConnection();//利用HttpURLConnection对象,可以从网络中获取网页数据.
            conn.setDoInput(true);
            conn.setConnectTimeout(30000);
            conn.connect();
            is = conn.getInputStream(); //得到网络返回的输入流
            byteStream=toByteArray(is);
        } catch (IOException e) {
            LOGGER.error("return bit map inputStream exception e:{}",e.getLocalizedMessage());
        }finally{
            try {
                if(conn!=null){
                    conn.disconnect();
                }
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                LOGGER.error("inputStream close exception e:{}",e.getLocalizedMessage());
            }
        }
        return byteStream;
    }

    public static byte[] toByteArray(InputStream input){
        byte[] outBytes=null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        try {
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            outBytes=output.toByteArray();
        } catch (IOException e) {
            LOGGER.error("to Byte Array exception e:{}",e.getLocalizedMessage());
        }finally{
            try {
                if(output!=null){
                    output.close();
                }
            } catch (IOException e) {
                LOGGER.error("byteArrayOutputStream close exception e:{}",e.getLocalizedMessage());
            }
        }
        return outBytes;
    }



}
