<%@ page import="com.pms.entity.Admin" %>
<%@ page import="com.pms.entity.Guide" %><%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 22-Dec-18
  Time: 9:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    out.print((Guide) session.getAttribute("user"));
%>
</body>
</html>
