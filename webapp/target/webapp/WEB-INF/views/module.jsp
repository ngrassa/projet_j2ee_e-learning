<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Module ${module.title}</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@1/css/pico.min.css">
</head>
<body>
<jsp:include page="fragments/header.jspf"/>
<main class="container">
    <h2>${module.title}</h2>
    <p>${module.description}</p>

    <h3>Ressources PDF</h3>
    <c:if test="${empty resources}">
        <p>Aucun document pour le moment.</p>
    </c:if>
    <c:forEach var="res" items="${resources}">
        <article>
            <header>
                <strong>${res.title}</strong>
                <small>par ${res.uploadedBy}</small>
            </header>
            <p><a href="${res.path}" target="_blank">Ouvrir le PDF</a></p>
            <details>
                <summary>Commentaires</summary>
                <div>
                    <c:forEach var="comment" items="${commentsByResource[res.id]}">
                        <p><strong>${comment.author}</strong> : ${comment.content}</p>
                    </c:forEach>
                    <form method="post" action="${pageContext.request.contextPath}/comment">
                        <input type="hidden" name="resourceId" value="${res.id}">
                        <label>Ajouter un commentaire
                            <textarea name="content" required></textarea>
                        </label>
                        <button type="submit">Publier</button>
                    </form>
                </div>
            </details>
        </article>
    </c:forEach>
</main>
</body>
</html>

