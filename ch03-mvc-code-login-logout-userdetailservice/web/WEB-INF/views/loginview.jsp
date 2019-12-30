<%--
  User: cj
  Date: 2019/12/26
  Time: 20:15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>自己的登录页面</title>
</head>
<body>
<h1>自己的登录页面</h1>

<form action="/dologin" method="post">
    <input type="text" name="uname"/>
    <input type="password" name="pwd"/>
    <input type="submit" value="登录"/>
</form>
</body>
</html>
