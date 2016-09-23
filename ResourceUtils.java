/**
 * 
 */
package springlol.util;

import java.io.File;
import java.io.InputStream;

/**
 * 用于快速操作资源文件
 * 
 * @author Springlol
 * 
 */
public class ResourceUtils {
    /**
     * 以流的方式获取资源文件
     * 
     * @param resourceName 资源文件名称
     * @return 对应的资源文件输入流
     */
    public static InputStream getResourceAsStream(String resourceName) {
        return getContextClassLoader().getResourceAsStream(resourceName);
    }

    /**
     * 以文件方式获取资源文件
     * 
     * @param resourceName 资源文件名称
     * @return 对应的资源文件名称
     */
    public static File getResourceFile(String resourceName) {
        return new File(getResourceUrl(resourceName));
    }

    /**
     * 获取资源文件路径
     * 
     * @param resourceName 资源文件名
     * @return 对应的资源文件路径
     */
    private static String getResourceUrl(String resourceName) {
        return getContextClassLoader().getResource(resourceName).getFile().substring(1);
    }

    /**
     * 获取classloader
     * 
     * @return classloader对象
     */
    private static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
