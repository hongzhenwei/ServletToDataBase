package com.hzw.ServletToDataBase;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.hzw.Student.Student;
import com.mysql.jdbc.Connection;

public class DataBaseOperator {
	// 数据库连接变量，用来存储建立的数据库连接
	Connection connection = null;
	// 实例变量,用来存储产生的唯一实例
	static DataBaseOperator instance = null;

	// 构造函数，为实现单一模式，私有
	private DataBaseOperator() {
		try {
			init();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 初始化方法，在其中建立数据库连接
	void init() throws InstantiationException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 数据库路径
			String url = "jdbc:mysql://129.204.49.63/Student?characterEncoding=utf-8";
			// String url =
			// "jdbc:mysql://www.cerambycidae.org.cn/usc_javaee?characterEncoding=utf-8";
			// 数据库用户名
			String user = "root";
			// 数据库连接密码
			String password = "123456";
			// 建立并获得数据库连接
			connection = (Connection) DriverManager.getConnection(url, user, password);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// 获得该单例实例的方法
	public static DataBaseOperator getInstance() {
		// 如果不存在该类的实例，则新创建，并保存在instance
		if (instance == null)
			instance = new DataBaseOperator();
		return instance;
	}

	// 数据库插入方法
	public void insert(Student st) {
		// 从Student实例中获得各个学生的信息
		int id = st.getId();
		String name = st.getName();
		int age = st.getAge();
		String gender = st.getGender();
		String major = st.getMajor();
		// 创建插入数据库SQL语句
		String sql = "insert into students(id, name, age, gender, major) values(" + id + ",'" + name + "'," + age + ",'"
				+ gender + "','" + major + "');";
		System.out.println(sql);
		// 执行数据库操作
		java.sql.Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 数据库删除方法
	public void delete(String id) {
		try {
			java.sql.Statement statement = null;
			statement = connection.createStatement();
			statement.executeUpdate("delete from students where id=" + id + "");
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 数据库更新操作
	public void update(Student st) {
		// 从Student实例中获得各个学生的信息
		int id = st.getId();
		String name = st.getName();
		int age = st.getAge();
		String gender = st.getGender();
		String major = st.getMajor();
		String sql = "update students set id=" + id + ",name='" + name + "',age=" + age + ",gender='" + gender
				+ "',major='" + major + "' where id=" + id + "";
		System.out.println(sql);
		// 执行数据库操作
		java.sql.Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 数据库查询操作
	// 输出查询结果为查询到的所有学生对象集合
	public Set<Student> search(String id, String name) {
		java.sql.Statement statement = null;
		ResultSet rSet = null;
		try {
			statement = connection.createStatement();
			Set<Student> stSet = new HashSet<Student>();
			if (id == null)
				id = "";
			if (name == null)
				name = "";
			if (id == "" && name == "") {
				// 如果学号和姓名都为空，则查询所有学生信息
				rSet = statement.executeQuery("select * from students");
			}
			if (id != "" && name == "") {
				rSet = statement.executeQuery("select * from students where id=" + id + "");
			}
			if (id == "" && name != "") {
				rSet = statement.executeQuery("select * from students where name='" + name + "'");
			}
			if (id != "" && name != "") {
				rSet = statement.executeQuery("select * from students where id=" + id + " and name='" + name + "'");
			}
			while (rSet.next()) {
				Student student = new Student();
				student.setId(rSet.getInt("id"));
				student.setName(rSet.getString("name"));
				student.setAge(rSet.getInt("age"));
				student.setGender(rSet.getString("gender"));
				student.setMajor(rSet.getString("major"));
				stSet.add(student);
			}
			if (rSet != null) {
				rSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			// 返回查询到的所有学生的集合
			return stSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}