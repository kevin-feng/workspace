package org.tsc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.tsc.core.bean.IdEntity;


/**
 *   系统附件管理类，用来管理系统所有附件信息，包括图片附件、rar附件等等
 * @since V1.0
 * 
 */
public class Accessory extends IdEntity implements Serializable{
	private static final long serialVersionUID = 3468198942173749416L;
	private String name;// 附件名称
	private String oldName;//附件原有名称
	private String path;// 存放路径
	private float size;// 附件大小
	private int width;// 宽度
	private int height;// 高度
	private String ext;// 扩展名，不包括.
	private String type;// 附件类型  默认为0，1为网站主图
	private int count;//附件的下载次数  默认为0



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
   
}
