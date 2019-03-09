package com.hzw.ServletToDataBase;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.hzw.Student.Student;
import com.mysql.jdbc.Connection;

public class DataBaseOperator {
	// ���ݿ����ӱ����������洢���������ݿ�����
	Connection connection = null;
	// ʵ������,�����洢������Ψһʵ��
	static DataBaseOperator instance = null;

	// ���캯����Ϊʵ�ֵ�һģʽ��˽��
	private DataBaseOperator() {
		try {
			init();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ��ʼ�������������н������ݿ�����
	void init() throws InstantiationException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// ���ݿ�·��
			String url = "jdbc:mysql://129.204.49.63/Student?characterEncoding=utf-8";
			// String url =
			// "jdbc:mysql://www.cerambycidae.org.cn/usc_javaee?characterEncoding=utf-8";
			// ���ݿ��û���
			String user = "root";
			// ���ݿ���������
			String password = "123456";
			// ������������ݿ�����
			connection = (Connection) DriverManager.getConnection(url, user, password);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// ��øõ���ʵ���ķ���
	public static DataBaseOperator getInstance() {
		// ��������ڸ����ʵ�������´�������������instance
		if (instance == null)
			instance = new DataBaseOperator();
		return instance;
	}

	// ���ݿ���뷽��
	public void insert(Student st) {
		// ��Studentʵ���л�ø���ѧ������Ϣ
		int id = st.getId();
		String name = st.getName();
		int age = st.getAge();
		String gender = st.getGender();
		String major = st.getMajor();
		// �����������ݿ�SQL���
		String sql = "insert into students(id, name, age, gender, major) values(" + id + ",'" + name + "'," + age + ",'"
				+ gender + "','" + major + "');";
		System.out.println(sql);
		// ִ�����ݿ����
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

	// ���ݿ�ɾ������
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

	// ���ݿ���²���
	public void update(Student st) {
		// ��Studentʵ���л�ø���ѧ������Ϣ
		int id = st.getId();
		String name = st.getName();
		int age = st.getAge();
		String gender = st.getGender();
		String major = st.getMajor();
		String sql = "update students set id=" + id + ",name='" + name + "',age=" + age + ",gender='" + gender
				+ "',major='" + major + "' where id=" + id + "";
		System.out.println(sql);
		// ִ�����ݿ����
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

	// ���ݿ��ѯ����
	// �����ѯ���Ϊ��ѯ��������ѧ�����󼯺�
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
				// ���ѧ�ź�������Ϊ�գ����ѯ����ѧ����Ϣ
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
			// ���ز�ѯ��������ѧ���ļ���
			return stSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}