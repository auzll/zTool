/**
 * https://github.com/auzll/zTool
 */
package z.tool.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import z.tool.checker.HumanNeededError;

import com.google.common.collect.Lists;
import com.yahoo.platform.yui.compressor.Bootstrap;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

/**
 * 打包压缩工具
 */
public final class WarCompressor {
    private final String charset = StringUtil.CHARSET_UTF_8;
    private final int randomNameSize = 15;
    
    public static enum JavascriptCompressType {
        Compress, AsSingleLine
    }
    
    private final String scriptOrCssNewPrefix = "Original_" + randomString(randomNameSize) + "_";
    private final Pattern INNER_SCRIPT_PATTERN = Pattern.compile(
            ".*[.\r\n]*(<script\\s+type=[\"']text/javascript[\"']\\s{0,}>(<\\!\\-\\-){0,1}(.*?)(//){0,1}(\\-\\->){0,1}</script>?)[.\r\n]*.*", 
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    
//    private final Pattern CHANGE_LINE_PATTERN = Pattern.compile(".*((<\\!\\-\\-)|(//\\-\\->))+.*", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    
    private final Pattern DOC_PATTERN = Pattern.compile("<\\!\\-\\-(.*?)\\-\\->");
    
    private static ErrorReporter ERROR_REPORTER = new ErrorReporter() {
        public void warning(String message, String sourceName,
                int line, String lineSource, int lineOffset) {
            System.out.println("method:warning,message:" + message + ",sourceName:" 
                    + sourceName + ",line:" + line + ",lineSource:" + lineSource 
                    + ",lineOffset:" + lineOffset);
        }
        public void error(String message, String sourceName,
                int line, String lineSource, int lineOffset) {
            System.out.println("method:error,message:" + message + ",sourceName:" 
                    + sourceName + ",line:" + line + ",lineSource:" + lineSource 
                    + ",lineOffset:" + lineOffset);
        }
        public EvaluatorException runtimeError(String message,
                String sourceName, int line, String lineSource,
                int lineOffset) {
            error(message, sourceName, line, lineSource, lineOffset);
            return new EvaluatorException(message);
        }
    };
    
    private static String randomString(int len) {
        final int max = 26 * 2 + 10;
        StringBuilder buff = new StringBuilder();
        Random random = new Random();
        for (int i = len; i > 0; i--) {
            int rd = random.nextInt(max);
            if(rd < 10) {
                buff.append(rd);
            } else if (rd < 36) {
                buff.append((char) ('a' + (rd - 10) / 2)); // 小写字母
            } else {
                buff.append((char) ('A' + (rd - 10) / 2)); // 大写字母
            }
        }
        return buff.toString();
    }
    
    private static String randomFileName(int len, String suffix) {
        FastDateFormat dateFormat = FastDateFormat.getInstance("yyyyMMddHHmmss");
        return randomString(len) + "." + dateFormat.format(System.currentTimeMillis()) + "." + suffix;
    }
    
    static class ScriptOrCss {
        String srcName;
        String distName;
        ScriptOrCss(String srcName, String distName) {
            this.srcName = srcName;
            this.distName = distName;
        }
    }
    
    private void compressJspOrHtml(String filePath, List<ScriptOrCss> scriptOrCssFileNames, JavascriptCompressType compressType) throws Exception {
        File filePathObject = new File(filePath);
        String[] fileNames = filePathObject.list();
        if (null == fileNames || fileNames.length < 1) {
            return;
        }
        
        for (String fileName : fileNames) {
            File fileObject = new File(filePathObject, fileName); 
            if (fileObject.isDirectory()) {
                // 忽略目录
                continue;
            }
            
            // 把jsp或html内容放到buff中，压缩空格和换行符
            BufferedReader reader = null;
            StringBuilder buff = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileObject), charset));
                String line = null;
                while ( null != (line = reader.readLine()) ) {
                    line = StringUtil.trimAndTryReturnNull(line);
                    if (null != line) {
                        buff.append(line);
                    }
                }
            } finally {
                IOUtils.closeQuietly(reader);
            }
            
            // jsp或html正文内容
            String jspOrHtml = buff.toString();
            
            // 压缩jsp或html的内嵌js
            List<String> matchList = Lists.newArrayList();
            while (true) {
                Matcher matcher = INNER_SCRIPT_PATTERN.matcher(jspOrHtml);
                if (!matcher.matches()) {
                    break;
                }
                
                if (5 != matcher.groupCount()) {
                    buff = new StringBuilder();
                    for (int i = 1; i <= matcher.groupCount(); i++) {
                        buff.append("group").append(i).append(":").append(matcher.group(i)).append(",");
                    }
                    throw new HumanNeededError("method:compress," + buff.toString() + "desc:group count not match");
                }
                
                String innerScript;
                // matcher.group(1) 是包含<script type..标记的，matcher.group(3)就是内部的具体函数
                String scriptBody = matcher.group(1).trim(); // 含script标记的
                if (JavascriptCompressType.Compress == compressType) {
                    innerScript = "<script type=\"text/javascript\">" + compressJavaScriptString(matcher.group(3).trim()) + "</script>";
                } else if (JavascriptCompressType.AsSingleLine == compressType) {
                    innerScript = "<script type=\"text/javascript\">" + matcher.group(3).trim() + "</script>";
                } else {
                    throw new RuntimeException("unknow type[" + compressType + "]");
                }
                matchList.add(innerScript);
                jspOrHtml = jspOrHtml.replace(scriptBody, "${" + (matchList.size() - 1) + "}");
            }
            if (matchList.size() > 0) {
                for (int i = 0, size = matchList.size(); i < size; i++) {
                    jspOrHtml = jspOrHtml.replace("${" + i + "}", matchList.get(i));
                }
            }
            
            // 删除html注释内容
            Matcher matcher = DOC_PATTERN.matcher(jspOrHtml);
            StringBuffer qBuff = new StringBuffer();
            while (matcher.find()) {
                matcher.appendReplacement(qBuff, "");
            }
            matcher.appendTail(qBuff);
            jspOrHtml = qBuff.toString();
            
            // 用压缩后的js和css文件替换到jsp或html内的引用内容
            for (ScriptOrCss sc : scriptOrCssFileNames) {
                jspOrHtml = jspOrHtml.replaceAll(sc.srcName, sc.distName);
            }
            
            // 把最新的jsp或html内容写回到文件中
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileObject), charset));
                writer.write(jspOrHtml);
                writer.flush();
            } finally {
                IOUtils.closeQuietly(writer);
            }
        }
        
    }
    
    public static String compressJavaScriptString(String script) throws Exception {
        String result = script; // 压缩失败则直接返回原内容
        StringReader reader = null;
        StringWriter writer = null;
        
        try {
            reader = new StringReader(result);
            writer = new StringWriter();
            
            JavaScriptCompressor compressor = new JavaScriptCompressor(reader, ERROR_REPORTER);
            compressor.compress(writer, -1, true, false, false, false);
            
            result = writer.toString();
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(writer);
        }

        return result;
    }
    
    private String getSuffix(String fileName) {
        int index = fileName.lastIndexOf('.');
        return fileName.substring(index + 1);
    }
    
    /**
     * 压缩指定目录下的所有js或css文件(不递归检查目录)，在原路径压缩并保存新文件，旧文件将被重命名
     */
    private List<ScriptOrCss> compressScriptOrCss(String filePath) throws Exception {
        List<ScriptOrCss> list = Lists.newArrayList();
        
        File filePathObject = new File(filePath);
        String[] fileNames = filePathObject.list();
        if (null == fileNames || fileNames.length < 1) {
            return list;
        }
        
        for (String fileName : fileNames) {
            File srcFileObject = new File(filePathObject, fileName);
            if (srcFileObject.isDirectory()) {
                // 忽略目录
                continue;
            }
            
            String newFileName = randomFileName(randomNameSize, getSuffix(fileName));
            File distFileObject = new File(filePathObject, newFileName);
            
            // 压缩javascript或css
            String[] bootstrapArgs = {
                srcFileObject.getAbsolutePath(),
                "-o",
                distFileObject.getAbsolutePath(),
                "--charset",
                charset
            };
            Bootstrap.main(bootstrapArgs);
            
            // 重命名原文件
            srcFileObject.renameTo(new File(filePathObject, scriptOrCssNewPrefix + fileName));
            
            list.add(new ScriptOrCss(fileName, newFileName));
        }
        
        return list;
    }
    
    public void compress(String[] jspOrHtmlPaths, String[] jsOrCssPaths, JavascriptCompressType compressType) throws Exception {
        // 必须先压缩js、css
        List<ScriptOrCss> scriptOrCssList = Collections.emptyList();
        if (null != jsOrCssPaths && jsOrCssPaths.length > 0) {
            scriptOrCssList = Lists.newArrayList();
            for (String jsOrCssPath : jsOrCssPaths) {
                List<ScriptOrCss> tmp = compressScriptOrCss(jsOrCssPath);
                if (!ZUtils.isEmpty(tmp)) {
                    scriptOrCssList.addAll(tmp);
                }
            }
        }
        
        // 压缩jsp、html
        if (null != jspOrHtmlPaths && jspOrHtmlPaths.length > 0) {
            for (String jspOrHtmlPath : jspOrHtmlPaths) {
                compressJspOrHtml(jspOrHtmlPath, scriptOrCssList, compressType);
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        SimpleLogUtil.info("Param: " + Arrays.toString(args));
        
        String tips = "Usage: java WarCompressor jspOrHtmlPaths jsOrCssPaths [JavascriptCompressType" + Arrays.toString(JavascriptCompressType.values()) + "]";
        if (args.length < 2) {
            System.out.println(tips);
            System.exit(0);
        }
        
        String[] jspOrHtmlPaths = null;
        String[] jsOrCssPaths = null;
        JavascriptCompressType compressType = JavascriptCompressType.AsSingleLine;
        
        if (!"null".equalsIgnoreCase(args[0])) {
            jspOrHtmlPaths = args[0].split(",");
        }
        
        if (!"null".equalsIgnoreCase(args[1])) {
            jsOrCssPaths = args[1].split(",");
        }
        
        if (args.length > 2 && !"null".equalsIgnoreCase(args[2])) {
            compressType = JavascriptCompressType.valueOf(args[2]);
        }
     
        new WarCompressor().compress(jspOrHtmlPaths, jsOrCssPaths, compressType);
    }

}