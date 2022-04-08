package com.mire.sixclass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DBController {
	
	//DB로 입력
	public static int studentInfoInsert(StudentInfoModel studentInfoModel) {
		//primary key인 id가 중복됐는지 체크
		List<StudentInfoModel> list = new ArrayList<StudentInfoModel>();
		
		list = studentInfoPrint();
		
		for(StudentInfoModel data : list) {
			if(data.getId() == studentInfoModel.getId()) {
				System.out.println("id중복 : 다시 입력해주세요");
				return 0;
			}
		}//중복체크 끝
		
		//입력됐는지 확인할 return값
		int returnValue = 0;
		
		Connection con = (Connection) DBUtility.getConnection();
		
		//DB에서 실행할 query문
		String insertQuery = "insert into studentinfotbl values(?, ?, ?, ?, ?, ?, ?, ?)";
		//SQL구문 실행하는 객체를 받는 참조변수
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
			
			//executeUpdate로 insert에 반영된 레코드 수 반환(int)
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

	//DB에서 출력
	public static List<StudentInfoModel> studentInfoPrint() {
		//DB에서 꺼내온 데이터를 받을 framework
		List<StudentInfoModel> list = new ArrayList<StudentInfoModel>();
		
		Connection con = (Connection) DBUtility.getConnection();
		
		String printQuery = "select * from studentinfotbl";
		PreparedStatement ps = null;
		//DB에서 꺼내온 데이터를 받는 객체참조변수
		ResultSet resultSet = null;
		
		try {
			ps = (PreparedStatement) con.prepareStatement(printQuery);
			//executeQuery로 DB에 있는 테이블전체 레코드셋반환
			resultSet = ps.executeQuery();
			
			//resultSet에서 꺼내올 데이터가 없을때까지 list에 데이터입력
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
	
	//DB에서 검색
	public static StudentInfoModel studentInfoSearch(int id_buffer) {
		//검색한 id의 학생정보를 저장할 객체
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
	
	//DB에서 삭제
	public static int studentInfoDelete(int id_buffer) {
		//삭제됐는지 확인할 return값
		int returnValue = 0;
		
		Connection con = (Connection) DBUtility.getConnection();
		
		String deleteQuery = "delete from studentinfotbl where id = ?";
		PreparedStatement ps = null;
		
		try {
			ps = (PreparedStatement) con.prepareStatement(deleteQuery);
			String deleteData = "" + id_buffer;
			ps.setString(1, deleteData);
			
			//executeUpdate로 delete에 반영된 레코드 수 반환(int)
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
	
	//DB에서 수정
	public static int studentInfoUpdate(int id_buffer, int c_buffer, int java_buffer, int kotlin_buffer, int sum_buffer, double avr_buffer, char grade_buffer) {
		//수정됐는지 확인할 return값
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
			
			//executeUpdate로 update에 반영된 레코드 수 반환(int)
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
	
	//DB에서 정렬
	public static List<StudentInfoModel> studentInfoSort(int no) {
		final int ASC = 1, DESC = 2;
		//DB에서 꺼내온 정렬데이터를 받을 framework
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
			
			//executeQuery로 DB에 있는 정렬된 레코드셋반환
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
