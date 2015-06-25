<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
#OptionDiv {
    line-height:30px;
    text-align:left;
    background-color:#eeeeee;
    height:340px;
    width:220px;
    float:left;	    
    padding-left:10px;  
}
#ResultTableDiv {
    width:700px;
    height:340px;
    float:left; 
}
table, th, td {
    border: 1px solid black;
}
td {
	padding-left: 5px;
	padding-right: 5px;
}
table {
height: 340px;
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
    
	<div id="result">
           <h3>Current File: ${filename} </h3> 
    </div>
    <script>
	  function setDir(){
	    var el = document.getElementById("Sorted");
	    if(el.checked)
	    {
    		document.getElementById("ASC").disabled = false;
    		document.getElementById("ASC").checked = true;
    		document.getElementById("DESC").disabled = false;
    		document.getElementById("sortingindex").disabled = false;
	    }
	    else
	    {
    		document.getElementById("ASC").disabled = true;  
    		document.getElementById("DESC").disabled = true;
    		document.getElementById("ASC").checked = false;
    		document.getElementById("DESC").checked = false;
    		document.getElementById("sortingindex").disabled = true;
	    }
	  }

	  function enableSorting()
	  {
		var el1 = document.getElementById("FileOutput");
		var el2 = document.getElementById("ToPageOutput");
		if(el1.checked || el2.checked)
		{
			document.getElementById("Sorted").disabled = false;
		}
		else
			document.getElementById("Sorted").disabled = true;
	  }
	</script>
	
    <div id="OptionDiv">
    	<h2> Options </h2>
    	<form action="subOptions" method="post">
    	<input type="hidden" name="action" value="inputOptions">
   		<input type="hidden" name="filename" value="${filename}">
    	<div style="float: left; width: 100px; height: 220px;background-color:#eeeeee;padding=10px 10px 10px 10px;text-align=left;">
    		Input Options<br>
    		<input type="radio" name="inputoptions" value="File" checked>File<br>
    		<input type="radio" name="inputoptions" value="Db" disabled=disabled>Db<br>
    		Output Options<br>
    		<input type="checkbox" name="outputoptions" id="FileOutput" onchange="enableSorting();" value="File">File<br>
    		<input type="checkbox" name="outputoptions" id="DbOutput" onchange="enableSorting();" value="Db">Db<br>
    		<input type="checkbox" name="outputoptions" id="ToPageOutput" onchange="enableSorting();" value="ToPage">ToPage<br>
    	</div>
    	<div style="float: left; width: 100px; height: 220px;background-color:#eeeeee;text-align=right;">
    		Sorting Options<br>
    		<input type="checkbox" name="sortingoptions" id="Sorted" onchange="setDir();" value="Sorted" disabled=disabled>Sorted<br>
    		<input type="radio" name="sortingoptions" id="ASC" value="ASC" disabled=disabled>ASC<br>
    		<input type="radio" name="sortingoptions" id="DESC" value="DESC" disabled=disabled>DESC<br>
    		Sort Index<br>
    		<select name="sortingindex" id="sortingindex" disabled=disabled>
	    		<option value="uid">uid</option>
				<option value="firstname">firstname</option>
				<option value="lastname">lastname</option>
				<option value="zip">zip</option>
				<option value="state">state</option>
    		</select>
    	</div>
    	<input type="submit" name="submit" id="submitbutton" value="Submit Options">
    	</form>
    </div>
    
    <div id="ResultTableDiv">
    <table>
    <tr>
    	<th>Uid</th>
    	<th>First Name</th>
    	<th>Last Name</th>
    	<th>Street1</th>
    	<th>Street2</th>
    	<th>City</th>
    	<th>State</th>
    	<th>ZIP</th>
    	<th>Created Date</th>
    	<th>Modified Date</th>
  	</tr>
    <c:forEach var="element" items="${table}">
        <tr>
            <td>${element.ADDRS_INFO_SID}</td> 
            <td>${element.USR_FIRST_NAME}</td> 
            <td>${element.USR_LAST_NAME}</td>
            <td>${element.STREET1}</td>
            <td>${element.STREET2}</td>
            <td>${element.CITY}</td>
            <td>${element.STATE_CODE}</td>
            <td>${element.ZIP_CODE}</td>
            <td>${element.CREATED_DATE}</td>
            <td>${element.MODIFIED_DATE}</td>
        </tr> 
    </c:forEach>
	</table>
    </div>
    
    <div id="footer">
		<p>By: Roger Wang. Contact information: <a href="Roger.Wang@cns-inc.com"> 
  		Roger.Wang@cns-inc.com</a>.</p>
	</div>
</body>
</html>