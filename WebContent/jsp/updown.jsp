<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String basePath=request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Servlet+Jsp上传下载</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>/css/common.css"/>
<script type="text/javascript" src="<%=basePath%>/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$(".thumbs img").click(function(){
			var src=$(this).attr("src").replace("thumb","lg");
			$("#largeImg").attr({
				"alt":$(this).attr("title"),
				"title":$(this).attr("title"),
				"src":src
			});
			var fileName=src.substring(src.lastIndexOf("/")+1);
			$("#download").attr("href","<%=basePath%>/download?filename="+fileName);
		});
		$("#file").change(function(){
			$("#previewImg").attr("src",$(this).val());
		});
		var large=$("#large");
		$("#previewImg").mousemove(function(e){
			var originImg=large.children("img");
			large.css({
				"top":e.pageY,
				"left":e.pageX
			});
			originImg.attr("src",$(this).attr("src"));
			large.show();
		}).mouseout(function(){
			large.hide();
		});
	});
</script>
</head>
<body>
<div id="preview" style="margin-bottom:20px">
	<img id="previewImg" src="<%=basePath%>/images/preview.jpg" width="80px" height="80px">
	<div id="large">
		<img/>
	</div>
</div>
<form action="<%=basePath%>/upload" method="post" enctype="multipart/form-data">
	上传：<input id="file" name="file" type="file">
	<input type="submit" value="提交"/>
	<p>${uploadResult}</p>
</form>
<hr/>
<h2>图片预览</h2>
<p><a id="download" href="<%=basePath%>/download?filename=img1-lg.jpg"><img id="largeImg" alt="LargeImage" title="largeImage" src="<%=basePath%>/images/img1-lg.jpg"></a></p>
<p class="thumbs">
	<img title="image2" src="<%=basePath %>/images/img2-thumb.jpg">
	<img title="image3" src="<%=basePath %>/images/img3-thumb.jpg">
	<img title="image4" src="<%=basePath %>/images/img4-thumb.jpg">
	<img title="image5" src="<%=basePath %>/images/img5-thumb.jpg">
	<img title="image6" src="<%=basePath %>/images/img6-thumb.jpg">
</p>
</body>
</html>