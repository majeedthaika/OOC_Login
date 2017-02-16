<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body>
<a href="/logout" method="post" class="btn btn-info btn-lg">
    <span class="glyphicon glyphicon-log-out"></span> Logout
</a>
<div>${userDetails}</div>
<a href="/modify?username=${user}" method="post" type = "button" class="btn btn-warning">Edit Info</a>
</body>
</html>
