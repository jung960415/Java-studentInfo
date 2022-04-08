package com.mire.sixclass;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentInfoMain {
	//°ú¸ñ¼ö »ó¼ö
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
	
	//1.ÀÔ·Â
	private static void studentInfoInsert() {
		//ÀÔ·ÂÇÒ ÇĞ»ıÁ¤º¸ ÇÊµå°ª
		int id = 0;
		String name = null;
		int c = 0;
		int java = 0;
		int kotlin = 0;
		int sum = 0;
		double avr = 0;
		char grade = '\u0000';
		//ÀÔ·ÂµÆ´ÂÁö È®ÀÎÇÒ return°ª
		int returnValue = 0;
		
		//idÀÔ·Â
		id = studentInfoInsertWhile("id");
		
		//nameÀÔ·Â
		while(true) {
			System.out.print("ÀÌ¸§(2 ~ 5ÀÚ) ÀÔ·Â>>");
			name = scan.nextLine();
			
			Pattern pattern = Pattern.compile("^[°¡-ÆR]{2,5}$");
	        Matcher matcher = pattern.matcher(name);
	        
	        if(matcher.matches()) {
	        	break;
	        }else {
	        	System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
	        	continue;
	        }
		}//end of while(name)
		
		//¼ºÀûÀÔ·Â
		c = studentInfoInsertWhile("c");
		java = studentInfoInsertWhile("java");
		kotlin = studentInfoInsertWhile("kotlin");
		
		//ÀÔ·ÂÇÒ idÀÇ ÃÑÁ¡, Æò±Õ, µî±Ş
		sum = c + java + kotlin;
		avr = sum/(double)SUBJECT;
		grade = calGrade(avr);
		
		//DB¿¡ ÀÔ·ÂÇÒ ÇĞ»ıÁ¤º¸°´Ã¼
		StudentInfoModel studentInfoModel = new StudentInfoModel(id, name, c, java, kotlin, sum, avr, grade);
		
		returnValue = DBController.studentInfoInsert(studentInfoModel);
				
		if(returnValue != 0) {
			System.out.println("ÀÔ·Â ¼º°øÇÏ¿´½À´Ï´Ù.");
			System.out.println(studentInfoModel);
		}else {
			System.out.println("ÀÔ·Â ½ÇÆĞÇÏ¿´½À´Ï´Ù.");
		}
		
	}

	//1.1µî±Ş°è»ê
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
	
	//1.2ÇĞ»ıÁ¤º¸ÀÔ·Â(whileÁßº¹¹æÁö)
	private static int studentInfoInsertWhile(String string) {
		
		int value = 0;
		
		while(true) {
			System.out.print(string + "(1~100) ÀÔ·Â >>");
			try {
				value = Integer.parseInt(scan.nextLine());				
			}catch(InputMismatchException e) {
				System.out.println("°æ°í: ¼ıÀÚ ÀÔ·Â ÇØÁÖ¼¼¿ä!");
				continue;
			}catch(Exception e) {
				System.out.println("°æ°í: ¼ıÀÚ ÀÔ·Â ÇØÁÖ¼¼¿ä!");
				continue;
			}
			
			if(value >= 1 && value <= 100) {
				break;
			}else {
				System.out.println("1~100 ÀÔ·ÂÇØÁÖ¼¼¿ä");
				continue;
			}
		}//end of while
		
		return value;
	}
	
	//2.Ãâ·Â(DB Å×ÀÌºí ÀüÃ¼ ·¹ÄÚµå¼Â Ãâ·Â)
	private static void studentInfoPrint() {
		//DB¿¡¼­ ²¨³»¿Â µ¥ÀÌÅÍ¸¦ ¹ŞÀ» framework
		List<StudentInfoModel> list = new ArrayList<StudentInfoModel>();
		
		list = DBController.studentInfoPrint();
		
		for(StudentInfoModel data : list) {
			System.out.println(data);
		}
	}

	//3.°Ë»ö(id·Î °Ë»ö)
	private static void studentInfoSearch() {
		int id = 0;
		
		//°Ë»öÇÒ ÇĞ»ı id
		id = studentInfoInsertWhile("°Ë»öÇÒ id");
		
		//DB¿¡¼­ ²¨³»¿Â µ¥ÀÌÅÍ¸¦ ¹ŞÀ» °´Ã¼
		StudentInfoModel studentInfoModel = new StudentInfoModel();
		
		studentInfoModel = DBController.studentInfoSearch(id);
		
		if(studentInfoModel != null) {
			System.out.println(studentInfoModel);
		}else {
			System.out.println(id + "·Î °Ë»öÇÑ °á°ú´Â ¾ø½À´Ï´Ù.");
		}
	}

	//4.»èÁ¦(id·Î »èÁ¦)
	private static void studentInfoDelete() {
		int id = 0;
		//»èÁ¦µÆ´ÂÁö È®ÀÎÇÒ return°ª
		int returnValue = 0;
		
		//»èÁ¦ÇÒ ÇĞ»ı id
		id = studentInfoInsertWhile("»èÁ¦ÇÒ id");
		
		returnValue = DBController.studentInfoDelete(id);
		
		if(returnValue != 0) {
			System.out.println(id + "ÇĞ»ıÀÌ »èÁ¦µÇ¾ú½À´Ï´Ù.");
		}else {
			System.out.println("»èÁ¦ ½ÇÆĞÇß½À´Ï´Ù.");
		}
	}

	//5.¼öÁ¤(id·Î °Ë»öÇÑ ÇĞ»ı Á¡¼ö, ÃÑÁ¡, Æò±Õ, µî±Ş ¼öÁ¤)
	private static void studentInfoUpdate() {
		//¼öÁ¤ÇÒ ÇĞ»ıÁ¤º¸ ÇÊµå°ª
		int id = 0;
		int c = 0;
		int java = 0;
		int kotlin = 0;
		int sum = 0;
		double avr = 0.0;
		char grade = '\u0000';
		//¼öÁ¤µÆ´ÂÁö È®ÀÎÇÒ return°ª
		int returnValue = 0;
		
		//¼öÁ¤ÇÒ ÇĞ»ı id
		id = studentInfoInsertWhile("¼öÁ¤ÇÒ id");
		
		//¼öÁ¤ÇÒ idÀÇ ¼ºÀû ¼öÁ¤
		c = studentInfoInsertWhile("¼öÁ¤ÇÒ c");
		java = studentInfoInsertWhile("¼öÁ¤ÇÒ java");
		kotlin = studentInfoInsertWhile("¼öÁ¤ÇÒ kotlin");
		
		//¼öÁ¤ÇÒ idÀÇ ÃÑÁ¡, Æò±Õ, µî±Ş ¼öÁ¤
		sum = c + java + kotlin;
		avr = sum/(double)SUBJECT;
		grade = calGrade(avr);
		
		returnValue = DBController.studentInfoUpdate(id, c, java, kotlin, sum, avr, grade);
		
		if(returnValue != 0) {
			System.out.println(id + "ÇĞ»ıÀÌ ¼öÁ¤µÇ¾ú½À´Ï´Ù.");
		}else {
			System.out.println("¼öÁ¤ ½ÇÆĞÇß½À´Ï´Ù.");
		}
		
	}

	//6.Á¤·Ä(¿À¸§Â÷¼ø or ³»¸²Â÷¼ø)
	private static void studentInfoSort() {
		//studentInfoSortÇÔ¼ö¿¡¼­ ¹Ş¾Æ¿Â µ¥ÀÌÅÍ¸¦ Ãâ·ÂÇÒ framework
		List<StudentInfoModel> list = new ArrayList<StudentInfoModel>();
		int no = 0;
		boolean flag = false;
		
		//Á¤·Ä¹æ½Ä : ÀÌ¸§¼ø(¿À¸§Â÷¼ø, ³»¸²Â÷¼ø)
		while(!flag) {
			System.out.println("1.¿À¸§Â÷¼ø(ÃÑÁ¡), 2.³»¸²Â÷¼ø(ÃÑÁ¡)");
			System.out.print("Á¤·Ä¹æ½Ä¼±ÅÃ >>");
			
			try {
				no = Integer.parseInt(scan.nextLine());				
			}catch(InputMismatchException e) {
				System.out.println("°æ°í: ¼ıÀÚ ÀÔ·Â ÇØÁÖ¼¼¿ä!");
				continue;
			}catch(Exception e) {
				System.out.println("°æ°í: ¼ıÀÚ ÀÔ·Â ÇØÁÖ¼¼¿ä!");
				continue;
			}
			
			if(no>=1 && no <=2) {
				flag = true;
			}else {
				System.out.println("°æ°í: (1 ~ 2) ¼ıÀÚ ÀÔ·Â ÇØÁÖ¼¼¿ä!");
				continue;
			}
		}//end of while
		
		list = DBController.studentInfoSort(no);
		
		if(list.size() == 0) {
			System.out.println("Á¤·ÄÇÒ ³»¿ëÀÌ ¾ø½À´Ï´Ù");
			return;
		}
		
		for(StudentInfoModel data : list) {
			System.out.println(data);
		}
		
	}
	
	//ÃÊ±â¸Ş´º ¼±ÅÃ
	private static int selectMenu() {
		int menu = 0;
		boolean flag = false;
		
		while(!flag) {
			System.out.println(" ----------------------------------------");
			System.out.println("|1.ÀÔ·Â 2.Ãâ·Â 3.°Ë»ö 4.»èÁ¦ 5.¼öÁ¤ 6.Á¤·Ä 7.Á¾·á|");
			System.out.println(" ----------------------------------------");
			System.out.print("¼±ÅÃ >>");
			
			try {
				menu = Integer.parseInt(scan.nextLine());				
			}catch(InputMismatchException e) {
				System.out.println("°æ°í: ¼ıÀÚ ÀÔ·Â ÇØÁÖ¼¼¿ä!");
				continue;
			}catch(Exception e) {
				System.out.println("°æ°í: ¼ıÀÚ ÀÔ·Â ÇØÁÖ¼¼¿ä!");
				continue;
			}
			
			if(menu >=1 && menu <=7 ) {
				flag = true;
			}else {
				System.out.println("1~7ÀÇ ¼ıÀÚ¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä");
				continue;
			}
		}
		
		return menu;
	}

}
