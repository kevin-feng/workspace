package org.tsc.core.tools;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 文件、图上上传工具类
 * 
 * @author fyxiang
 * 
 */
public class UploadUtils {
	
	/**
	 * 上传不超过 1M 的图片
	 * @param request
	 * @param inputName
	 * @param savePath
	 * @return
	 */
	public static Map<String, Object> upload1MImage(HttpServletRequest request, String inputName,
			String savePath) {
		// 1M = 1024 kb = 1024 b * 1024 b = 1048576 b
		return uploadImage(request, inputName, savePath, 1048576);
	}

	/**
	 * 上传不超过 5M 的图片
	 * @param request
	 * @param inputName
	 * @param savePath
	 * @return
	 */
	public static Map<String, Object> upload5MImage(HttpServletRequest request, String inputName,
			String savePath) {
		// 5M = 1024 kb * 5 = 1024 b * 1024 b * 5 = 5242880 b
		return uploadImage(request, inputName, savePath, 5242880);
	}
	
	/**
	 * 上传不超过 30M 的图片
	 * @param request
	 * @param inputName
	 * @param savePath
	 * @return
	 */
	public static Map<String, Object> upload30MFile(HttpServletRequest request, String inputName,
			String savePath) {
		// 5M = 1024 kb * 5 = 1024 b * 1024 b * 5 = 5242880 b
		return uploadImage(request, inputName, savePath, 31457280);
	}
	
	
	/**
	 * 图片上传
	 * 
	 * @param request
	 * @param inputName
	 *            上传input 的name 名
	 * @param savePath
	 *            保存的路径(不包含根路径，不包含文件名，文件名采用 uuid 规则产生）
	 * @param fileName
	 *            文件名
	 * @param extendes
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> uploadImage(HttpServletRequest request, String inputName,
			String savePath, long maxSize){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(inputName);
		System.out.println("------------ file = "+file.getOriginalFilename());
		Map<String, Object> map = new HashMap<String, Object>();
		if ((file != null) && (!file.isEmpty())) {
			long fileSize = file.getSize();
			if (fileSize > maxSize) {
				map.put("errors", "上传文件大小超过限制");
				return map;
			}
			String fileExt = file.getOriginalFilename()
					.substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
			// 检查文件格式
//			if (!isImageExt(fileExt)) {
//				map.put("errors", "上传的图片格式不正确");
//				return map;
//			}
			// uuid 产生文件名
			String fileName = UUID.randomUUID().toString() + "." + fileExt;
 
			//创建上传文件夹
			map.put("savePath",savePath + "/");
			savePath = request.getSession().getServletContext().getRealPath("/")+ savePath+"/"+fileName;
			File uploadDir = new File(savePath);
			//判断目标文件的文件路径是否存在
			if (!uploadDir.getParentFile().exists()) {
				uploadDir.getParentFile().mkdirs();
			}
			try {
				//创建目标文件
				uploadDir.createNewFile();
				//将上传的文件转换为目标文件
				file.transferTo(uploadDir);
				System.out.println("path = "+uploadDir.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			map.put("mime", fileExt);
			map.put("fileName",fileName);
			map.put("fileSize",fileSize);
			map.put("oldName", file.getOriginalFilename());
		}
//		System.out.println("uplaod success");
		return map;
	}

	/**
	 * 判断拓展名是否为图片格式（允许的图片格式有：gif,jpg,jpeg,png,bmp）
	 * 
	 * @param fileExt
	 *            文件的后缀名
	 * @return
	 */
	public static boolean isImageExt(String fileExt) {
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		if (!Arrays.asList(extMap.get("image").split(",")).contains(fileExt)) {
			return false;
		}
		return true;
	}
}
