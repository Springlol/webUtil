package springlol.web.service;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by zhoutao on 2017/1/5.
 */
@SuppressWarnings({"rawTypes,unchecked"})
public abstract class BaseService<T> {
	/**
	 * 继承BaseService<T></>，初始化对象时，会初始化aClass为给定类型
	 */
	private Class aClass;

	@Resource
	private BaseDao baseDao;

	public BaseService(){
		//反射泛型
		Type type = this.getClass().getGenericSuperclass();
		if(type instanceof ParameterizedType){
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type type1 = parameterizedType.getActualTypeArguments()[0];
			this.aClass = (Class)type1;
		}
	}

	/**
	 * 根据Id查询实体对象
	 * @param id
	 * @return
	 */
	public T getByEntityId(String id) {
		return (T)baseDao.get(aClass, id);
	}

	/**
	 * 根据指定类型，id查询实体对象
	 * @param t
	 * @param id
	 * @return
	 */
	public T getByEntityId(Class<T> t,String id){
		return (T)baseDao.get(t,id);
	}

	/**
	 * 查询所有
	 * @return
	 */
	public List<T> findAll(){
		String hql = DaoUtil.getFindPrefix(aClass);
		return baseDao.find(hql);
	}

	/**
	 * 查询指定类型的对象
	 * @param tClass
	 * @return
	 */
	public List<T> findAll(Class<T> tClass){
		String hql = DaoUtil.getFindPrefix(tClass);
		return baseDao.find(hql);
	}

	/**
	 * 根据ids集合查询对象
	 * @param ids
	 * @return
	 */
	public List<T> findByIds(Collection<String> ids){
		String hql = DaoUtil.getFindPrefix(aClass) + " WHERE id in ( :ids ) ";
		Map<String ,Object> params = new HashMap<String ,Object>();
		params.put("ids",ids);
		return baseDao.find(hql,params);
	}

	/**
	 * 根据ids集合查询对象
	 * @param ids
	 * @return
	 */
	public List<T> findByIds(Class<T> t,Collection<String> ids){
		String hql = DaoUtil.getFindPrefix(t) + " WHERE id in ( :ids ) ";
		Map<String ,Object> params = new HashMap<String ,Object>();
		params.put("ids",ids);
		return baseDao.find(hql,params);
	}

	/**
	 * 根据ids字符串查询对象。以","分割  例如 idStr = "123,124,125";
	 * @param idStr
	 * @return
	 */
	public List<T> findByIdStr(String idStr){
		String[] split = idStr.split(",");
		List<String> ids = Arrays.asList(split);
		return findByIds(ids);
	}

	/**
	 * 根据ids字符串查询对象。以","分割  例如 idStr = "123,124,125";
	 * @param idStr
	 * @return
	 */
	public List<T> findByIdStr(Class<T> t,String idStr){
		String[] split = idStr.split(",");
		List<String> ids = Arrays.asList(split);
		return findByIds(t,ids);
	}

	/**
	 * 根据条件查询对象
	 * @param condition
	 * @return
	 */
	public List<T> findByCondition(String condition){
		String hql = DaoUtil.getFindPrefix(aClass);
		return baseDao.find(hql+condition);
	}

	/**
	 * 根据条件查询对象
	 * @param condition
	 * @param params
	 * @return
	 */
	public List<T> findByCondition(String condition,Map<String,Object> params){
		String hql = DaoUtil.getFindPrefix(aClass);
		return baseDao.find(hql+condition,params);
	}

	/**
	 * 根据条件查询指定对象
	 * @param tClass
	 * @param condition
	 * @param params
	 * @return
	 */
	public List<T> findByCondition(Class<T> tClass,String condition,Map<String,Object> params){
		String hql = DaoUtil.getFindPrefix(tClass) ;
		return baseDao.find(hql+condition,params);
	}

	/**
	 * 分页查询
	 * @param condition
	 * @param params
	 * @param rows
	 * @param page
	 * @return
	 */
	public List<T> findByPage(String condition,Map<String ,Object> params,int rows,int page){
		String hql = DaoUtil.getFindPrefix(aClass);
		return baseDao.find(hql+condition,params,page,rows);
	}

	/**
	 * 分页查询指定对象
	 * @param tClass
	 * @param condition
	 * @param params
	 * @param rows
	 * @param page
	 * @return
	 */
	public List<T> findByPage(Class<T> tClass,String condition,Map<String ,Object> params,int rows,int page){
		String hql = DaoUtil.getFindPrefix(tClass) ;
		return baseDao.find(hql+condition,params,page,rows);
	}

	/**
	 * 更新对象
	 * @param t
	 */
	public void updateEntity(T t){
		baseDao.saveOrUpdate(t);
	}

	/**
	 * 删除对象
	 * @param t
	 */
	public void deleteEntity(T t){
		baseDao.delete(t);
	}

	/**
	 * 批量删除集合对中象
	 * @param t
	 */
	public void deleteEntityByCollection(Collection<T> t){
		for (T t1 : t) {
			deleteEntity(t1);
		}
	}
	/**
	 * 根据id删除对象
	 * @param id
	 */
	public void deleteById(String id){
		baseDao.delete(aClass,id);
	}

	/**
	 * 根据id删除指定对象
	 * @param t
	 * @param id
	 */
	public void deleteById(Class<T> t ,String id){
		baseDao.delete(t,id);
	}

	/**
	 * 根据id集合删除对象
	 * @param ids
	 */
	public void deleteByIds(Collection<String> ids){
		for (String id : ids) {
			deleteById(id);
		}
	}

	/**
	 * 通过id集合删除
	 * @param t
	 * @param ids
	 */
	public void deleteByIds(Class<T> t,Collection<String> ids){
		for (String id : ids) {
			deleteById(t,id);
		}
	}

	/**
	 * 根据idStr删除对象 列如 idStr = "123,124,125";
	 * @param idStr
	 */
	public void deleteByIdStr(String idStr){
		String[] ids = idStr.split(",");
		List<String> idList = Arrays.asList(ids);
		deleteByIds(idList);
	}

	/**
	 * 根据idStr删除对象 列如 idStr = "123,124,125";
	 * @param idStr
	 */
	public void deleteByIdStr(Class<T> t,String idStr){
		String[] ids = idStr.split(",");
		List<String> idList = Arrays.asList(ids);
		deleteByIds(t,idList);
	}


	/**
	 * 保存对象
	 * @param t
	 */
	public void saveEntity(T t){
		baseDao.saveOrUpdate(t);
	}

	public String savePicture(File file, String fileName, String rootKey,String courierId) {
		String photoName = courierId + "_" + fileName;
		String picturePath = UploadFileUtils.getDirectory(rootKey);
		String filePath = picturePath + "/" + photoName;
		File oldFile = new File(filePath);
		if(oldFile.exists()) {
			oldFile.delete();
		}
		UploadFileUtils.imgCompress(file, filePath);
		return photoName;
	}

	/**
	 * 根据图片名称获取图片
	 *
	 * @param photoName  图片名称
	 * @param dictKey  词典路径key
	 * @return 找不到或照片名称为空 就返回系统中 'sorry' 的图片
	 *
	 */
	public File getPicture(String dictKey,String photoName) {
		if(StringUtils.isBlank(photoName)) {
			return ResourceUtils.getResourceFile("others/sorry.png");
		}
		File photoFile = UploadFileUtils.getFileByDirKey(dictKey, photoName);
		if(photoFile == null) {
			return ResourceUtils.getResourceFile("others/sorry.png");
		}
		return photoFile;
	}


}
