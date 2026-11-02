<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String errorMessage = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Login - Hibernate Login App</title>
    <style>
        body { font-family: Arial, Helvetica, sans-serif; background: #f2f4f7; margin: 0;
               display: flex; align-items: center; justify-content: center; min-height: 100vh; }
        .card { background: #fff; padding: 32px 36px; border-radius: 8px; width: 340px;
                box-shadow: 0 2px 10px rgba(0,0,0,.12); }
        h2 { margin: 0 0 20px; text-align: center; color: #222; }
        label { display: block; margin: 14px 0 6px; font-size: 14px; color: #333; }
        input { width: 100%; padding: 9px 10px; border: 1px solid #ccc; border-radius: 4px;
                box-sizing: border-box; font-size: 14px; }
        button { width: 100%; margin-top: 22px; padding: 10px; border: 0; border-radius: 4px;
                 background: #2563eb; color: #fff; font-size: 15px; cursor: pointer; }
        button:hover { background: #1d4ed8; }
        .error { background: #fdecea; color: #b3261e; border: 1px solid #f5c2c0;
                 padding: 9px 12px; border-radius: 4px; font-size: 14px; margin-bottom: 4px; }
    </style>
</head>
<body>
<div class="card">
    <h2>Sign in</h2>

    <% if (errorMessage != null) { %>
        <p class="error"><%= errorMessage %></p>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/login">
        <label for="username">Username</label>
        <input type="text" id="username" name="username"
               value="${requestScope.username}" required autofocus>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required>

        <button type="submit">Login</button>
    </form>
</div>
</body>
</html>
