package com.atguigu.flyweight;

import java.util.HashMap;

// ��վ�����࣬������Ҫ����ѹһ����վ
public class WebSiteFactory {

	public static void main(String[] args) {
		Integer a=111;
		Integer b=111;
		Integer c=333;
		Integer d=333;
		System.out.println(a==b);
		System.out.println(c==d);
	}

	
	//���ϣ� �䵱�ص�����
	private HashMap<String, ConcreteWebSite> pool = new HashMap<>();
	
	//������վ�����ͣ�����һ����վ, ���û�оʹ���һ����վ�������뵽����,������
	public WebSite getWebSiteCategory(String type) {
		if(!pool.containsKey(type)) {
			//�ʹ���һ����վ�������뵽����
			pool.put(type, new ConcreteWebSite(type));
		}
		
		return (WebSite)pool.get(type);
	}
	
	//��ȡ��վ��������� (�����ж��ٸ���վ����)
	public int getWebSiteCount() {
		return pool.size();
	}
}
