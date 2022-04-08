package com.mire.sixclass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DBController {
	
	//DB�� �Է�
	public static int studentInfoInsert(StudentInfoModel studentInfoModel) {
		//primary key�� id�� �ߺ��ƴ��� üũ
		List<StudentInfoModel> list = new ArrayList<StudentInfoModel>();
		
		list = studentInfoPrint();
		
		for(StudentInfoModel data : list) {
			if(data.getId() == studentInfoModel.getId()) {
				System.out.println("id�ߺ� : �ٽ� �Է����ּ���");
				return 0;
			}
		}//�ߺ�üũ ��
		
		//�Էµƴ��� Ȯ���� return��
		int returnValue = 0;
		
		Connection con = (Connection) DBUtility.getConnection();
		
		//DB���� ������ query��
		String insertQuery = "insert into studentinfotbl values(?, ?, ?, ?, ?, ?, ?, ?)";
		//SQL���� �����ϴ� ��ü�� �޴� ��������
		PreparedStatement ps = null;
		
		try {
			ps = (PreparedStatement) con.prepareStatement(insertQuery);
			
			String grade = String.valueOf(studentInfoModel.getGrade());
			
			ps.setInt(1, studentInfoModel.getId());
			ps.setString(2, studentInfoModel.getName());
			ps.setInt(3, studentInfoModel.getC());
			ps.setInt(4, studentInfoModel.getJava());
			ps.setInt(5, studentInfoModel.getKotlin());
			ps.setInt(6, studentInfoModel.getSum());
			ps.setDouble(7, studentInfoModel.getAvr());
			ps.setString(8, grade);
			
			//executeUpdate�� insert�� �ݿ��� ���ڵ� �� ��ȯ(int)
			returnValue = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return returnValue;
	}

	//DB���� ���
	public static List<StudentInfoModel> studentInfoPrint() {
		//DB���� ������ �����͸� ���� framework
		List<StudentInfoModel> list = new ArrayList<StudentInfoModel>();
		
		Connection con = (Connection) DBUtility.getConnection();
		
		String printQuery = "select * from studentinfotbl";
		PreparedStatement ps = null;
		//DB���� ������ �����͸� �޴� ��ü��������
		ResultSet resultSet = null;
		
		try {
			ps = (PreparedStatement) con.prepareStatement(printQuery);
			//executeQuery�� DB�� �ִ� ���̺���ü ���ڵ�¹�ȯ
			resultSet = ps.executeQuery();
			
			//resultSet���� ������ �����Ͱ� ���������� list�� �������Է�
			while(resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				int c = resultSet.getInt(3);
				int java = resultSet.getInt(4);
				int kotlin = resultSet.getInt(5);
				int sum = resultSet.getInt(6);
				double avr = resultSet.getDouble(7);
				String grade_buffer = resultSet.getString(8);
				
				char grade = grade_buffer.charAt(0);
				
				StudentInfoModel studentInfoModel = new StudentInfoModel(id, name, c, java, kotlin, sum, avr, grade);
				list.add(studentInfoModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	//DB���� �˻�
	public static StudentInfoModel studentInfoSearch(int id_buffer) {
		//�˻��� id�� �л������� ������ ��ü
		StudentInfoModel studentInfoModel = new StudentInfoModel();
		List<StudentInfoModel> list = new ArrayList<StudentInfoModel>();
		
		list = studentInfoPrint();
		
		for(StudentInfoModel data : list) {
			if(data.getId() == id_buffer) {
				studentInfoModel.setId(data.getId());
				studentInfoModel.setName(data.getName());
				studentInfoModel.setC(data.getC());
				studentInfoModel.setJava(data.getJava());
				studentInfoModel.setKotlin(data.getKotlin());
				studentInfoModel.setSum(data.getSum());
				studentInfoModel.setAvr(data.getAvr());
				studentInfoModel.setGrade(data.getGrade());
			}
		}

		return studentInfoModel;
	}
	
	//DB���� ����
	public static int studentInfoDelete(int id_buffer) {
		//�����ƴ��� Ȯ���� return��
		int returnValue = 0;
		
		Connection con = (Connection) DBUtility.getConnection();
		
		String deleteQuery = "delete from studentinfotbl where id = ?";
		PreparedStatement ps = null;
		
		try {
			ps = (PreparedStatement) con.prepareStatement(deleteQuery);
			String deleteData = "" + id_buffer;
			ps.setString(1, deleteData);
			
			//executeUpdate�� delete�� �ݿ��� ���ڵ� �� ��ȯ(int)
			returnValue = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return returnValue;
	}
	
	//DB���� ����
	public static int studentInfoUpdate(int id_buffer, int c_buffer, int java_buffer, int kotlin_buffer, int sum_buffer, double avr_buffer, char grade_buffer) {
		//�����ƴ��� Ȯ���� return��
		int returnValue = 0;
		
		Connection con = (Connection) DBUtility.getConnection();
		
		String deleteQuery = "update studentinfotbl set c = ?, java = ?, kotlin = ?, sum = ?, avr = ?, grade = ? where id = ?";
		PreparedStatement ps = null;
		
		try {
			ps = (PreparedStatement) con.prepareStatement(deleteQuery);
			String id = String.valueOf(id_buffer);
			String c = String.valueOf(c_buffer);
			String java = String.valueOf(java_buffer);
			String kotlin = String.valueOf(kotlin_buffer);
			String sum = String.valueOf(sum_buffer);
			String avr = String.valueOf(avr_buffer);
			String grade = String.valueOf(grade_buffer);
			ps.setString(1, c);
			ps.setString(2, java);
			ps.setString(3, kotlin);
			ps.setString(4, sum);
			ps.setString(5, avr);
			ps.setString(6, grade);
			ps.setString(7, id);
			
			//executeUpdate�� update�� �ݿ��� ���ڵ� �� ��ȯ(int)
			returnValue = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return returnValue;
	}
	
	//DB���� ����
	public static List<StudentInfoModel> studentInfoSort(int no) {
		final int ASC = 1, DESC = 2;
		//DB���� ������ ���ĵ����͸� ���� framework
		List<StudentInfoModel> list = new ArrayList<StudentInfoModel>();
		
		Connection con = (Connection) DBUtility.getConnection();
		
		String sortQuery = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		switch(no) {
		case ASC:
			sortQuery = "select * from studentinfotbl order by sum asc";
			break;
		case DESC:
			sortQuery = "select * from studentinfotbl order by sum desc";
			break;
		}
		try {
			ps = (PreparedStatement) con.prepareStatement(sortQuery);
			
			//executeQuery�� DB�� �ִ� ���ĵ� ���ڵ�¹�ȯ
			resultSet = ps.executeQuery();
			
			while(resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				int c = resultSet.getInt(3);
				int java = resultSet.getInt(4);
				int kotlin = resultSet.getInt(5);
				int sum = resultSet.getInt(6);
				double avr = resultSet.getDouble(7);
				String grade_buffer = resultSet.getString(8);
				char grade = grade_buffer.charAt(0);
				
				StudentInfoModel studentInfoModel = new StudentInfoModel(id, name, c, java, kotlin, sum, avr, grade);
				list.add(studentInfoModel);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}

}
