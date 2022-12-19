package com.ms.studentapp.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ms.studentapp.dto.Student;
import com.mysql.cj.xdevapi.CreateIndexParams;

public class StudentDatabaseOperation {

	public static final String DRIVERCLASS = "com.mysql.cj.jdbc.Driver";
	public static final String DBURL = "jdbc:mysql://localhost:3306/demo_nik?user=root&password=root";

	public boolean insertData(Student stu) throws IOException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVERCLASS);
			con = DriverManager.getConnection(DBURL);
			String query = "INSERT INTO student_table VALUE(?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, stu.getId());
			pstmt.setString(2, stu.getName());
			pstmt.setDouble(3, stu.getMarks());
			pstmt.setString(4, stu.getEmailId());
			pstmt.setString(5, stu.getPassword());
			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected != 0) {
				return true;
			} else {
				return false;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}
		}
		return false;
	}

	public Student loginValidate(String mail, String pass) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(DRIVERCLASS);
			con = DriverManager.getConnection(DBURL);
			String query = "SELECT * FROM student_table WHERE email =? AND password = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mail);
			pstmt.setString(2, pass);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				Student s = new Student();

				int id = rs.getInt(1);
				String name = rs.getString(2);
				double marks = rs.getDouble(3);
				String email = rs.getString(4);
				String password = rs.getString(5);

				s.setId(id);
				s.setName(name);
				s.setMarks(marks);
				s.setEmailId(email);
				s.setPassword(password);

				return s;

			} else {
				return null;

			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public ArrayList<Student> getAllStudents() {
		//SELECT * FROM student_table
		ArrayList<Student> allStudentList = new ArrayList<>();
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVERCLASS);
			con = DriverManager.getConnection(DBURL);
			
			String query ="SELECT * FROM student_table";
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				double marks = rs.getDouble(3);
				String email = rs.getString(4);
				String password = rs.getString(5);

				Student s = new Student();
				
				s.setId(id);
				s.setName(name);
				s.setMarks(marks);
				s.setEmailId(email);
				s.setPassword(password);
				
				allStudentList.add(s);
				
				
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return allStudentList;
	}

	public Student getStudent(int stuId) {
		//SELECT * FROM student_table WHERE std_id = ?
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVERCLASS);
			con = DriverManager.getConnection(DBURL);
			
			String query ="SELECT * FROM student_table WHERE std_id = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, stuId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Student s = new Student();

				int id = rs.getInt(1);
				String name = rs.getString(2);
				double marks = rs.getDouble(3);
				String email = rs.getString(4);
				String password = rs.getString(5);

				s.setId(id);
				s.setName(name);
				s.setMarks(marks);
				s.setEmailId(email);

				return s;

			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
			return null;
	}

	public ArrayList<Student> getAllStudentsByMarks(int lwstvalue, int histvalue) {
		//SELECT * FROM student_table WHERE marks >= ? AND marks <= ?
		ArrayList<Student> studentbymarks = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVERCLASS);
			con = DriverManager.getConnection(DBURL);
			
			String query = "SELECT * FROM student_table WHERE marks >= ? AND marks <= ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, lwstvalue);
			pstmt.setInt(2, histvalue);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				double marks = rs.getDouble(3);
				String email = rs.getString(4);
				String password = rs.getString(5);

				Student s = new Student();
				
				s.setId(id);
				s.setName(name);
				s.setMarks(marks);
				s.setEmailId(email);
				s.setPassword(password);
				
				studentbymarks.add(s);
				
			} 
			
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		
		return studentbymarks;
	}

	public boolean updateStudent(Student stu) {
		//UPDATE studen_table set name = ? ,marks = ? ,email =? where std_id=?
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVERCLASS);
			con = DriverManager.getConnection(DBURL);
			
			String query = "UPDATE student_table set name = ? ,marks = ? ,email = ? where std_id=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, stu.getName());
			pstmt.setDouble(2, stu.getMarks());
			pstmt.setString(3, stu.getEmailId());
			pstmt.setInt(4, stu.getId());
			int rowsAffected = pstmt.executeUpdate();
			
			return rowsAffected != 0;
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return false;
	}

	public Boolean updatePassword(String mailId, String newPassword) {
		//Update MEthod
		//UPDATE student_table SET password = ? WHERE email = ?
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVERCLASS);
			con = DriverManager.getConnection(DBURL);
			
			String query = "UPDATE student_table SET password = ? WHERE email = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, newPassword);
			pstmt.setString(2, mailId);		
			
			int updatePassword = pstmt.executeUpdate();
			if (updatePassword == 0) {
				return false;
			} else {
				return true;
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}
		}
		
		
		return null;
	}

	public boolean deleteStudent(int deleteId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVERCLASS);
			con = DriverManager.getConnection(DBURL);
			String query = "delete from student_table where std_id=?";
		 
			pstmt = con.prepareStatement(query);
		 	pstmt.setInt(1, deleteId);
		 	int rowsAffected=pstmt.executeUpdate();
		 	return rowsAffected!=0;
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}
		}
			
		return false;
	}

}
