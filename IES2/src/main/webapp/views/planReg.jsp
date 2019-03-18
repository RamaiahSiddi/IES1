<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Insert title here</title>
<style>
.error {
	color: #FF0000
}
</style>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script>
	$(function() {

		$('form[id="accRegFormm"]').validate({
			rules : {
				planName : 'required',
				plandes : 'required',
				startdate : 'required',
				enddate : 'required',
			
			},
			messages : {
				planName : 'Please enter  name',
				plandes : 'please enter des ',
				startdate : 'Please  select  start date',
				enddate  : 'Please slect end date',
			},
			submitHandler : function(form) {
				form.submit();
			}
		});
		 $("#datepicker").datepicker({
			 numberOfMonths: 1,
		        changeMonth: true,
		        changeYear: true,
		        onSelect: function (selected) {
		            var dt = new Date(selected);
		            dt.setDate(dt.getDate() + 1);
		            $("#datepicker2").datepicker("option", "minDate", dt);
		        }
		    });
		    $("#datepicker2").datepicker({
		        numberOfMonths: 1,
		        changeMonth: true,
		        changeYear: true,
		        onSelect: function (selected) {
		            var dt = new Date(selected);
		            dt.setDate(dt.getDate() - 1);
		            $("#datepicker").datepicker("option", "maxDate", dt);
		        }
		    });
	});
</script>
</head>
<%@ include file="header-inner.jsp" %>
<body>

	<font color='green'>${success}</font>
	<font color='red'>${failure}</font>

	<h2>Case Worker Registration</h2>
	<form:form action="planReg" method="POST" modelAttribute="planModel"
		id="accRegFormm">
		<table>
			<tr>
				<td>Plan Name</td>
				<td><form:input path="planName" /></td>
			</tr>
			<tr>
				<td>DESCRiption</td>
				<td><form:textarea path="plandes" /></td>
			</tr>
			<tr>
				<td>Started Date</td>
				<td><form:input path="startdate" id="datepicker" /></td>
			</tr>
			<tr>
				<td>Ended Date</td>
				<td><form:input path="enddate" id="datepicker2" /></td>
			</tr>

			<tr>
				<td><input type="reset" value="Reset" /></td>
				<td><input type="Submit" value="Register" /></td>
			</tr>

		</table>


	</form:form>

</body>
</html>