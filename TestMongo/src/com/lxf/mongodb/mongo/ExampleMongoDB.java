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
import com.mongodb.MongoException;

public class ExampleMongoDB {

	/**
	 * java mongodb�����ݲ��롢��ȡ�����¡�ɾ��
	 */

	private static Mongo m = null;
	private static DB db = null;

	// ���ݼ�������
	private static final String COLLECTION_NAME = "mcpang";

	/*
	 * ����java����mongodb������ɾ���ġ������
	 */
	public static void main(String[] args) {
		// ��ȡ���ݿ�����
		startMongoDBConn();
		// ��������
		createColData();
		// ��ȡ����
		readColData();
		// ��������
		updateColData();
		// ��ȡ����
		readColData();
		// ɾ������
		deleteColData();
		// ��ȡ����
		readColData();
		// ɾ�����ݼ�
		db.getCollection(COLLECTION_NAME).drop();
		// �ر����ݿ�����
		stopMondoDBConn();

	}

	/**
	 * ���ݲ��� �������ݣ� ��name:С�age:30��address:������ ��name:С�š�age:25��address:���
	 * 
	 * @return
	 */
	private static void createColData() {
		DBCollection dbCol = db.getCollection(COLLECTION_NAME);
		System.out.println("�����ݼ��в������ݿ�ʼ��");
		List<DBObject> dbList = new ArrayList<DBObject>();
		BasicDBObject doc1 = new BasicDBObject();
		doc1.put("name", "С��");
		doc1.put("age", 30);
		doc1.put("address", "����");
		dbList.add(doc1);

		BasicDBObject doc2 = new BasicDBObject();
		doc2.put("name", "С��");
		doc2.put("age", 25);
		doc2.put("address", "���");
		dbList.add(doc2);

		dbCol.insert(dbList);
		System.out.println("�����ݼ��в���������ɣ�");
		System.out.println("------------------------------");
	}

	/**
	 * ���ݶ�ȡ
	 */
	private static void readColData() {
		DBCollection dbCol = db.getCollection(COLLECTION_NAME);
		DBCursor ret = dbCol.find();
		System.out.println("�����ݼ��ж�ȡ���ݣ�");
		while (ret.hasNext()) {
			BasicDBObject bdbObj = (BasicDBObject) ret.next();
			if (bdbObj != null) {
				System.out.println("name:" + bdbObj.getString("name"));
				System.out.println("age:" + bdbObj.getInt("age"));
				System.out.println("address:" + bdbObj.getString("address"));
			}
		}
	}

	/**
	 * ���ݸ��� update(q, o, upsert, multi) update(q, o, upsert, multi, concern)
	 * update(arg0, arg1, arg2, arg3, arg4, arg5) updateMulti(q, o)
	 */
	private static void updateColData() {
		System.out.println("------------------------------");
		DBCollection dbCol = db.getCollection(COLLECTION_NAME);
		DBCursor ret = dbCol.find();
		BasicDBObject doc = new BasicDBObject();
		BasicDBObject res = new BasicDBObject();
		res.put("age", 40);
		System.out.println("�����ݼ��е������ĵ���age�޸ĳ�40��");
		doc.put("$set", res);
		dbCol.update(new BasicDBObject(), doc, false, true);
		System.out.println("����������ɣ�");
		System.out.println("------------------------------");
	}

	/**
	 * ����ɾ��
	 */
	private static void deleteColData() {
		System.out.println("------------------------------");
		DBCollection dbCol = db.getCollection(COLLECTION_NAME);
		System.out.println("ɾ����С���");
		BasicDBObject doc = new BasicDBObject();
		doc.put("name", "С��");
		dbCol.remove(doc);
		System.out.println("------------------------------");
	}

	/**
	 * �ر�mongodb���ݿ�����
	 */
	private static void stopMondoDBConn() {
		if (null != m) {
			if (null != db) {
				// ����Mongo���ݿ����������
				try {
					db.requestDone();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				m.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			m = null;
			db = null;
		}
	}

	/**
	 * ��ȡmongodb���ݿ�����
	 */
	private static void startMongoDBConn() {
		try {
			// Mongo(p1, p2):p1=>IP��ַ p2=>�˿�
			m = new Mongo("127.0.0.1", 27017);
			// ����mongodb���ݿ�����ƻ�ȡmongodb����
			db = m.getDB("yyl");
			// У���û������Ƿ���ȷ
			if (!db.authenticate("yyl", "yyl123".toCharArray())) {
				System.out.println("����MongoDB���ݿ�,У��ʧ�ܣ�");
			} else {
				System.out.println("����MongoDB���ݿ�,У��ɹ���");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
}
