package com.atguigu.visitor;

public class Success extends IVisitor {

	@Override
	public void visit(Man man) {
		// TODO Auto-generated method stub
		System.out.println(" ���˸������۸ø��ֺܳɹ� !");
	}

	@Override
	public void visit(Woman woman) {
		// TODO Auto-generated method stub
		System.out.println(" Ů�˸������۸ø��ֺܳɹ� !");
	}

}
