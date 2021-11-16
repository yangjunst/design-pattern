package com.maomao.learn.iterator;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/********************************************
 * ������Ա: ѩ������
 * ��ְʱ��: 2016/05/16
 * ����ʱ��: 2021/8/2 15:35
 *********************************************/
class Department{
    private String name;
    private String desc;

    public Department(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}

public class College {
    private List<Department> departmentLis;

    public College() {
        this.departmentLis = new ArrayList<>();
    }

    public void addDepartment(String name,String desc){
        departmentLis.add(new Department(name,desc));
    }

    private Iterator<Department> createIterator(){
        return new DepartmentIterator();
    }

    private class DepartmentIterator implements Iterator<Department>{

        private int index=0;

        @Override
        public boolean hasNext() {
            return index<departmentLis.size();
        }

        @Override
        public Department next() {
            return departmentLis.get(index++);
        }
    }

    public static void main(String[] args) {
        College college=new College();
        college.addDepartment("yangjun","jisuanji");
        college.addDepartment("maomao","computer");
        college.addDepartment("xyqz","java");
        Iterator<Department> iterator = college.createIterator();
        while (iterator.hasNext()){
            Department next = iterator.next();
            System.out.println(next);
        }
    }
}
