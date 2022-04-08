package com.mire.sixclass;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentInfoMain {
	//����� ���
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
	
	//1.�Է�
	private static void studentInfoInsert() {
		//�Է��� �л����� �ʵ尪
		int id = 0;
		String name = null;
		int c = 0;
		int java = 0;
		int kotlin = 0;
		int sum = 0;
		double avr = 0;
		char grade = '\u0000';
		//�Էµƴ��� Ȯ���� return��
		int returnValue = 0;
		
		//id�Է�
		id = studentInfoInsertWhile("id");
		
		//name�Է�
		while(true) {
			System.out.print("�̸�(2 ~ 5��) �Է�>>");
			name = scan.nextLine();
			
			Pattern pattern = Pattern.compile("^[��-�R]{2,5}$");
	        Matcher matcher = pattern.matcher(name);
	        
	        if(matcher.matches()) {
	        	break;
	        }else {
	        	System.out.println("�ٽ� �Է����ּ���");
	        	continue;
	        }
		}//end of while(name)
		
		//�����Է�
		c = studentInfoInsertWhile("c");
		java = studentInfoInsertWhile("java");
		kotlin = studentInfoInsertWhile("kotlin");
		
		//�Է��� id�� ����, ���, ���
		sum = c + java + kotlin;
		avr = sum/(double)SUBJECT;
		grade = calGrade(avr);
		
		//DB�� �Է��� �л�������ü
		StudentInfoModel studentInfoModel = new StudentInfoModel(id, name, c, java, kotlin, sum, avr, grade);
		
		returnValue = DBController.studentInfoInsert(studentInfoModel);
				
		if(returnValue != 0) {
			System.out.println("�Է� �����Ͽ����ϴ�.");
			System.out.println(studentInfoModel);
		}else {
			System.out.println("�Է� �����Ͽ����ϴ�.");
		}
		
	}

	//1.1��ް��
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
	
	//1.2�л������Է�(while�ߺ�����)
	private static int studentInfoInsertWhile(String string) {
		
		int value = 0;
		
		while(true) {
			System.out.print(string + "(1~100) �Է� >>");
			try {
				value = Integer.parseInt(scan.nextLine());				
			}catch(InputMismatchException e) {
				System.out.println("���: ���� �Է� ���ּ���!");
				continue;
			}catch(Exception e) {
				System.out.println("���: ���� �Է� ���ּ���!");
				continue;
			}
			
			if(value >= 1 && value <= 100) {
				break;
			}else {
				System.out.println("1~100 �Է����ּ���");
				continue;
			}
		}//end of while
		
		return value;
	}
	
	//2.���(DB ���̺� ��ü ���ڵ�� ���)
	private static void studentInfoPrint() {
		//DB���� ������ �����͸� ���� framework
		List<StudentInfoModel> list = new ArrayList<StudentInfoModel>();
		
		list = DBController.studentInfoPrint();
		
		for(StudentInfoModel data : list) {
			System.out.println(data);
		}
	}

	//3.�˻�(id�� �˻�)
	private static void studentInfoSearch() {
		int id = 0;
		
		//�˻��� �л� id
		id = studentInfoInsertWhile("�˻��� id");
		
		//DB���� ������ �����͸� ���� ��ü
		StudentInfoModel studentInfoModel = new StudentInfoModel();
		
		studentInfoModel = DBController.studentInfoSearch(id);
		
		if(studentInfoModel != null) {
			System.out.println(studentInfoModel);
		}else {
			System.out.println(id + "�� �˻��� ����� �����ϴ�.");
		}
	}

	//4.����(id�� ����)
	private static void studentInfoDelete() {
		int id = 0;
		//�����ƴ��� Ȯ���� return��
		int returnValue = 0;
		
		//������ �л� id
		id = studentInfoInsertWhile("������ id");
		
		returnValue = DBController.studentInfoDelete(id);
		
		if(returnValue != 0) {
			System.out.println(id + "�л��� �����Ǿ����ϴ�.");
		}else {
			System.out.println("���� �����߽��ϴ�.");
		}
	}

	//5.����(id�� �˻��� �л� ����, ����, ���, ��� ����)
	private static void studentInfoUpdate() {
		//������ �л����� �ʵ尪
		int id = 0;
		int c = 0;
		int java = 0;
		int kotlin = 0;
		int sum = 0;
		double avr = 0.0;
		char grade = '\u0000';
		//�����ƴ��� Ȯ���� return��
		int returnValue = 0;
		
		//������ �л� id
		id = studentInfoInsertWhile("������ id");
		
		//������ id�� ���� ����
		c = studentInfoInsertWhile("������ c");
		java = studentInfoInsertWhile("������ java");
		kotlin = studentInfoInsertWhile("������ kotlin");
		
		//������ id�� ����, ���, ��� ����
		sum = c + java + kotlin;
		avr = sum/(double)SUBJECT;
		grade = calGrade(avr);
		
		returnValue = DBController.studentInfoUpdate(id, c, java, kotlin, sum, avr, grade);
		
		if(returnValue != 0) {
			System.out.println(id + "�л��� �����Ǿ����ϴ�.");
		}else {
			System.out.println("���� �����߽��ϴ�.");
		}
		
	}

	//6.����(�������� or ��������)
	private static void studentInfoSort() {
		//studentInfoSort�Լ����� �޾ƿ� �����͸� ����� framework
		List<StudentInfoModel> list = new ArrayList<StudentInfoModel>();
		int no = 0;
		boolean flag = false;
		
		//���Ĺ�� : �̸���(��������, ��������)
		while(!flag) {
			System.out.println("1.��������(����), 2.��������(����)");
			System.out.print("���Ĺ�ļ��� >>");
			
			try {
				no = Integer.parseInt(scan.nextLine());				
			}catch(InputMismatchException e) {
				System.out.println("���: ���� �Է� ���ּ���!");
				continue;
			}catch(Exception e) {
				System.out.println("���: ���� �Է� ���ּ���!");
				continue;
			}
			
			if(no>=1 && no <=2) {
				flag = true;
			}else {
				System.out.println("���: (1 ~ 2) ���� �Է� ���ּ���!");
				continue;
			}
		}//end of while
		
		list = DBController.studentInfoSort(no);
		
		if(list.size() == 0) {
			System.out.println("������ ������ �����ϴ�");
			return;
		}
		
		for(StudentInfoModel data : list) {
			System.out.println(data);
		}
		
	}
	
	//�ʱ�޴� ����
	private static int selectMenu() {
		int menu = 0;
		boolean flag = false;
		
		while(!flag) {
			System.out.println(" ----------------------------------------");
			System.out.println("|1.�Է� 2.��� 3.�˻� 4.���� 5.���� 6.���� 7.����|");
			System.out.println(" ----------------------------------------");
			System.out.print("���� >>");
			
			try {
				menu = Integer.parseInt(scan.nextLine());				
			}catch(InputMismatchException e) {
				System.out.println("���: ���� �Է� ���ּ���!");
				continue;
			}catch(Exception e) {
				System.out.println("���: ���� �Է� ���ּ���!");
				continue;
			}
			
			if(menu >=1 && menu <=7 ) {
				flag = true;
			}else {
				System.out.println("1~7�� ���ڸ� �Է����ּ���");
				continue;
			}
		}
		
		return menu;
	}

}
