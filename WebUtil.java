/**
 * 
 */
package springlol.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import springlol.entity.user.User;
import springlol.util.ResourceUtils;

/**
 * web层工具类
 * 
 * @author Springlol
 * 
 */
public class WebUtil {

    private static PropertyFilter propertiesFilter = new PropertyFilter() {
        public boolean apply(Object source, String name, Object value) {
            if(source.getClass().equals(User.class) && "password".equals(name)) {// 过滤不需要的字段
                return false;
            }
            return true;
        }
    };
    private static String DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 设置登录用户
     * 
     * @param user
     */
    public static void setLoginUser(User user) {
        getSession().setAttribute("loginUser", user);
    }

    /**
     * 获取登录用户
     * 
     * @return
     */
    public static User getLoginUser() {
        return (User) getSession().getAttribute("loginUser");
    }

    /**
     * 获取登录者id
     * 
     * @return
     */
    public static String getLoginUserId() {
        return getLoginUser().getId();
    }

    /**
     * 获取session
     * 
     * @return
     */
    public static HttpSession getSession() {
        return ServletActionContext.getRequest().getSession();
    }

    /**
     * 获取客户端ip
     * 
     * @return
     */
    public static String getRemoteIp() {
        return getRequest().getRemoteAddr();
    }

    /**
     * 获取response
     * 
     * @return
     */
    public static HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    /**
     * 获取request
     * 
     * @return
     */
    public static HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * 获取json字符串
     * 
     * @param object
     * @return
     */
    public static String getJsonStr(Object object) {
        // 设置默认时间对象格式
        JSON.DEFFAULT_DATE_FORMAT = DEFFAULT_DATE_FORMAT;
        return JSON.toJSONString(object, propertiesFilter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 获取json字符串
     * 
     * @param object
     * @return
     */
    public static String getJsonStr(Object object, String formatStr) {
        // 设置默认时间对象格式
        JSON.DEFFAULT_DATE_FORMAT = formatStr;
        return JSON.toJSONString(object, propertiesFilter, SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 获取json字符串
     * 
     * @param object
     * @return
     */
    public static String getJsonStrByFilter(Object object, String... args) {
        // 设置默认时间对象格式
        JSON.DEFFAULT_DATE_FORMAT = DEFFAULT_DATE_FORMAT;
        return JSON.toJSONString(object, new SimplePropertyPreFilter(args), SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 将对象转换成JSON字符串，并输出
     * 
     * @param object 要输出的对象
     * @throws IOException IO异常
     */
    public static void writeJson(Object object) {
        String jsonStr = "";
        if(object != null) {
            jsonStr = getJsonStr(object);
        }

        writeString(jsonStr);
    }

    /**
     * 将对象转换成JSON字符串，并输出
     * 
     * @param object 要输出的对象
     * @throws IOException IO异常
     */
    public static void writeJson(Object object, String formatStr) {
        String jsonStr = "";
        if(object != null) {
            jsonStr = getJsonStr(object, formatStr);
        }

        writeString(jsonStr);
    }

    /**
     * 将对象转换成JSON字符串，并输出
     * 
     * @param object 要输出的对象
     * @throws IOException IO异常
     */
    public static void writeJsonByFilter(Object object, String... args) {
        String jsonStr = "";
        if(object != null) {
            jsonStr = getJsonStrByFilter(object, args);
        }

        writeString(jsonStr);
    }

    public static void writeString(String str) {
        try {
            // 设置字符编码
            getResponse().setContentType("text/html;charset=UTF-8");
            getResponse().getWriter().write(str);
            getResponse().getWriter().flush();
            getResponse().getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出文件<br />
     * 用于文件下载
     * 
     * @param file
     * @throws IOException
     */
    public static void outputFile(File file) throws IOException {
        outputFile(file, "");
    }

    /**
     * 输出文件<br />
     * 用于文件下载
     * 
     * @param file
     * @throws IOException
     */
    public static void outputFile(File file, String fileName) throws IOException {
        if(file == null) {
            file = getFileNoFoundFile();
        }
        // 取得文件名。
        if(StringUtils.isBlank(fileName)) {
            fileName = file.getName();

        }
        // 以流的形式下载文件。
        InputStream fis = new BufferedInputStream(new FileInputStream(file));
        outputStream(fis, fileName, file.length());
    }
    
    public static void outputStream(InputStream inputStream, String fileName, long length) throws IOException {
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);
        inputStream.close();
        // 清空response
        HttpServletResponse response = getResponse();
        response.reset();
        if(StringUtils.isNotBlank(fileName)) {
         // 设置response的Header
            String encodedFileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
            //由于火狐下 下载有带空格的文件  文件名被截取  删除;filename*=utf-8''" + encodedFileName 就恢复正常
//            response.setHeader("Content-Disposition", "attachment;filename=\"" + encodedFileName + "\";filename*=utf-8''" + encodedFileName);
            response.setHeader("Content-Disposition", "attachment;filename=\"" + encodedFileName + "\"");
        }
        if(length > 0) {
            response.addHeader("Content-Length", "" + length);
        }
        response.setCharacterEncoding("UTF-8");
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream;charset=UTF-8");
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
    }

    private static File getFileNoFoundFile() {
        return ResourceUtils.getResourceFile("others/文件不存在.txt");
    }
    
    //修改密码后改变session中loginUser的isChangedPWD
    public static void updateChangePwdFlagInSession(){
        User user = getLoginUser();
        user.setIsChangedPWD(true);
        setLoginUser(user);
    }
    
    /**
     * 获取布尔型参数
     * @param name
     * @return 如果对应参数为空串或null返回null
     */
    public static Boolean getBooleanParameter(String name) {
        Boolean b = null;
        String bStr = getRequest().getParameter("state");
        if(StringUtils.isNotBlank(bStr)) {
            b = Boolean.valueOf(bStr);
        }
        return b;
    }
}
