package org.shen.daily;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadServlet extends HttpServlet{
	private static final long serialVersionUID = -8334738589079384300L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ServletInputStream sis=req.getInputStream();
		
		File tempFile=new File("E:/tempFile");
		FileOutputStream fos=new FileOutputStream(tempFile);
		
		byte[] body=new byte[1024];
		int n;
		
		while((n=sis.read(body))!=-1) {
			fos.write(body, 0, n);
		}
		fos.close();
		
		RandomAccessFile ras=new RandomAccessFile(tempFile, "r");
		
		ras.readLine();
		String fileNameLine=ras.readLine();
		
		int beginIndex=fileNameLine.lastIndexOf("\\")+1;
		int endIndex=fileNameLine.lastIndexOf("\"");
		
		String fileName=fileNameLine.substring(beginIndex, endIndex);
		fileName=new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
		
		long beginPosition=0;
		long endPosition=0;
		
		ras.seek(0);
		
		int i=1;
		while((n=ras.read())!=-1&&i<=4) {
			if(n=='\n') {
				beginPosition=ras.getFilePointer();
				i++;
			}
		}
		
		ras.seek(ras.length());
		
		int j=1;
		endPosition=ras.length();
		
		while(endPosition>=0&&j<=2) {
			endPosition--;
			ras.seek(endPosition);
			if(ras.read()=='\n') {
				j++;
			}
		}
		
		File uploadFile=new File(req.getServletContext().getRealPath("images/"+fileName));
		
		RandomAccessFile rasForUploadFile=new RandomAccessFile(uploadFile, "rw");
		
		ras.seek(beginPosition);
		
		while(ras.getFilePointer()<endPosition) {
			rasForUploadFile.write(ras.read());
		}
		
		ras.close();
		rasForUploadFile.close();
		
		req.setAttribute("uploadResult", "上传成功");
		req.getRequestDispatcher("jsp/updown.jsp").forward(req, resp);;
	}
	
}
