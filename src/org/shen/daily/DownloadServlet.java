package org.shen.daily;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadServlet extends HttpServlet{

	private static final long serialVersionUID = 7786692730685023568L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fileName=req.getParameter("filename");
		
		File downloadFile=new File(req.getServletContext().getRealPath("/images/"+fileName));
		
		if(!downloadFile.exists()) {
			req.setAttribute("downloadResult", "下载的文件不存在");
			req.getRequestDispatcher("jsp/updown.jsp").forward(req, resp);
		}
		
		resp.setHeader("Content-Type", "application/octet-stream");
		resp.setHeader("Content-Disposition", "attachement;filename="+fileName);
		
		FileInputStream fis=new FileInputStream(downloadFile);
		
		OutputStream os=resp.getOutputStream();
		
		byte[] body=new byte[1024];
		int n;
		
		while((n=fis.read(body))!=-1) {
			os.write(body, 0, n);
		}
		
		fis.close();
		os.close();
		
	}
	
	
}
