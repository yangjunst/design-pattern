package com.atguigu.builder.improve;

public class Client {
	public static void main(String[] args) {
		
		//����ͨ����
		CommonHouse commonHouse = new CommonHouse();
		//׼���������ӵ�ָ����
		HouseDirector houseDirector = new HouseDirector(commonHouse);
		
		//��ɸǷ��ӣ����ز�Ʒ(��ͨ����)
		House house = houseDirector.constructHouse();
		
		//System.out.println("�������");
		
		System.out.println("--------------------------"+house);
		//�Ǹ�¥
		HighBuilding highBuilding = new HighBuilding();
		//���ý�����
		houseDirector.setHouseBuilder(highBuilding);
		//��ɸǷ��ӣ����ز�Ʒ(��¥)
		House house1 = houseDirector.constructHouse();

		System.out.println(house1);


	}
}
