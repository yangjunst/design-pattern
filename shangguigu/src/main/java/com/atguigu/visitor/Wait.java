package com.atguigu.visitor;

public class Wait extends IVisitor {

	@Override
	public void visit(Man man) {
		// TODO Auto-generated method stub
		System.out.println(" ���˸��������Ǹø��ִ��� ..");
	}

	@Override
	public void visit(Woman woman) {
		// TODO Auto-generated method stub
		System.out.println(" Ů�˸��������Ǹø��ִ��� ..");
	}

}
