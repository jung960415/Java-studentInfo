package com.mire.sixclass;

import java.util.Objects;

public class StudentInfoModel implements Comparable{
	//필드
	private int id; 		//학번
	private String name; 	//이름
	private int c; 			//c언어성적
	private int java;		//java성적
	private int kotlin;		//kotlin성적
	private int sum;		//성적 총합
	private double avr;		//평균
	private char grade;		//등급
	
	//생성자
	public StudentInfoModel() {
		this(0, null, 0, 0, 0, 0, 0.0, '\u0000');
	}
	
	public StudentInfoModel(int id, String name, int c, int java, int kotlin, int sum, double avr, char grade) {
		super();
		this.id = id;
		this.name = name;
		this.c = c;
		this.java = java;
		this.kotlin = kotlin;
		this.sum = sum;
		this.avr = avr;
		this.grade = grade;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getJava() {
		return java;
	}

	public void setJava(int java) {
		this.java = java;
	}

	public int getKotlin() {
		return kotlin;
	}

	public void setKotlin(int kotlin) {
		this.kotlin = kotlin;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public double getAvr() {
		return avr;
	}

	public void setAvr(double avr) {
		this.avr = avr;
	}

	public char getGrade() {
		return grade;
	}

	public void setGrade(char grade) {
		this.grade = grade;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof StudentInfoModel)) {
			throw new IllegalArgumentException("Not Found Instance");
		}
		
		StudentInfoModel data = (StudentInfoModel)obj;
		
		return (this.id == data.id);
	}

	@Override
	public int compareTo(Object obj) {
		if(!(obj instanceof StudentInfoModel)) {
			throw new IllegalArgumentException("Not Found Instance");
		}
		
		StudentInfoModel data = (StudentInfoModel)obj;
		
		if(this.id > data.id) {
			return -1;
		}else if(this.id < data.id){
			return 1;
		}else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return id + "\t" + name + "\t" + c + "\t" + java + "\t" + kotlin
				+ "\t" + sum + "\t" + String.format("%6.2f", avr) + "\t" + grade;
	}
	
}
