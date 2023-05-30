package com.hyw.platform.tools;

import com.hyw.platform.exception.FastRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;

/**
 * ZIP压缩与解压处理
 *
 * @author tangjialin on 2019-10-10.
 */
@Slf4j
public class ZipUtils {
    private static final int BUFFER = 8192;

    /**
     * 解压zip中所有文件到与此文件同级的同名目录下
     * <pre>
     * C:\a\b\c.zip --> C:\a\b\c
     * ~/a/b/c.zip  --> ~/a/b/c
     * </pre>
     *
     * @param zipFile 解压的zip压缩包
     * @return 返回解压后的目录
     */
    public static File decompress(File zipFile) {
        File outDirectory = new File(zipFile.getParentFile(), FilenameUtils.removeExtension(zipFile.getName()));
        decompress(zipFile, outDirectory);
        return outDirectory;
    }

    /**
     * 解压zip文件中所有文件到指定目录
     * 示例:
     * <pre>
     * 解压前 zipFile 文件结构如下:
     * a.zip
     *   - a.txt
     *   - b.txt
     *   - c
     *     - c-a.txt
     *     - c-b.txt
     *  解压后:
     *  outDirectory
     *   - a.txt
     *   - b.txt
     *   - c
     *     - c-a.txt
     *     - c-b.txt
     * </pre>
     *
     * @param zipFile      解压的zip压缩包
     * @param outDirectory 解压后存放的目录
     */
    public static void decompress(File zipFile, File outDirectory) {
        if (zipFile == null || outDirectory == null) {
            throw new FastRuntimeException("解压缩文件或解压后存放目录不能为空");
        }
        if (!zipFile.exists()) { throw new FastRuntimeException("解压文件[" + zipFile + "]不存在"); }
        if (outDirectory.exists() && !outDirectory.isDirectory()) {
            throw new FastRuntimeException("[" + outDirectory + "]不是一个有效的文件目录");
        }
        try (ZipInputStream zipInput = new ZipInputStream(new FileInputStream(zipFile))) {
            byte[] bytes = new byte[BUFFER];
            for (ZipEntry entry; (entry = zipInput.getNextEntry()) != null; ) {
                File outFile = new File(outDirectory, entry.getName());
                outFile.getParentFile().mkdirs();
                try (OutputStream output = new FileOutputStream(outFile)) {
                    for (int count = -1; (count = zipInput.read(bytes)) != -1; ) {
                        output.write(bytes, 0, count);
                    }
                }
            }
        } catch (Exception e) {
            throw new FastRuntimeException("文件[" + zipFile + "]解压失败", e);
        }
    }

    /**
     * 压缩文件(忽略空目录)到与此文件同级目录下,且名称与原文件相同
     * <pre>
     * C:\a\b\c --> C:\a\b\c.zip
     * ~/a/b/c  --> ~/a/b/c.zip
     * </pre>
     *
     * @param srcFile 压缩后的文件
     */
    public static File compress(File srcFile) {
        String fileName = srcFile.isDirectory() ? srcFile.getName() : FilenameUtils.removeExtension(srcFile.getName());
        File zipFile = new File(srcFile.getParentFile(), fileName + ".zip");
        compress(srcFile, zipFile, true);
        return zipFile;
    }

    /**
     * 文件压缩(忽略空目录)
     * <pre>
     *  压缩前:
     *  srcFile
     *   - a.txt
     *   - b.txt
     *   - c
     *     - c-a.txt
     *     - c-b.txt
     *
     * 当 excludeRootDirectory = true 压缩后 zipFile 文件结构如下:
     * srcFile.zip
     *   - a.txt
     *   - b.txt
     *   - c
     *     - c-a.txt
     *     - c-b.txt
     *
     * 当 excludeRootDirectory = false 压缩后 zipFile 文件结构如下:
     * srcFile.zip
     *   - srcFile
     *     - a.txt
     *     - b.txt
     *     - c
     *       - c-a.txt
     *       - c-b.txt
     * </pre>
     *
     * @param srcFile              需要压缩的文件或目录
     * @param zipFile              压缩后的zip压缩包
     * @param excludeRootDirectory 是否需要排除根目录(true:不添加根文件夹,false:添加根文件夹)
     */
    public static void compress(File srcFile, File zipFile, boolean excludeRootDirectory) {
        if (zipFile == null) { throw new FastRuntimeException("压缩后存放文件不能为空"); }
        // 创建父目录
        zipFile.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             // 添加CRC32文件校验
             CheckedOutputStream cos = new CheckedOutputStream(fos, new CRC32());
             ZipOutputStream zipOut = new ZipOutputStream(cos)
        ) {
            compress(srcFile, zipOut, "", excludeRootDirectory);
        } catch (FastRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new FastRuntimeException("文件[" + srcFile + "]压缩失败", e);
        }
    }

    /**
     * 文件压缩(忽略空目录)
     * <pre>
     *  压缩前:
     *  srcFile
     *   - a.txt
     *   - b.txt
     *   - c
     *     - c-a.txt
     *     - c-b.txt
     *
     * 当 excludeRootDirectory = true 压缩后 zipFile 文件结构如下:
     * srcFile.zip
     *   - basedir
     *     - a.txt
     *     - b.txt
     *     - c
     *       - c-a.txt
     *       - c-b.txt
     *
     * 当 excludeRootDirectory = false 压缩后 zipFile 文件结构如下:
     * srcFile.zip
     *   - basedir
     *     - srcFile
     *       - a.txt
     *       - b.txt
     *       - c
     *         - c-a.txt
     *         - c-b.txt
     * </pre>
     *
     * @param srcFile              需要压缩的文件或目录
     * @param outputStream         压缩后的zip压缩包
     * @param basedir              压缩包的基础路径
     * @param excludeRootDirectory 是否需要排除根目录(true:不添加根文件夹,false:添加根文件夹)
     */
    public static void compress(File srcFile, OutputStream outputStream, String basedir, boolean excludeRootDirectory) {
        if (outputStream == null || srcFile == null) {
            throw new FastRuntimeException("需压缩的文件或压缩后存放文件不能为空");
        }
        if (!srcFile.exists()) {
            throw new RuntimeException("需压缩的文件[" + srcFile + "]不存在");
        }

        String path = excludeRootDirectory ? "" : srcFile.getName();
        if (basedir != null && !basedir.isEmpty()) {
            char separator = basedir.charAt(basedir.length() - 1);
            path = (separator == IOUtils.DIR_SEPARATOR_UNIX || separator == IOUtils.DIR_SEPARATOR_WINDOWS ? basedir : basedir + "/") + path;
        }
        ZipOutputStream zipOutput = outputStream instanceof ZipOutputStream ? (ZipOutputStream) outputStream : new ZipOutputStream(outputStream);
        if (srcFile.isDirectory()) {
            File[] files = srcFile.listFiles();
            if (files == null) { return; }
            for (File fileItem : files) {
                compress(fileItem, zipOutput, path + File.separator, false);
            }
        } else {
            log.debug("压缩文件:{}", path);
            try (InputStream input = new FileInputStream(srcFile)) {
                zipOutput.putNextEntry(new ZipEntry(path));
                byte[] bytes = new byte[BUFFER];
                for (int count = -1; (count = input.read(bytes)) != -1; ) {
                    zipOutput.write(bytes, 0, count);
                }
            } catch (Exception e) {
                throw new FastRuntimeException("文件[" + srcFile + "]压缩失败", e);
            }
        }
    }

    public static void main(String[] args) {
        String zipFilePath="D:\\temp\\z.zip";
        String outDir="D:\\temp\\";
        int eachSubZipFileSizeMB = 10; //10MB
        System.out.println("开始分割文件");
        List<File> subFileList = split(new File(zipFilePath), eachSubZipFileSizeMB, outDir);
//        List<File> subFileList = split2(new File(zipFilePath), eachSubZipFileSizeMB, outDir);
        System.out.println("文件分割完成");

        String outWholeFilePath = "D:\\temp\\z_whole.zip";
        File wholeFile = merge(subFileList,outWholeFilePath);

        decompress(wholeFile,new File(outDir));
    }

    /**
     * 按输入大小拆分ZIP文件
     * @param zipFile 需要拆分的ZIP文件
     * @param eachSubZipFileSizeMB　每个子文件大小(MB)
     * @param subZipFileSaveDir 拆分出的子文件保存位置
     */
    public static List<File> split(File zipFile, int eachSubZipFileSizeMB, String subZipFileSaveDir) {
        List<File> subZipFileList = new ArrayList<>();
        if (!zipFile.exists()) {
            return subZipFileList;
        }

        //读取文件名
        String filename = zipFile.getName();
        if(filename.contains(".zip")) {
            filename = filename.substring(0, filename.lastIndexOf(".zip"));
        }

        //拆分文件操作
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(zipFile);
            byte[] part = new byte[1024 * 1024 * eachSubZipFileSizeMB]; // 设置缓存
            int bytesRead; //读取的有效字节长度
            int i = 1; // 分卷序号
            while ((bytesRead = fis.read(part)) != -1) {
                // 子文件文件名：原名_数字
                String subFilePath = subZipFileSaveDir + File.separator + filename + "_part_" + i + ".zip";
                File subFile = new File(subFilePath);
                subZipFileList.add(subFile);
                fos = new FileOutputStream(subFile);
                fos.write(part, 0, bytesRead);
                fos.close();
                i++;
            }
            fis.close();
        }catch (Exception e){
            throw new FastRuntimeException("拆分文件[" + zipFile.getName() + "]失败", e);
        }finally {
            try {
                if (fis != null) fis.close();
                if (fos != null) fos.close();
            }catch (Exception e){
                //不再报错
            }
        }
        return subZipFileList;
    }

    /**
     * 合并文件
     * @param subFileList 子文件
     * @param mergeFilePath 输出合并后的文件全路径
     */
    public static File merge(List<File> subFileList, String mergeFilePath) {
        //异常判断1:传入的子文件List为空，无法合并
        if(CollectionUtils.isEmpty(subFileList)) return null;
        //异常判断1:传入的子文件个数为1，不需要合并
        if(subFileList.size()==1) return subFileList.get(0);

        //合并后的文件
        File wholeFile = new File(mergeFilePath);
        if(!wholeFile.isFile()){
            throw new FastRuntimeException("合并文件，传入的合并文件路径异常！");
        }
        //合并文件
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bos = new BufferedOutputStream(Files.newOutputStream(wholeFile.toPath()));
            byte[] bytes =new byte[1024*1024];//缓存
            int len;
            for (File file : subFileList) {
                bis = new BufferedInputStream(Files.newInputStream(file.toPath()));
                while ((len = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, len);
                }
            }
        } catch (Exception e){
            throw new FastRuntimeException("合并文件失败", e);
        } finally {
            try {
                if (bos != null) bos.close();
                if (bis != null) bis.close();
            }catch (Exception e){
                //不再报错
            }
        }
        return wholeFile;
    }
}
