<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Tolak akses langsung tanpa login.
    String loggedUser = (session == null) ? null : (String) session.getAttribute("user");
    if (loggedUser == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Login Berhasil</title>
    <style>
        body { font-family: Arial, Helvetica, sans-serif; background: #f2f4f7; margin: 0;
               display: flex; align-items: center; justify-content: center; min-height: 100vh; }
        .card { background: #fff; padding: 32px 36px; border-radius: 8px; width: 340px;
                box-shadow: 0 2px 10px rgba(0,0,0,.12); text-align: center; }
        h2 { margin: 0 0 10px; color: #15803d; }
        p { color: #444; font-size: 15px; }
        .user { font-weight: bold; color: #111; }
        a.btn { display: inline-block; margin-top: 18px; padding: 10px 18px; border-radius: 4px;
                background: #dc2626; color: #fff; text-decoration: none; font-size: 14px; }
        a.btn:hover { background: #b91c1c; }
    </style>
</head>
<body>
<div class="card">
    <h2>Login Berhasil</h2>
    <p>Selamat datang, <span class="user"><%= loggedUser %></span>!</p>
    <p>Autentikasi berhasil divalidasi melalui Hibernate.</p>
    <a class="btn" href="${pageContext.request.contextPath}/logout">Logout</a>
</div>
</body>
</html>
