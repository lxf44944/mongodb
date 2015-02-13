package com.lxf.mongodb.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * MongoDB����
 * 
 * @author �����
 * 
 */
public class MongoDB {

	private MongoClient mongo = null;
	private DB db = null;
	private DBCollection students = null;

	/**
	 * ��ʼ����������ü��϶���
	 * 
	 * @param dbName
	 *            ���ݿ���
	 * @param collectionName
	 *            ������
	 */
	public MongoDB(String dbName, String collectionName) {
		super();
		try {
			mongo = new MongoClient();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db = mongo.getDB(dbName);
		students = db.getCollection(collectionName);
	}

	/**
	 * �ڶ�������ʱ�Զ��ر���Դ
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		this.close();
		super.finalize();
	}

	/**
	 * �ر���Դ
	 */
	public void close() {
		System.out.println("------------�ر���Դ---------------");
		// TODO Auto-generated method stub
		if (mongo != null) {
			mongo.close();
			mongo = null;
		}

		if (db != null) {
			// ����Mongo���ݿ����������
			db.requestDone();
			db = null;
		}
	}

	/**
	 * ɾ������
	 */
	public void delete() {
		System.out.println("------------ɾ��---------------");
		BasicDBObject object = new BasicDBObject();
		object.put("age", 40);
		object.put("name", "С��1");
		students.remove(object);

	}

	/**
	 * �޸�����
	 */
	public void update() {
		// TODO Auto-generated method stub
		System.out.println("------------�޸�---------------");
		BasicDBObject doc = new BasicDBObject();
		BasicDBObject res = new BasicDBObject();
		res.put("age", 40);
		System.out.println("�����ݼ��е������ĵ���age�޸ĳ�40��");
		doc.put("$set", res);
		students.update(new BasicDBObject(), doc, false, true);
	}

	/**
	 * ��������
	 */
	public void insert() {
		System.out.println("------------����---------------");
		List<DBObject> dbList = new ArrayList<DBObject>();
		BasicDBObject stu1 = null;

		for (int i = 0; i < 10; i++) {
			stu1 = new BasicDBObject();
			stu1.put("name", "С��" + (i + 1));
			stu1.put("age", 30 + i);
			stu1.put("id", 1 + i);
			dbList.add(stu1);
		}

		students.insert(dbList);
	}

	/**
	 * ��ӡ����
	 */
	public void printValues() {
		// System.out.println("------------��ӡ---------------");
		DBCursor cur = students.find();
		System.out.println(cur.count() + "����¼");
		while (cur.hasNext()) {
			BasicDBObject bdbObj = (BasicDBObject) cur.next();
			if (bdbObj != null) {
				System.out.print("id:" + bdbObj.getString("id") + "\t");
				System.out.print("name:" + bdbObj.getString("name") + "\t");
				System.out.print("age:" + bdbObj.getInt("age") + "\n");
			}
		}
	}
}
