<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File Upload</title>
<style>
#fileUploadDiv {
    background-color:black;
    color:white;
    text-align:center;
    padding:5px;
}
#footer {
    position: fixed;
    bottom: 0;
    width: 100%;
}
</style>
</head>
<body>

	<div id="fileUploadDiv">
        <h1> Choose File to Upload in Server </h1>
        <form action="upload" method="post" enctype="multipart/form-data">
            <input type="file" name="file" />
            <input type="submit" value="upload" />
        </form>
    </div>
    
    <div id="footer">
		<p>By: Roger Wang Contact information: <a href="Roger.Wang@cns-inc.com"> 
  		Roger.Wang@cns-inc.com</a>.</p>
	</div>
</body>
</html>