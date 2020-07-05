<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<title>Update Password</title>
</head>
<body>
<center>
<form action="${pageContext.request.contextPath}/updatepassword" method="post">
<h1 class="heading">Change Password</h1>
Enter old password:<input type="text" placeholder="old password" value="${oldpassword}" name="oldpassword"/><br/>
Enter new password:<input type="text" placeholder="new password" value="${newpassword1}" name="newfpassword"/><br/>
Re-enter new password:<input type="text" placeholder="re-enter password" value="${newpassword2}" name="newspassword"/><br/>
<input type="submit" value="Update"/>
</form>
${msg}
</center>
</body>
</html>