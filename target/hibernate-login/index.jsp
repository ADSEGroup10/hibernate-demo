<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Halaman awal langsung mengarahkan ke halaman login.
    response.sendRedirect(request.getContextPath() + "/login");
%>
