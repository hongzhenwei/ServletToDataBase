package com.hzw.Service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class studentMain
 */
@WebServlet("/studentMain")
public class studentMain extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public studentMain() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ������Ӧ���뷽ʽ
		resp.setContentType("text/html;charset=UTF-8");
		// ��ȡ������Ϣ
		// ��������
		// ��Ӧ������
		// ��ȡrequest����������
		String str = (String) req.getAttribute("str") == null ? "" : (String) req.getAttribute("str");
		resp.getWriter().write("<font color='red' size='10px'>" + str + "</font>");
		System.out.println(str);
		resp.getWriter().write("<html>");
		resp.getWriter().write("<head>");
		resp.getWriter().write("</head>");
		resp.getWriter().write("<body>");
		// resp.getWriter().write("<font color='red' size='20px'>" + str + "</font>");
		resp.getWriter().write("<h3>���ݿ����ӣ�</h3>");

		resp.getWriter().write(
				"<table border=1  style='width: 650px;height: 200px;'><tr>" + "<td style='width: 35%;'>" + "<table>");
		resp.getWriter().write("<tr><td>");
		resp.getWriter().write("<form action='/ServletToDataBaseTest/DataAccess?op=3' method='post'>");
		resp.getWriter().write("<p>��ѯ������</p></td></tr><tr><td>");
		resp.getWriter().write("<span>ѧ�ţ�</span><input name='number' type='text'/></td></tr><tr><td>");
		resp.getWriter().write("<span>������</span><input name='name' type='text'/></td></tr><tr><td>");
		resp.getWriter().write("<input name='search' type='submit' value='��ѯ'/></td></tr></form>");
		resp.getWriter().write("</table></td>");
		resp.getWriter().write("<td style='width:65%;'><table>");

		resp.getWriter().write("<tr><td>");
		resp.getWriter().write("<form action='/ServletToDataBaseTest/DataAccess?op=0' method='post'>");
		resp.getWriter().write("<p>��Ӳ�����</p></td></tr><tr><td  rowspan='5'>");
		resp.getWriter().write("<span>ѧ�ţ�</span><input name='add_number' type='text'/>");
		resp.getWriter().write("<span>������</span><input name='add_name' type='text'/>");
		resp.getWriter().write("<span>���䣺</span><input name='add_age' type='text'/>");
		resp.getWriter().write("<span>�Ա�</span><input name='add_sex' type='text'/>");
		resp.getWriter().write("<span>רҵ��</span><input name='add_major' type='text'/>"
				+ "</td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td>");
		resp.getWriter().write("<input type='submit' value='���'/></td></tr></form>");
		resp.getWriter().write("</table></td></tr></table>");
		resp.getWriter().write("</body>");
		resp.getWriter().write("</html>");
		// return;
	}
}
