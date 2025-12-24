<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dépôt de cours</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@1/css/pico.min.css">
</head>
<body>
<jsp:include page="fragments/header.jspf"/>
<main class="container">
    <h2>Déposer un PDF</h2>
    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data">
        <label>Titre
            <input type="text" name="title" required>
        </label>
        <label>Module
            <select name="moduleId">
                <c:forEach var="module" items="${modules}">
                    <option value="${module.id}">${module.title}</option>
                </c:forEach>
            </select>
        </label>
        <label>Fichier (PDF)
            <input type="file" name="file" accept="application/pdf" required>
        </label>
        <button type="submit">Uploader</button>
    </form>
</main>
</body>
</html>

