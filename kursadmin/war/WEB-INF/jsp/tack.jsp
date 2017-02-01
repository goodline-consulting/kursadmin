<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>

<head>
<meta http-equiv="Content-Language" content="sv">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
</head>

<body>

<p align="center"><img border="0" src="images/${modelMap.logga}" width="${modelMap.loggaW}" height="${modelMap.loggaH}"></p>
<p align="center">&nbsp;</p>
<H1 align="center">${modelMap.rubrik}</H1>
<c:forEach items="${modelMap.body}" var="kursText" varStatus="cnt">
	<p align="center">${kursText}</p>
</c:forEach>

<p align="center">${modelMap.footer}</p>
<form method="POST" action="${modelMap.actionUrl}">
	<p align="center"><input type="submit" value="Tillbaka" name="B1"></p>
</form>
<p align="center">&nbsp;</p>

</body>

</html>