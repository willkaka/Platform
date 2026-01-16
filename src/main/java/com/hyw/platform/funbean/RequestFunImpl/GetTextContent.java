package com.hyw.platform.funbean.RequestFunImpl;

import com.hyw.platform.exception.BizException;
import com.hyw.platform.exception.FastRuntimeException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.web.req.PublicReq;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service("getTextContent")
public class GetTextContent extends RequestFunUnit<String, GetTextContent.QueryVariable> {

    /**
     * 输入参数检查
     * @param dto 参数
     */
    @Override
    public void checkVariable(GetTextContent.QueryVariable dto){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(dto.getFullPath()),"文件路径,不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, GetTextContent.QueryVariable dto){

        File file = new File(dto.getFullPath());
        if(!file.isFile()){
            throw new FastRuntimeException("文件路径不是文件!");
        }
        // 判断文件类型
        if(!dto.getFullPath().endsWith(".txt") && !dto.getFullPath().endsWith(".text")){
            throw new FastRuntimeException("该文件不是txt文件!暂不支持查看。");
        }

//        String[] commonEncodings = {"GBK", "UTF-8", "GB2312", "ISO-8859-1", "Windows-1252"};
        StringBuilder sb = null;
//        for (String encoding : commonEncodings) {
            sb = new StringBuilder();
            try {
                String encoding = detectEncoding(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(dto.getFullPath())), encoding));
                String line;
                while ((line = br.readLine()) != null) {
//                    if(isChineseGarbled(line) && !encoding.equals(commonEncodings[commonEncodings.length-1])){
//                        break;
//                    }
                    sb.append(line).append("\n");
                }
            } catch (IOException e) {
                log.error("读文件数据异常!",e);
                throw new FastRuntimeException("读文件数据异常!");
            }
//        }
        return sb==null?null:sb.toString();
    }

        /**
         * 检测文件编码是GBK还是UTF-8
         * @param file 要检测的文件
         * @return "GBK", "UTF-8" 或 "UNKNOWN"
         */
        public String detectEncoding(File file) throws IOException {
            // 1. 首先检查UTF-8 BOM
            if (hasUTF8BOM(file)) {
                return "UTF-8";
            }

            // 2. 读取文件内容进行分析
            byte[] content = readFirstBytes(file, 4096); // 读取前4KB足够判断

            // 3. 检查是否为有效的UTF-8
            if (isValidUTF8(content)) {
                return "UTF-8";
            }

            // 4. 假设是GBK编码
            if (isLikelyGBK(content)) {
                return "GBK";
            }

            return "UNKNOWN";
        }

        // 检查UTF-8 BOM (EF BB BF)
        private boolean hasUTF8BOM(File file) throws IOException {
            try (InputStream in = new FileInputStream(file)) {
                byte[] bom = new byte[3];
                int read = in.read(bom);
                return read == 3 && bom[0] == (byte)0xEF && bom[1] == (byte)0xBB && bom[2] == (byte)0xBF;
            }
        }

        // 读取文件前N个字节
        private byte[] readFirstBytes(File file, int numBytes) throws IOException {
            byte[] buffer = new byte[numBytes];
            try (InputStream in = new FileInputStream(file)) {
                int read = in.read(buffer);
                if (read < buffer.length) {
                    byte[] shortened = new byte[read];
                    System.arraycopy(buffer, 0, shortened, 0, read);
                    return shortened;
                }
                return buffer;
            }
        }

        // 检查是否为有效UTF-8编码
        private boolean isValidUTF8(byte[] data) {
            try {
                String test = new String(data, "UTF-8");
                // 再编码回字节，检查是否一致
                byte[] utf8Bytes = test.getBytes("UTF-8");
                if (utf8Bytes.length != data.length) {
                    return false;
                }
                for (int i = 0; i < data.length; i++) {
                    if (utf8Bytes[i] != data[i]) {
                        return false;
                    }
                }
                return true;
            } catch (UnsupportedEncodingException e) {
                return false;
            }
        }

        // 判断可能是GBK编码
        private boolean isLikelyGBK(byte[] data) {
            try {
                String test = new String(data, "GBK");
                // GBK编码的中文字符通常是2个字节
                // 检查是否有连续的无效字符
                int invalidCount = 0;
                for (int i = 0; i < test.length(); i++) {
                    char c = test.charAt(i);
                    // GBK中常见的中文字符范围
                    if (c == '�' || (c >= 0xD800 && c <= 0xDFFF)) {
                        invalidCount++;
                        if (invalidCount > test.length() / 10) { // 超过10%的无效字符
                            return false;
                        }
                    }
                }
                return true;
            } catch (UnsupportedEncodingException e) {
                return false;
            }
        }


    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String fullPath;
    }
}
