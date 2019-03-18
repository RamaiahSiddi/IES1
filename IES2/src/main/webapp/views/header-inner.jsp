<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
.dropbtn {
	background-color: #4CAF50;
	color: white;
	padding: 16px;
	font-size: 16px;
	border: none;
}

.dropdown {
	position: relative;
	display: inline-block;
}

.dropdown-content {
	display: none;
	position: absolute;
	background-color: #f1f1f1;
	min-width: 160px;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
}

.dropdown-content a {
	color: black;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
}

.dropdown-content a:hover {
	background-color: #ddd;
}

.dropdown:hover .dropdown-content {
	display: block;
}

.dropdown:hover .dropbtn {
	background-color: #3e8e41;
}
</style>
</head>
<body>

	<div class="dropdown">
		<button class="dropbtn">Application Registration</button>
		<div class="dropdown-content">
			<a href="#">Create Application</a> <a href="#">View Applications</a>
		</div>
	</div>
	<div class="dropdown">
		<button class="dropbtn">Data Collection</button>
		<div class="dropdown-content">
			<a href="#">Create Case</a> <a href="#">View Cases</a>
		</div>
	</div>
	<div class="dropdown">
		<button class="dropbtn">Admin</button>
		<div class="dropdown-content">
			<a href="accReg">Create Account</a> <a href="viewAccounts">View
				Accounts</a> <a href="planReg">Create Plan</a> <a href="viewPlans">View Plans</a>
		</div>
	</div>

</body>
</html>
