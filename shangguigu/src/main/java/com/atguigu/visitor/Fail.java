package com.atguigu.visitor;

public class Fail extends IVisitor {

	@Override
	public void visit(Man man) {
		// TODO Auto-generated method stub
		System.out.println(" 男人给的评价该歌手失败 !");
	}

	@Override
	public void visit(Woman woman) {
		// TODO Auto-generated method stub
		System.out.println(" 女人给的评价该歌手失败 !");
	}

}
