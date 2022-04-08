package com.mire.sixclass;

import java.util.Objects;

public class StudentInfoModel implements Comparable{
	//�ʵ�
	private int id; 		//�й�
	private String name; 	//�̸�
	private int c; 			//c����
	private int java;		//java����
	private int kotlin;		//kotlin����
	private int sum;		//���� ����
	private double avr;		//���
	private char grade;		//���
	
	//������
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
