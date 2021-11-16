package com.atguigu.visitor;

public class Man extends Person {

	@Override
	public void accept(IVisitor action) {
		// TODO Auto-generated method stub
		action.visit(this);
	}

}
