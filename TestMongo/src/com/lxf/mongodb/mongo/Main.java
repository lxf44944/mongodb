package com.lxf.mongodb.mongo;

/**
 * ����MongoDB��
 * @author �����
 *
 */
public class Main {

	public static void main(String[] args) {
		//���ݿ�Ϊlxf������Ϊstudents
		MongoDB mongoDB = new MongoDB("lxf", "students");
		mongoDB.printValues();
		mongoDB.insert();
		mongoDB.printValues();
		mongoDB.update();
		mongoDB.printValues();
		mongoDB.delete();
		mongoDB.printValues();
		mongoDB.close();
	}
}
