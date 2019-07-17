package com.kxjl.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kxjl.base.pojo.CtrollerMapperBean;
import com.kxjl.tasktransferplat.pojo.Address;
import com.kxjl.tasktransferplat.pojo.Config;

public class TemplateFileUtil {
	private static Logger log = LoggerFactory.getLogger(TemplateFileUtil.class);

	/**
	 * 基础的保存文件
	 * 
	 * @param fileName
	 * @param path
	 * @param content
	 * @param bean
	 * @throws Exception
	 * @author zj
	 * @date 2019年1月7日
	 */
	public static void SaveFile(String fileName, String path, String content) throws Exception {
		try {
			String basePath = path;

			File fold = new File(basePath);
			if (!fold.exists()) {
				fold.mkdirs();
			}

			String filePath = basePath + File.separator + fileName;
			File ftarget = new File(filePath);//

			// "F:\\w3\\baseFrameWk\\base\\src\\main\\java\\com\\ztgm\\demo\\controller\\WebController\\KtestController.java");
			FileWriter fw = new FileWriter(ftarget);
			fw.write(content);

			fw.flush();
			fw.close();
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}

	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 * @param path
	 * @param content
	 * @param bean
	 * @throws Exception
	 * @author zj
	 * @date 2019年1月7日
	 */
	public static boolean DelFile(String fileName, String path) throws Exception {

		boolean isok = false;
		try {
			String basePath = path;
			String filePath = basePath + File.separator + fileName;
			File ftarget = new File(filePath);//
			if (ftarget.exists()) {
				isok = ftarget.delete();
			}

		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return isok;

	}

	/**
	 * 备份制定文件 至resouces/temp
	 * 
	 * @param fileName
	 * @param path
	 * @param content
	 * @param bean
	 * @throws Exception
	 * @author zj
	 * @date 2019年1月7日
	 */
	public static void BackUpFile(String fileName, String path) throws Exception {

		BufferedReader reader = null;
		try {
			String basePath = path;
			String filePath = basePath + File.separator + fileName;
			File ftarget = new File(filePath);//

			if (ftarget.exists()) {
				FileReader fr = new FileReader(ftarget);
				reader = new BufferedReader(fr);
				StringBuffer sbf = new StringBuffer();

				// 读取
				String tempString = "";
				boolean isadd = false;
				while ((tempString = reader.readLine()) != null) {
					sbf.append(tempString + "\r\n");
				}
				String oldContext = sbf.toString();

				// 备份
				String tpath = TemplateFileUtil.class.getResource("/").getPath();
				tpath += "../../src/main/resources/temp/" + fileName;
				File descfile = new File(tpath);//
				FileWriter fw = new FileWriter(descfile);
				fw.write(oldContext);
				fw.flush();
				fw.close();
			}
		} catch (Exception e) {
			log.error("", e);
			throw e;
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
		}

	}

	/**
	 * 附加内容至制定文件结束符号之前
	 * 
	 * @param fileName
	 * @param path
	 * @param content
	 * @param endFlagStr
	 *            结束标记： } 或者 </mapper> 附加到制定字符串之前
	 * @throws Exception
	 * @author zj
	 * @date 2019年1月7日
	 */
	public static void InsertToSaveFile(String fileName, String path, String content, String endFlagStr)
			throws Exception {
		BufferedReader reader = null;
		try {
			String basePath = path;
			String filePath = basePath + File.separator + fileName;
			File ftarget = new File(filePath);//

			FileReader fr = new FileReader(ftarget);
			reader = new BufferedReader(fr);

			StringBuffer sbf = new StringBuffer();

			String tempString = "";
			boolean isadd = false;
			while ((tempString = reader.readLine()) != null) {
				sbf.append(tempString + "\r\n");

				if (endFlagStr.equals("}") && fileName.endsWith("Mapper.java")) // java
				{
					if (!isadd) {
						// mapper中补充 import java.util.List;
						sbf.append("import java.util.List;" + "\r\n");

						isadd = true;
					}
				}

			}

			String oldContext = sbf.toString();
			oldContext = oldContext.replace(endFlagStr, ""); // 替换结束

			// 原文+新的plus+结束
			String newContext = oldContext + content + "\r\n" + endFlagStr;

			FileWriter fw = new FileWriter(ftarget);
			fw.write(newContext);

			fw.flush();
			fw.close();
		} catch (Exception e) {
			log.error("", e);
			throw e;
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
		}

	}

	/**
	 * 扩展dao <code>  extends BaseMapper&lt;Address>   </code>
	 * 
	 * @param fileName
	 * @param path
	 *            * @param
	 * 
	 *            <pre>
	 *  content extends BaseMapper&lt;Address>
	 *            </pre>
	 * 
	 * @throws Exception
	 * @author zj
	 * @date 2019年1月29日
	 */
	public static void extendMybatisPlus(String fileName, String path, String content) throws Exception {
		BufferedReader reader = null;
		try {
			String basePath = path;
			String filePath = basePath + File.separator + fileName;
			File ftarget = new File(filePath);//

			FileReader fr = new FileReader(ftarget);
			reader = new BufferedReader(fr);

			StringBuffer sbf = new StringBuffer();

			String tempString = "";
			boolean isadd = false;
			while ((tempString = reader.readLine()) != null) {
				sbf.append(tempString + "\r\n");

				if (!isadd) {
					// 兼容mybatis-plus
					sbf.append("import com.baomidou.mybatisplus.core.metadata.IPage;" + "\r\n");
					sbf.append("import com.baomidou.mybatisplus.core.mapper.BaseMapper;" + "\r\n");
					sbf.append("import com.baomidou.mybatisplus.extension.plugins.pagination.Page;" + "\r\n");

					isadd = true;
				}

			}

			String oldContext = sbf.toString();
			oldContext = oldContext.replace("{", " " + content + "   {"); // 替换结束

			// 原文+新的plus+结束
			String newContext = oldContext;

			FileWriter fw = new FileWriter(ftarget);
			fw.write(newContext);

			fw.flush();
			fw.close();
		} catch (Exception e) {
			log.error("", e);
			throw e;
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
		}

	}

	/**
	 * 修改pojo主键int->long类型
	 * 
	 * @param fileName
	 * @param path
	 * @param content
	 * @throws Exception
	 * @author zj
	 * @date 2019年1月29日
	 */
	public static void ModifyPriKeyToLong(CtrollerMapperBean bean) throws Exception {

		if(bean.getIdType().equals("2")) //varchar的不修改  ，1int型的继续修改
			return ;
		
		String fileName = bean.getModelClass() + ".java";

		String path = TemplateFileUtil.class.getResource("/").getPath();
		path += "../../src/main/java/" + bean.getBasepackageName().replace(".", "/") + File.separator + "pojo";

		BufferedReader reader = null;
		try {
			String basePath = path;
			String filePath = basePath + File.separator + fileName;
			File ftarget = new File(filePath);//

			FileReader fr = new FileReader(ftarget);
			reader = new BufferedReader(fr);

			StringBuffer sbf = new StringBuffer();

			String tempString = "";
			boolean isadd = false;
			while ((tempString = reader.readLine()) != null) {
				sbf.append(tempString + "\r\n");
			}

			String oldContext = sbf.toString();
			oldContext = oldContext.replace("private Integer id;", "private Long id;"); // 替换结束
			oldContext = oldContext.replace("public void setId(Integer id)", "public void setId(Long id)"); // 替换结束
			oldContext = oldContext.replace("public Integer getId()", "public Long getId()"); // 替换结束

			// 原文+新的plus+结束
			String newContext = oldContext;

			FileWriter fw = new FileWriter(ftarget);
			fw.write(newContext);

			fw.flush();
			fw.close();
		} catch (Exception e) {
			log.error("", e);
			throw e;
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
		}

	}

	/**
	 * 保存service 文件
	 * 
	 * @param content
	 * @param bean
	 * @throws Exception
	 * @author zj
	 * @date 2019年1月7日
	 */
	public static void AddToDaoFile(String content, CtrollerMapperBean bean) throws Exception {
		try {

			String fileName = bean.getModelClass() + "Mapper.java";

			String path = TemplateFileUtil.class.getResource("/").getPath();
			path += "../../src/main/java/" + bean.getBasepackageName().replace(".", "/") + File.separator + "dao";

			if (!bean.getDaosubfold().equals(""))
				path += "/" + bean.getDaosubfold();

			// System.out.println(path);
			InsertToSaveFile(fileName, path, content, "}");

			String autoMapper = "  extends BaseMapper<" + bean.getModelClass() + "> ";
			// 兼容-mybatis-plus
			extendMybatisPlus(fileName, path, autoMapper);

		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
	}

	/**
	 * 保存service 文件
	 * 
	 * @param content
	 * @param bean
	 * @throws Exception
	 * @author zj
	 * @date 2019年1月7日
	 */
	public static void AddToMapperFile(String content, CtrollerMapperBean bean) throws Exception {
		try {

			String fileName = bean.getModelClass() + "Mapper.xml";

			String path = TemplateFileUtil.class.getResource("/").getPath();
			path += "../../src/main/resources/mappers/";
			// System.out.println(path);
			InsertToSaveFile(fileName, path, content, "</mapper>");

		} catch (Exception e) {
			log.error("", e);
			throw e;
		}

	}

	/**
	 * 保存controller文件
	 * 
	 * @param content
	 * @param bean
	 * @throws Exception
	 * @author zj
	 * @date 2019年1月7日
	 */
	public static void SaveControllerFile(String content, CtrollerMapperBean bean) throws Exception {
		try {

			String fileName = bean.getModelClass() + "Controller.java";

			String path = TemplateFileUtil.class.getResource("/").getPath();
			path += "../../src/main/java/" + bean.getBasepackageName().replace(".", "/") + File.separator + "controller"
					+ File.separator + "WebController";
			// System.out.println(path);
			SaveFile(fileName, path, content);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}

	}

	/**
	 * 保存service 文件
	 * 
	 * @param content
	 * @param bean
	 * @throws Exception
	 * @author zj
	 * @date 2019年1月7日
	 */
	public static void SaveServiceFile(String content, CtrollerMapperBean bean) throws Exception {
		try {

			String fileName = bean.getModelClass() + "Service.java";

			String path = TemplateFileUtil.class.getResource("/").getPath();
			path += "../../src/main/java/" + bean.getBasepackageName().replace(".", "/") + File.separator + "service";
			// System.out.println(path);
			SaveFile(fileName, path, content);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}

	}

	/**
	 * 保存serviceimpl 文件
	 * 
	 * @param content
	 * @param bean
	 * @throws Exception
	 * @author zj
	 * @date 2019年1月7日
	 */
	public static void SaveServiceImplFile(String content, CtrollerMapperBean bean) throws Exception {
		try {

			String fileName = bean.getModelClass() + "ServiceImpl.java";
			String path = TemplateFileUtil.class.getResource("/").getPath();
			path += "../../src/main/java/" + bean.getBasepackageName().replace(".", "/") + File.separator + "service"
					+ File.separator + "impl";
			SaveFile(fileName, path, content);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}

	}

	/**
	 * 保存查询页面 文件
	 * 
	 * @param content
	 * @param bean
	 * @throws Exception
	 * @author zj
	 * @date 2019年1月7日
	 */
	public static void SaveIndexFile(String content, CtrollerMapperBean bean, String type) throws Exception {
		try {
			String fileName = "index.ftl";
			if (type.equals("jsp"))
				fileName = "index.jsp";

			String path = TemplateFileUtil.class.getResource("/").getPath();
			path += "../../src/main/resources/templates/backend/page/" + bean.getCtrollerModelMapping();

			SaveFile(fileName, path, content);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}

	}

	/**
	 * 保存表单页面 文件
	 * 
	 * @param content
	 * @param bean
	 * @throws Exception
	 * @author zj
	 * @date 2019年1月7日
	 */
	public static void SavePageFile(String content, CtrollerMapperBean bean, String type) throws Exception {
		try {
			String fileName = "form.ftl";
			if (type.equals("jsp"))
				fileName = "form.jsp";

			String path = TemplateFileUtil.class.getResource("/").getPath();
			path += "../../src/main/resources/templates/backend/page/" + bean.getCtrollerModelMapping();

			SaveFile(fileName, path, content);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}

	}

	/**
	 * 保存js页面 文件
	 * 
	 * @param content
	 * @param bean
	 * @throws Exception
	 * @author zj
	 * @date 2019年1月7日
	 */
	public static void SaveJSFile(String content, CtrollerMapperBean bean) throws Exception {
		try {
			String fileName = bean.getCtrollerModelMapping() + ".js";

			String path = TemplateFileUtil.class.getResource("/").getPath();
			path += "../../src/main/resources/static/vendor/pageAuto/" + bean.getCtrollerModelMapping() + "/js";

			SaveFile(fileName, path, content);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}

	}
}
