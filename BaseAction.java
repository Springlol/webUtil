package springlol.web.actions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.ActionSupport;

import springlol.entity.user.User;
import springlol.web.WebUtil;

/**
 * 基础控制器 提供一些action常用功能
 * 
 * @author springlol
 */
@ParentPackage("springlol")
@Namespace("/")
public class BaseAction extends ActionSupport {

    /**  */
    private static final long serialVersionUID = 1L;

    /**
     * 将对象转换成JSON字符串，并输出
     * 
     * @param object 要输出的对象
     * @throws IOException IO异常
     */
    public void writeJson(Object object) {
        WebUtil.writeJson(object);
    }

    /**
     * 将对象转换成JSON字符串，并输出
     * 
     * @param object 要输出的对象
     * @throws IOException IO异常
     */
    public void writeJsonByFilter(Object object, String... args) {
        WebUtil.writeJsonByFilter(object, args);
    }
    
    /**
     * 将对象转换成JSON字符串，并输出
     * 
     * @param object 要输出的对象
     * @throws IOException IO异常
     */
    public void writeJson(Object object, String formatStr) {
        WebUtil.writeJson(object, formatStr);
    }

    public void writeString(String str) {
        WebUtil.writeString(str);
    }

    /**
     * 获取json字符串
     * 
     * @param object
     * @return
     */
    public String getJsonStr(Object object) {
        return WebUtil.getJsonStr(object);
    }

    /**
     * 获取json字符串
     * 
     * @param object
     * @return
     */
    public String getJsonStr(Object object, String... args) {
        return WebUtil.getJsonStrByFilter(object, args);
    }

    /**
     * 获取登录用户
     * 
     * @return
     */
    public User getLoginUser() {
        return WebUtil.getLoginUser();
    }

    /**
     * 获取登录者id
     * 
     * @return
     */
    public String getLoginUserId() {
        return WebUtil.getLoginUserId();
    }

    /**
     * 获取session
     * 
     * @return
     */
    public HttpSession getSession() {
        return WebUtil.getSession();
    }

    /**
     * 获取response
     * 
     * @return
     */
    public HttpServletResponse getResponse() {
        return WebUtil.getResponse();
    }

    /**
     * 获取request
     * 
     * @return
     */
    public HttpServletRequest getRequest() {
        return WebUtil.getRequest();
    }

    /**
     * 输出文件
     * 
     * @param file 要输出的文件
     * @throws IOException
     */
    public void outputFile(File file) throws IOException {
        outputFile(file, "");
    }

    /**
     * 输出文件
     * 
     * @param file 要输出的文件
     * @throws IOException
     */
    public void outputFile(File file, String fileName) throws IOException {
        WebUtil.outputFile(file, fileName);
    }
    
    /**
     * 输出流
     * @param inputStream
     * @param fileName
     * @param length
     * @throws IOException
     */
    public void outputStream(InputStream inputStream, String fileName, long length) throws IOException {
        WebUtil.outputStream(inputStream, fileName, length);
    }
    
    /**
     * 获取布尔型参数
     * @param name
     * @return 如果对应参数为空串或null返回null
     */
    public Boolean getBooleanParameter(String name) {
        return WebUtil.getBooleanParameter(name);
    }
}
