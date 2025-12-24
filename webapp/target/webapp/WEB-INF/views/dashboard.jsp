<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@1/css/pico.min.css">
</head>
<body>
<jsp:include page="fragments/header.jspf"/>
<main class="container">
    <h2>Modules disponibles</h2>
    <div class="grid">
        <c:forEach var="module" items="${modules}">
            <article>
                <header>
                    <strong>${module.title}</strong>
                </header>
                <p>${module.description}</p>
                <a href="${pageContext.request.contextPath}/modules/${module.id}">Consulter</a>
            </article>
        </c:forEach>
    </div>
</main>
</body>
</html>

