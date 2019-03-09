package com.hzw.ServletOperator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hzw.ServletToDataBase.DataBaseOperator;
import com.hzw.Student.Student;

/**
 * Servlet implementation class DataAccess
 */
@WebServlet("/DataAccess")
public class DataAccess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DataAccess() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ���������Ϣ�ı��뷽ʽΪUTF-8
		req.setCharacterEncoding("UTF-8");
		// ��ò������Ͳ�����0-���룬1-ɾ����2-���£�3-��ѯ��4-�༭
		String op = req.getParameter("op");
		int method = Integer.parseInt(op);
		switch (method) {
		case 0:// ���ѧ��
			insert(req, resp);
			break;
		case 1:// ɾ��ѧ��
			delete(req, resp);
			break;
		case 2:// ����ѧ��
			update(req, resp);
			break;
		case 3:// ����ѧ��
			search(req, resp);
			break;
		case 4:// �༭ѧ��
			edit(req, resp);
			break;
		default:
			break;
		}
	}

	private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		List<String> result = new ArrayList<String>();
		PrintWriter writer = resp.getWriter();
		// �����Ҫ���ĵ�ѧ����Ϣ
		String id = req.getParameter("id");
		// ����ѧ�Ų���
		Set<Student> sts = DataBaseOperator.getInstance().search(id, "");
		Iterator<Student> iterator = sts.iterator();
		String str = "<form action = '/ServletToDataBaseTest/DataAccess?op=2' method='post' target='workspace'>";
		result.add(str);
		while (iterator.hasNext()) {
			Student student = iterator.next();
			str = "ѧ��:<input type='text' name='id' value='" + student.getId() + "'><br>"
					+ "����:<input type='text' name='name' value='" + student.getName() + "'><br>"
					+ "����:<input type='text' name='age' value='" + student.getAge() + "'><br>"
					+ "�Ա�:<input type='text' name='gender' value='" + student.getGender() + "'><br>"
					+ "רҵ:<input type='text' name='major' value='" + student.getMajor() + "'><br>";
			result.add(str);
		}
		str = "<input style='submit' name='modify' value='�޸�'><br>"
				+ "<a href=\"/ServletToDataBaseTest/DataAccess?op=3\">���ز�ѯ���ҳ��</a>" + "</form>";
		result.add(str);
		for (String student : result) {
			writer.println(student);
		}
	}

	private void search(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter writer = resp.getWriter().append("Hello World! �������");
		ArrayList<String> result = new ArrayList<String>();
		// ��ò�ѯ����
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		if (id == null)
			id = "";
		if (name == null)
			name = "";
		// ���ݲ�ѯ������ѯ���ݿ⣬�������������student���͵�set������
		Set<Student> sts = DataBaseOperator.getInstance().search(id, name);
		// ͨ��ƴ���ַ�����ʽ���ձ����ʽ������֯
		String str = "";
		// ��֯��ͷ
		str = "<table frame=\"border\" bordercolor=\"black\" style=\"width:600px;\">";
		result.add(str);
		str = "<tr><td style=\"border:1px solid black;\">ѧ��</td><td style=\"border:1px solid black;\">����</td>"
				+ "<td style=\"border:1px solid black;\">����</td><td style= \"border:1px solid black;\">�Ա�</td>"
				+ "<td style=\"border:1px solid black;\">�о�����</td><td style=\"border:1px solid black;\">����</td></tr>";
		result.add(str);
		// ͨ��������������ѯ���
		Iterator<Student> iterator = sts.iterator();
		while (iterator.hasNext()) {
			Student st = iterator.next();
			str = "<tr>";
			str = str + "<td style=\"border:1px solid black;\">" + st.getId() + "</td>";
			str = str + "<td style=\"border:1px solid black;\">" + st.getName() + "</td>";
			str = str + "<td style=\"border:1px solid black;\">" + st.getAge() + "</td>";
			str = str + "<td style=\"border:1px solid black;\">" + st.getGender() + "</td>";
			str = str + "<td style=\"border:1px solid black;\">" + st.getMajor() + "</td>";
			str = str + "<td style=\"border:1px solid black;\">" + "<a href='DataAccess?op=1&id=" + st.getId()
					+ "'>ɾ��</a>" + "&nbsp&nbsp&nbsp&nbsp" + "<a href='DataAccess?op=4&id=" + st.getId() + "'>�޸�</a>"
					+ "</td>";
			str = str + "</tr>";
			result.add(str);
		}
		for (String tap : result) {
			writer.println(tap);
		}
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// ���Ҫɾ����ѧ��ѧ��
		String id = req.getParameter("id");
		// ͨ��ѧ�ţ������ݿ���ɾ��ѧ�Ŷ�Ӧ��ѧ��
		DataBaseOperator.getInstance().delete(id);
		resp.sendRedirect("/ServletToDataBaseTest/DataAccess?op=3");
	}

	private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// ����޸ĺ��ѧ����Ϣ
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String age = req.getParameter("age");
		String gender = req.getParameter("gender");
		String major = req.getParameter("major");
		// ����ѧ�����󣬲���ʼ��
		Student student = new Student();
		student.setId(Integer.parseInt(id));
		student.setName(name);
		student.setAge(Integer.parseInt(age));
		student.setGender(gender);
		student.setMajor(major);
		// �����ѧ����Ϣ��ӵ����ݿ���
		DataBaseOperator.getInstance().update(student);
		// ѧ������������ض��򵽽��ҳ��
		resp.sendRedirect("/ServletToDataBaseTest/DataAccess?op=3");

	}

	private void insert(HttpServletRequest req, HttpServletResponse resp) {
		String str = null;
		try {
			// ���ѧ����Ϣ
			String id = req.getParameter("add_number");
			String name = req.getParameter("add_name");
			String age = req.getParameter("add_age");
			String gender = req.getParameter("add_sex");
			String major = req.getParameter("add_major");
			if (id == "" || name == "" || age == "" || gender == "" || major == "") {
				str = "δ��������...";
				resp.sendRedirect("/ServletToDataBaseTest/studentMain");
			} else {
				// ����ѧ�����󣬲���ʼ��
				Student student = new Student();
				student.setId(Integer.parseInt(id));
				student.setName(name);
				student.setAge(Integer.parseInt(age));
				student.setGender(gender);
				student.setMajor(major);
				// �����ѧ����Ϣ��ӵ����ݿ���
				DataBaseOperator.getInstance().insert(student);
				// ѧ������������ض��򵽽��ҳ��
				resp.sendRedirect("/ServletToDataBaseTest/DataAccess?op=3");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
