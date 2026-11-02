package com.hibernate.controller;

import java.io.IOException;

import com.hibernate.dao.UserDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller login.
 *
 * GET  /login  -> menampilkan form login
 * POST /login  -> memproses kredensial
 * GET  /logout -> menghapus session lalu kembali ke form login
 */
@WebServlet(name = "LoginController", urlPatterns = { "/login", "/logout" })
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if ("/logout".equals(request.getServletPath())) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Menampilkan halaman login (forward, bukan redirect, agar
        // atribut request tetap bisa dibaca oleh JSP).
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean valid;
        try {
            valid = userDao.validate(username, password);
        } catch (RuntimeException e) {
            // Jangan bocorkan detail database ke pengguna.
            getServletContext().log("Login gagal karena error database", e);
            request.setAttribute("error", "Terjadi kesalahan pada database. Silakan coba lagi.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        if (valid) {
            // Session HTTP dipakai untuk menyimpan penanda login,
            // sehingga login-success.jsp bisa menolak akses langsung.
            HttpSession session = request.getSession(true);
            session.setAttribute("user", username.trim());

            // Redirect (bukan forward) mencegah form terkirim ulang
            // saat halaman di-refresh.
            response.sendRedirect(request.getContextPath() + "/login-success.jsp");
        } else {
            request.setAttribute("error", "Username atau password salah.");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
