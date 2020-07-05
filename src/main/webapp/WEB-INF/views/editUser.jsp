<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<title>User Update</title>
</head>
<body>
<center>
<form action="${pageContext.request.contextPath}/updateUser" method="post">
<h1 class="heading">User Update</h1>
ID:<input type="text" name="id" value="${user.id}" readonly/></br>
Full Name:<input type="text" placeholder="Full Name" name="name" value="${user.name}" required /><br/>
DOB:<input type="date" placeholder="DOB" name="dob" required value="${user.dob}"></input><br/>
Gender:

<input type="radio" name="gender" id="male" value="Male"
<c:if test="${user.gender =='Male'}"> checked </c:if> />Male

<input type="radio" name="gender" id="female" value="Female"
<c:if test="${user.gender =='Female'}"> checked </c:if> />Female<br/>

Email:<input type="text" placeholder="Email" name="email" required value="${user.email}"></input><br/>
<input type="submit" value="Update"/>
</form>
${errmsg}
</center>
</body>
</html>