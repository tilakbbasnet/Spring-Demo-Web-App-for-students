<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<title>Users</title></head>
<body/>
<h1>User List</h1>
<table style="border: 1px solid black;">

<tr>
<th style="border: 1px solid black;">ID</th>
<th style="border: 1px solid black;">Full Name</th>
<th style="border: 1px solid black;">Gender</th>
<th style="border: 1px solid black;">DOB</th>
<th style="border: 1px solid black;">Email</th>
<th style="border: 1px solid black;">Actions</th>
</tr>

<c:forEach items="${userList}" var="user">
<tr>
<td style="border: 1px solid black;">${user.id}</td>
<td style="border: 1px solid black;">${user.name}</td>
<td style="border: 1px solid black;">${user.gender}</td>
<td style="border: 1px solid black;">${user.dob}</td>
<td style="border: 1px solid black;">${user.email}</td>
<td style="border: 1px solid black;"><a href="${pageContext.request.contextPath}/edit/${user.id}">Edit</a> <a href="${pageContext.request.contextPath}/delete/${user.id}">Delete</a></td>
</tr>
</c:forEach>

</table>
<div>${param.msg}</div>
</body>
</html>