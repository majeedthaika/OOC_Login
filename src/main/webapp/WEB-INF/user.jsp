<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body>
<%@ include file="logout.jsp" %>
<div>${userDetails}</div>
<a href="/modify?username=${user}" method="post" type = "button" class="btn btn-warning">Edit Info</a>
<%@ include file="home.jsp" %>
</body>
</html>
