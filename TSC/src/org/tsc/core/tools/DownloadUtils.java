package org.tsc.core.tools;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadUtils {
	
	public static void downloadFile(HttpServletRequest request,HttpServletResponse response,
			String path,String fileName) {
          
        //设置文件MIME类型  
        response.setContentType(request.getServletContext().getMimeType(fileName));  
        //设置Content-Disposition  
        response.setHeader("Content-Disposition", "attachment;filename="+fileName);  
        //读取目标文件，通过response将目标文件写到客户端  
        //获取目标文件的绝对路径  
        String fullFileName = request.getServletContext().getRealPath("/")+path+fileName;  
        //System.out.println(fullFileName);  
        try {
        	//读取文件  
        	InputStream in = new FileInputStream(fullFileName);  
        	OutputStream out = response.getOutputStream();  
        	
        	//写文件  
        	int b;  
        	while((b=in.read())!= -1)  
        	{  
        		out.write(b);  
        	}  
        	in.close();  
        	out.close();  
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
          
	}
}
