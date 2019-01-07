package com.scloudyy.springbackend.util;

import com.scloudyy.springbackend.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();  // ??
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random random = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * transfer CommonsMultipartFile into file
     * @param cFile
     * @return newFile
     */
    public static File transferCommonsMultipartFileToFile (CommonsMultipartFile cFile) {
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * make dir along the targetAddr recursively
     * @param targetAddr
     */
    private static void makeDirPath (String targetAddr) {
        String realFileParentPath = PathUtil.getImageBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * return the extension name of input file name
     * @param fileName
     * @return extension name
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring((fileName.lastIndexOf(".")));
    }

    /**
     * return the random file name
     * @return random file name
     */
    public static String getRandomFileName() {
        int ranNum = random.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + ranNum;
    }

    /**
     * process thumbnail, return relative path of generated imageß
     * @param thumbnail
     * @param targetAddr
     * @return relativeAddr
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relative address is :" + relativeAddr);

        File dest = new File(PathUtil.getImageBasePath() + relativeAddr);
        logger.debug("current absolute address is :" + PathUtil.getImageBasePath() + relativeAddr);

        try {
            Thumbnails.of(thumbnail.getImage()).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")), 0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException("Fail to generate thumbnail: " + e.toString());
        }
        return relativeAddr;
    }

    /**
     *
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateNormalImage (ImageHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is :" + relativeAddr);

        File dest = new File(PathUtil.getImageBasePath() + relativeAddr);
        logger.debug("current absolute addr is :" + PathUtil.getImageBasePath() + relativeAddr);

        try {
            Thumbnails.of(thumbnail.getImage()).size(337, 400)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")), 0.25f)
                    .outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException("Fail to generate thumbnail：" + e.toString());
        }
        return relativeAddr;
    }

    public static void deleteFileOrPath(String storePath) {
        File fileOrPath = new File(PathUtil.getImageBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for (File f : files) {
                    f.delete();
                }
            }
            fileOrPath.delete();
        }
    }
}
