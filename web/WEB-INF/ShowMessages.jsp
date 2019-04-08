<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/mystyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Topic list</title>
</head>
<body>
	<table>
		<tr style="vertical-align: bottom">
			<td>
				<h2>List of messages of topic: ${param.topicname}</h2> <c:choose>
					<c:when test="${messages.size()>0}">
						<table border="1">
							<thead><tr>
							<th>Title</th>
							<th>Body</th>
							<th>Date</th>
							</tr></thead>
							<tbody>
							<c:forEach var="message" items="${messages}" varStatus="row">
								<c:choose>
									<c:when test="${row.count % 2 == 0}">
										<tr class="even">
									</c:when>
									<c:otherwise>
										<tr>
									</c:otherwise>
								</c:choose>
								<td><c:out value="${message.title}" /></td>
								<td><c:out value="${message.body}" /></td>
								<td><c:out value="${message.date}" /></td>
								</tr>
							</c:forEach>
						</tbody>
						</table>
					</c:when>
					<c:otherwise>No topics to display.
		</c:otherwise>
				</c:choose>
			</td>
			<td><c:url value="/CreateMessage" var="postUrl">
				</c:url>
				<h2>Create a new message</h2>
				<form method="post" action="${postUrl}">
					<br> Title: <input name="title" type="text" required>
					<br> Body: <input name="body" type="text" > <br>
					Date: <input name="date" type="date" required> <br> <input
						name="topicid" type="hidden" value="${param.topicid}"> <input
						name="topicname" type="hidden" value="${param.topicname}">
					<input type="submit" value="submit">
				</form></td>
		</tr>
	</table>
	<c:url value="/GetTopics" var="regURL"></c:url>
	<p>
		<a href="${regURL}">Back to the Home Page</a>
	</p>
</body>
</html>