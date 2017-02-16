<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">
    <title>Signin Template for Bootstrap</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body>

<br>

</body>
<body>
<%@ include file="logout.jsp" %>
<div class="container">
    <div class="row">
        <h2>Add New User</h2>

        <form class="form-horizontal" action="/new" method="post">
            <fieldset>

                <legend>Enter Info Here</legend>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="user">Username</label>
                    <div class="col-md-4">
                        <input id="user" name="user" placeholder="Insert your Username" class="form-control input-md" type="text" required value=${user} >
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="pass">Password</label>
                    <div class="col-md-4">
                        <input id="pass" name="pass" placeholder="Insert your Password" class="form-control input-md" type="password" required value=${pass} >
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="email">Email</label>
                    <div class="col-md-4">
                        <input id="email" name="email" placeholder="Insert your Email" class="form-control input-md" type="email" value=${email}>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="first_name">First Name</label>
                    <div class="col-md-4">
                        <input id="first_name" name="first_name" placeholder="Insert your First Name" class="form-control input-md" type="text" value=${first_name}>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="last_name">Last Name</label>
                    <div class="col-md-4">
                        <input id="last_name" name="last_name" placeholder="Insert your Last Name" class="form-control input-md" type="text" value=${last_name}>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="singlebutton"> </label>
                    <div class="col-md-4">
                        <button id="singlebutton" name="singlebutton" class="btn btn-primary">Submit</button>
                    </div>
                </div>

            </fieldset>
        </form>

    </div>
</div>
<%@ include file="home.jsp" %>
</body>
</html>
