<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>E-learning - Connexion</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@1/css/pico.min.css">
</head>
<body>
<main class="container">
    <h1>Mini plateforme e-learning</h1>
    <p>Rôles disponibles : admin/admin123 ou student/student123</p>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <label>Utilisateur
            <input type="text" name="username" required>
        </label>
        <label>Mot de passe
            <input type="password" name="password" required>
        </label>
        <button type="submit">Se connecter</button>
        <p style="color:red">${requestScope.error}</p>
    </form>
</main>
</body>
</html>

