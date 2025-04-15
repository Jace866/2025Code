package com.easy.util;

public class SequenceBean {
	public String name;
	public int current;
	public int max;
	
	public SequenceBean(String name, int current, int max){
		this.name=name;
		this.current=current;
		this.max=max;
	}

	public synchronized int getCurrent() {
		current++;
		return current;
	}

	@Override
	public String toString() {
		return "SequenceBean[name="+name+", current="+current+", max="+max+"]";
	}
	
}
