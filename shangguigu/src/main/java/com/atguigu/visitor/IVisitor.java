package com.atguigu.visitor;

public abstract class IVisitor {
	
	//得到男性 的测评
	public abstract void visit(Man man);
	
	//得到女的 测评
	public abstract void visit(Woman woman);
}
