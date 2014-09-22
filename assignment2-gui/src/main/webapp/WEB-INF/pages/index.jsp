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
<title>Location</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.css" rel="stylesheet">

	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
      <script src="/assignment2-gui/js/html5shiv.js"></script>
      <script src="/assignment2-gui/js/respond.min.js"></script>
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
				<a class="navbar-brand" href="/assignment2-gui/">Student system</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="student">Students</a></li>
					<li><a href="course">Courses</a></li>
					<li><a href="degree">Degrees</a></li>
					<li class="active"><a href="${pageContext.request.contextPath}/">Location</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="active"><a href="init">Init
							database</a></li>
				</ul>


			</div>
			<!--/.nav-collapse -->
		</div>
	</div>


	<div class="container">

		<h1>Students</h1>
		<p class="lead text-danger"></p>

		<table class="table" id="studentTable">
			<tr>
				<th>Student</th>
				<th>Courses</th>
				<th>Location</th>
			</tr>
		</table>

	</div>

	<div class="container">

		<form id="studentlocationform" name="studentlocationform">
			<table class="table" id="studentLocationTable">
			</table>
			<table class="table">
				<tr>
					<td><input id="locationbtn" class="btn btn-primary"
						type="button" value="Set location"></td>
				</tr>
			</table>
		</form>

	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<script src="http://modernizr.com/downloads/modernizr-latest.js"></script>
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/assignment.js"></script>

	<script>
	$(document).ready(function() {
		getStudentData();
	});
	</script>

</body>
</html>
