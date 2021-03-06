package com.mire.sixclass;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentInfoMain {
	//과목수 상수
	public static final int SUBJECT = 3;
	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		final int INSERT = 1, PRINT = 2, SEARCH = 3, DELETE = 4, UPDATE = 5, SORT = 6, EXIT = 7;
		boolean flag = false;
		
		while(!flag) {
			int first_menu = selectMenu();
			
			switch(first_menu) {
			case INSERT: studentInfoInsert(); break;
			case PRINT: studentInfoPrint(); break;
			case SEARCH: studentInfoSearch(); break;
			case DELETE: studentInfoDelete(); break;
			case UPDATE: studentInfoUpdate(); break;
			case SORT: studentInfoSort(); break;
			case EXIT: flag = true; break;
			}			
		}
	}
	
	//1.입력
	private static void studentInfoInsert() {
		//입력할 학생정보 필드값
		int id = 0;
		String name = null;
		int c = 0;
		int java = 0;
		int kotlin = 0;
		int sum = 0;
		double avr = 0;
		char grade = '\u0000';
		//입력됐는지 확인할 return값
		int returnValue = 0;
		
		//id입력
		id = studentInfoInsertWhile("id");
		
		//name입력
		while(true) {
			System.out.print("이름(2 ~ 5자) 입력>>");
			name = scan.nextLine();
			
			Pattern pattern = Pattern.compile("^[가-힣]{2,5}$");
	        Matcher matcher = pattern.matcher(name);
	        
	        if(matcher.matches()) {
	        	break;
	        }else {
	        	System.out.println("다시 입력해주세요");
	        	continue;
	        }
		}//end of while(name)
		
		//성적입력
		c = studentInfoInsertWhile("c");
		java = studentInfoInsertWhile("java");
		kotlin = studentInfoInsertWhile("kotlin");
		
		//입력할 id의 총점, 평균, 등급
		sum = c + java + kotlin;
		avr = sum/(double)SUBJECT;
		grade = calGrade(avr);
		
		//DB에 입력할 학생정보객체
		StudentInfoModel studentInfoModel = new StudentInfoModel(id, name, c, java, kotlin, sum, avr, grade);
		
		returnValue = DBController.studentInfoInsert(studentInfoModel);
				
		if(returnValue != 0) {
			System.out.println("입력 성공하였습니다.");
			System.out.println(studentInfoModel);
		}else {
			System.out.println("입력 실패하였습니다.");
		}
		
	}

	//1.1등급계산
	private static char calGrade(double avr) {
		
		char grade = '\u0000';
		
		switch((int)(avr/10.0)) {
		case 10: grade = 'A'; break;
		case 9: grade = 'A'; break;
		case 8: grade = 'B'; break;
		case 7: grade = 'C'; break;
		case 6: grade = 'D'; break;
		default: grade = 'F'; break;
		}
		
		return grade;
	}
	
	//1.2학생정보입력(while중복방지)
	private static int studentInfoInsertWhile(String string) {
		
		int value = 0;
		
		while(true) {
			System.out.print(string + "(1~100) 입력 >>");
			try {
				value = Integer.parseInt(scan.nextLine());				
			}catch(InputMismatchException e) {
				System.out.println("경고: 숫자 입력 해주세요!");
				continue;
			}catch(Exception e) {
				System.out.println("경고: 숫자 입력 해주세요!");
				continue;
			}
			
			if(value >= 1 && value <= 100) {
				break;
			}else {
				System.out.println("1~100 입력해주세요");
				continue;
			}
		}//end of while
		
		return value;
	}
	
	//2.출력(DB 테이블 전체 레코드셋 출력)
	private static void studentInfoPrint() {
		//DB에서 꺼내온 데이터를 받을 framework
		List<StudentInfoModel> list = new ArrayList<StudentInfoModel>();
		
		list = DBController.studentInfoPrint();
		
		for(StudentInfoModel data : list) {
			System.out.println(data);
		}
	}

	//3.검색(id로 검색)
	private static void studentInfoSearch() {
		int id = 0;
		
		//검색할 학생 id
		id = studentInfoInsertWhile("검색할 id");
		
		//DB에서 꺼내온 데이터를 받을 객체
		StudentInfoModel studentInfoModel = new StudentInfoModel();
		
		studentInfoModel = DBController.studentInfoSearch(id);
		
		if(studentInfoModel != null) {
			System.out.println(studentInfoModel);
		}else {
			System.out.println(id + "로 검색한 결과는 없습니다.");
		}
	}

	//4.삭제(id로 삭제)
	private static void studentInfoDelete() {
		int id = 0;
		//삭제됐는지 확인할 return값
		int returnValue = 0;
		
		//삭제할 학생 id
		id = studentInfoInsertWhile("삭제할 id");
		
		returnValue = DBController.studentInfoDelete(id);
		
		if(returnValue != 0) {
			System.out.println(id + "학생이 삭제되었습니다.");
		}else {
			System.out.println("삭제 실패했습니다.");
		}
	}

	//5.수정(id로 검색한 학생 점수, 총점, 평균, 등급 수정)
	private static void studentInfoUpdate() {
		//수정할 학생정보 필드값
		int id = 0;
		int c = 0;
		int java = 0;
		int kotlin = 0;
		int sum = 0;
		double avr = 0.0;
		char grade = '\u0000';
		//수정됐는지 확인할 return값
		int returnValue = 0;
		
		//수정할 학생 id
		id = studentInfoInsertWhile("수정할 id");
		
		//수정할 id의 성적 수정
		c = studentInfoInsertWhile("수정할 c");
		java = studentInfoInsertWhile("수정할 java");
		kotlin = studentInfoInsertWhile("수정할 kotlin");
		
		//수정할 id의 총점, 평균, 등급 수정
		sum = c + java + kotlin;
		avr = sum/(double)SUBJECT;
		grade = calGrade(avr);
		
		returnValue = DBController.studentInfoUpdate(id, c, java, kotlin, sum, avr, grade);
		
		if(returnValue != 0) {
			System.out.println(id + "학생이 수정되었습니다.");
		}else {
			System.out.println("수정 실패했습니다.");
		}
		
	}

	//6.정렬(오름차순 or 내림차순)
	private static void studentInfoSort() {
		//studentInfoSort함수에서 받아온 데이터를 출력할 framework
		List<StudentInfoModel> list = new ArrayList<StudentInfoModel>();
		int no = 0;
		boolean flag = false;
		
		//정렬방식 : 이름순(오름차순, 내림차순)
		while(!flag) {
			System.out.println("1.오름차순(총점), 2.내림차순(총점)");
			System.out.print("정렬방식선택 >>");
			
			try {
				no = Integer.parseInt(scan.nextLine());				
			}catch(InputMismatchException e) {
				System.out.println("경고: 숫자 입력 해주세요!");
				continue;
			}catch(Exception e) {
				System.out.println("경고: 숫자 입력 해주세요!");
				continue;
			}
			
			if(no>=1 && no <=2) {
				flag = true;
			}else {
				System.out.println("경고: (1 ~ 2) 숫자 입력 해주세요!");
				continue;
			}
		}//end of while
		
		list = DBController.studentInfoSort(no);
		
		if(list.size() == 0) {
			System.out.println("정렬할 내용이 없습니다");
			return;
		}
		
		for(StudentInfoModel data : list) {
			System.out.println(data);
		}
		
	}
	
	//초기메뉴 선택
	private static int selectMenu() {
		int menu = 0;
		boolean flag = false;
		
		while(!flag) {
			System.out.println(" ----------------------------------------");
			System.out.println("|1.입력 2.출력 3.검색 4.삭제 5.수정 6.정렬 7.종료|");
			System.out.println(" ----------------------------------------");
			System.out.print("선택 >>");
			
			try {
				menu = Integer.parseInt(scan.nextLine());				
			}catch(InputMismatchException e) {
				System.out.println("경고: 숫자 입력 해주세요!");
				continue;
			}catch(Exception e) {
				System.out.println("경고: 숫자 입력 해주세요!");
				continue;
			}
			
			if(menu >=1 && menu <=7 ) {
				flag = true;
			}else {
				System.out.println("1~7의 숫자를 입력해주세요");
				continue;
			}
		}
		
		return menu;
	}

}
