<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
	<h1>Welcome to the message application</h1>
	<h2>Choose a topic to see its messages</h2>
	<c:choose>
		<c:when test="${topics.size()>0}">
			<table border="1">
				<tr>
					<th>Topic name</th>
				</tr>
				<tbody>
					<c:forEach var="topic" items="${topics}" varStatus="row">
						<c:choose>
							<c:when test="${row.count % 2 == 0}">
								<tr class="even">
							</c:when>
							<c:otherwise>
								<tr>
							</c:otherwise>
						</c:choose>
						<c:url value="/GetMessagesOfTopic" var="regURL">
							<c:param name="topicid" value="${topic.id}" />
							<c:param name="topicname" value="${topic.name}" />
						</c:url>
						<td><a href="${regURL}"> <c:out value="${topic.name}" />
						</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>No topics to display.
		</c:otherwise>
	</c:choose>

</body>
</html>