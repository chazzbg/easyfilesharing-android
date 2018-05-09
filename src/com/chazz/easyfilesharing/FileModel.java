/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chazz.easyfilesharing;

import java.io.Serializable;

/**
 *
 * @author chazz
 */
public  class  FileModel implements Serializable {
	String name;
	long  size;

	public FileModel() {
	}

	public FileModel(String name, long size) {
		this.name = name;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "FileModel{" + "name=" + name + ", size=" + size + '}';
	}
	
	
}
