package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UseDeleteServlet")
public class UserDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		HttpSession session = request.getSession();

		if(userID == null || userID.equals("") )
		{	request.getSession().setAttribute("messageType", "���� �޽���");
			request.getSession().setAttribute("messageContent", "�߸��� �����Դϴ�.");
			response.sendRedirect("update.jsp");
			return;
			
		}
		if(!userID.equals((String) session.getAttribute("userID"))) {
			session.setAttribute("messageType", "���� �޽���");
			session.setAttribute("messageContent", "������ �� �����ϴ�.");
			response.sendRedirect("index.jsp");
			return;
		}
			
		int result = new UserDAO().delete(userID);
		if(result == 1 ) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "���� �޽���");
			request.getSession().setAttribute("messageContent", "ȸ��Ż�� �Ϸ�Ǿ����ϴ�.");
			response.sendRedirect("index.jsp");
		}
		else {
			request.getSession().setAttribute("messageType", "���� �޽���");
			request.getSession().setAttribute("messageContent", "�����ͺ��̽� ������ �߻��߽��ϴ�");
			response.sendRedirect("update.jsp");
		}
		session.invalidate();
	}

}
