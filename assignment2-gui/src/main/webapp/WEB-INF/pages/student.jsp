<?xml version="1.0" encoding="UTF-8" ?>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="../../assets/ico/favicon.png">
<title>Students</title>

<!-- Bootstrap core CSS -->
<link href="${pageContext.request.contextPath}/css/bootstrap.css"
	rel="stylesheet">

	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
      <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
      <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <![endif]-->
</head>
<body>

	<!-- Static navbar -->
	<div class="navbar navbar-default navbar-static-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath}/">Student
					system</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a
						href="${pageContext.request.contextPath}/student">Students</a></li>
					<li><a href="${pageContext.request.contextPath}/course">Courses</a></li>
					<li><a href="${pageContext.request.contextPath}/degree">Degrees</a></li>
					<li><a href="${pageContext.request.contextPath}/">Location</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="active"><a
						href="${pageContext.request.contextPath}/init">Init database</a></li>
				</ul>


			</div>
			<!--/.nav-collapse -->
		</div>
	</div>


	<div class="container">

		<h1>Students</h1>
		<p class="lead text-danger">${message}</p>

		<table class="table">
			<tr>
				<th>Student</th>
				<th>Courses</th>
				<th>Degree</th>
				<th>Enroll course</th>
				<th>Enroll degree</th>
				<th>Delete student</th>
			</tr>

			<c:forEach items="${students}" var="s">
				<tr>
					<td>${s.name}</td>
					<td><c:forEach items="${s.courses}" var="sc">
                            ${sc.courseCode} 
                            <a
								href="${pageContext.request.contextPath}/student/${s.id}/unenrollcourse/${sc.id}">
								<img
								src="${pageContext.request.contextPath}/images/Button-Delete-icon.png">
							</a>
						</c:forEach></td>
					<td><c:forEach items="${s.degrees}" var="sd">
                            ${sd.type} 
                        </c:forEach></td>
					<td>
						<form name="enroll"
							action="${pageContext.request.contextPath}/student/${s.id}/enrollcourse"
							method="post">
							<select class="form-control-static" name="courseid">
								<c:forEach items="${courses}" var="c">
									<option value="${c.id}">${c.courseCode}</option>
								</c:forEach>
							</select> <input type="submit" class="btn btn-default"
								value="Enroll course">
						</form>
					</td>
					<td>
						<form name="enroll"
							action="${pageContext.request.contextPath}/student/${s.id}/enrolldegree"
							method="post">
							<select class="form-control-static" name="degreeid">
								<c:forEach items="${degrees}" var="d">
									<option value="${d.id}">${d.type}</option>
								</c:forEach>
							</select> <input class="btn btn-default" type="submit"
								value="Enroll degree">
						</form>
					</td>
					<td><a
						href="${pageContext.request.contextPath}/student/${s.id}/delete">Delete</a></td>
				</tr>

			</c:forEach>
		</table>

	</div>

	<div class="container">

		<h2>Create student:</h2>
		<form name="input"
			action="${pageContext.request.contextPath}/student/new" method="post">
			<table class="table">
				<tr>
					<td>Name:</td>
					<td><input class="form-control" type="text" name="name"
						placeholder="Enter student name" autofocus /></td>
				</tr>
				<tr>
					<td></td>
					<td><input class="btn btn-primary" type="submit" value="Save"></td>
				</tr>
			</table>
		</form>
	</div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    
    <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
