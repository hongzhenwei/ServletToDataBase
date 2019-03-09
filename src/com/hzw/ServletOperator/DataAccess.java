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
		// 设置输出信息的编码方式为UTF-8
		req.setCharacterEncoding("UTF-8");
		// 获得操作类型参数：0-插入，1-删除，2-更新，3-查询，4-编辑
		String op = req.getParameter("op");
		int method = Integer.parseInt(op);
		switch (method) {
		case 0:// 添加学生
			insert(req, resp);
			break;
		case 1:// 删除学生
			delete(req, resp);
			break;
		case 2:// 更新学生
			update(req, resp);
			break;
		case 3:// 查找学生
			search(req, resp);
			break;
		case 4:// 编辑学生
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
		// 获得需要更改的学生信息
		String id = req.getParameter("id");
		// 根据学号查找
		Set<Student> sts = DataBaseOperator.getInstance().search(id, "");
		Iterator<Student> iterator = sts.iterator();
		String str = "<form action = '/ServletToDataBaseTest/DataAccess?op=2' method='post' target='workspace'>";
		result.add(str);
		while (iterator.hasNext()) {
			Student student = iterator.next();
			str = "学号:<input type='text' name='id' value='" + student.getId() + "'><br>"
					+ "姓名:<input type='text' name='name' value='" + student.getName() + "'><br>"
					+ "年龄:<input type='text' name='age' value='" + student.getAge() + "'><br>"
					+ "性别:<input type='text' name='gender' value='" + student.getGender() + "'><br>"
					+ "专业:<input type='text' name='major' value='" + student.getMajor() + "'><br>";
			result.add(str);
		}
		str = "<input style='submit' name='modify' value='修改'><br>"
				+ "<a href=\"/ServletToDataBaseTest/DataAccess?op=3\">返回查询结果页面</a>" + "</form>";
		result.add(str);
		for (String student : result) {
			writer.println(student);
		}
	}

	private void search(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter writer = resp.getWriter().append("Hello World! 你好世界");
		ArrayList<String> result = new ArrayList<String>();
		// 获得查询条件
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		if (id == null)
			id = "";
		if (name == null)
			name = "";
		// 根据查询条件查询数据库，并将结果保存在student类型的set集合中
		Set<Student> sts = DataBaseOperator.getInstance().search(id, name);
		// 通过拼接字符串方式按照表格形式进行组织
		String str = "";
		// 组织表头
		str = "<table frame=\"border\" bordercolor=\"black\" style=\"width:600px;\">";
		result.add(str);
		str = "<tr><td style=\"border:1px solid black;\">学号</td><td style=\"border:1px solid black;\">姓名</td>"
				+ "<td style=\"border:1px solid black;\">年龄</td><td style= \"border:1px solid black;\">性别</td>"
				+ "<td style=\"border:1px solid black;\">研究方向</td><td style=\"border:1px solid black;\">操作</td></tr>";
		result.add(str);
		// 通过迭代器遍历查询结果
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
					+ "'>删除</a>" + "&nbsp&nbsp&nbsp&nbsp" + "<a href='DataAccess?op=4&id=" + st.getId() + "'>修改</a>"
					+ "</td>";
			str = str + "</tr>";
			result.add(str);
		}
		for (String tap : result) {
			writer.println(tap);
		}
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 获得要删除的学生学号
		String id = req.getParameter("id");
		// 通过学号，从数据库中删除学号对应的学生
		DataBaseOperator.getInstance().delete(id);
		resp.sendRedirect("/ServletToDataBaseTest/DataAccess?op=3");
	}

	private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 获得修改后的学生信息
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String age = req.getParameter("age");
		String gender = req.getParameter("gender");
		String major = req.getParameter("major");
		// 创建学生对象，并初始化
		Student student = new Student();
		student.setId(Integer.parseInt(id));
		student.setName(name);
		student.setAge(Integer.parseInt(age));
		student.setGender(gender);
		student.setMajor(major);
		// 将这个学生信息添加到数据库中
		DataBaseOperator.getInstance().update(student);
		// 学生添加完后进行重定向到结果页面
		resp.sendRedirect("/ServletToDataBaseTest/DataAccess?op=3");

	}

	private void insert(HttpServletRequest req, HttpServletResponse resp) {
		String str = null;
		try {
			// 获得学生信息
			String id = req.getParameter("add_number");
			String name = req.getParameter("add_name");
			String age = req.getParameter("add_age");
			String gender = req.getParameter("add_sex");
			String major = req.getParameter("add_major");
			if (id == "" || name == "" || age == "" || gender == "" || major == "") {
				str = "未输入完整...";
				resp.sendRedirect("/ServletToDataBaseTest/studentMain");
			} else {
				// 创建学生对象，并初始化
				Student student = new Student();
				student.setId(Integer.parseInt(id));
				student.setName(name);
				student.setAge(Integer.parseInt(age));
				student.setGender(gender);
				student.setMajor(major);
				// 将这个学生信息添加到数据库中
				DataBaseOperator.getInstance().insert(student);
				// 学生添加完后进行重定向到结果页面
				resp.sendRedirect("/ServletToDataBaseTest/DataAccess?op=3");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
