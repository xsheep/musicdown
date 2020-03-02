package com.xiaor.archetype.common.config;

public class LabelValueBean<K,V>{
	private K label;
	private V value;
	public LabelValueBean(K label, V value){
		this.label = label;
		this.value = value;
	}
	public void setLabel(K label){
		this.label = label;
	}
	public K getLabel(){
		return this.label;
	}
	public void setValue(V value){
		this.value = value;
	}
	public V getValue(){
		return this.value;
	}
}
