<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<title>User Registration</title>
</head>
<body>
<center>
<form action="${pageContext.request.contextPath}/registration" method="post">
<h1 class="heading">User Registration</h1>
Full Name:<input type="text" placeholder="Full Name" name="name" value="${name}" required /><br/>
DOB:<input type="date" placeholder="DOB" name="dob" required value=${dob}></input><br/>
Gender:

<input type="radio" name="gender" id="male" value="Male"
<c:if test="${gender =='Male'}"> checked </c:if> />Male

<input type="radio" name="gender" id="female" value="Female"
<c:if test="${gender =='Female'}"> checked </c:if> />Female<br/>


Email:<input type="text" placeholder="Email" name="email" required value=${email}></input><br/>
Password:<input type="password" placeholder="Password" name="password" required value=${password}></input><br/>
Re-Enter Password:<input type="password" placeholder="Re-type password" name="password1" required value=${password1}></input><br/><br/><br/>

Address : <select name="address">
<option value="aa">aa</option>
<option value="bb">bb</option>
<option value="cc">cc</option>
</select>

<input type="submit" value="Register"/>
</form>
${errmsg}
</center>
</body>
</html>