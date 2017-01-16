package springlol.web.dao;
import java.util.Collection;

public class DaoUtil {

	/**
	 * 获取查询语句前缀
	 *
	 * @param c
	 * @return hql查询语句前缀
	 */
	@SuppressWarnings("rawtypes")
	public static String getFindPrefix(Class c) {
		return "from " + c.getSimpleName();
	}

	/**
	 * 获取统计语句前缀
	 *
	 * @param c
	 * @return hql统计语句前缀
	 */
	@SuppressWarnings("rawtypes")
	public static String getCountPrefix(Class c) {
		return "select count(*) from " + c.getSimpleName();
	}

	/**
	 * 生成in查询字符串
	 *
	 * @return
	 */
	public static String generateInStr(Collection<String> strs) {
		String result = "";
		if(strs != null) {
			for (String str : strs) {
				result += "'" + str + "',";
			}
		}
		if("".equals(result)) {
			return "''";
		}
		return result.substring(0, result.length() - 1);
	}
}
