package com.atguigu.visitor;

public class Success extends IVisitor {

	@Override
	public void visit(Man man) {
		// TODO Auto-generated method stub
		System.out.println(" 男人给的评价该歌手很成功 !");
	}

	@Override
	public void visit(Woman woman) {
		// TODO Auto-generated method stub
		System.out.println(" 女人给的评价该歌手很成功 !");
	}

}
