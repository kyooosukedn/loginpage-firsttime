package com;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try{
            // Set a connection to db
            Class.forName("org.mariadb.jdbc");
            conn = DriverManager.getConnection("yoururl");

            // Execute SQL Query
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "username");
            preparedStatement.setString(2, "password");
            rs = preparedStatement.executeQuery();

            // User exists && credentials correct??
            if (rs.next()) {
                resp.sendRedirect("welcome.jsp");
            } else {
                PrintWriter out = resp.getWriter();
                out.println("<html><body>");
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Invalid username or password');");
                out.println("location='login.jsp';");
                out.println("</script>");
                out.println("</body></html>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
