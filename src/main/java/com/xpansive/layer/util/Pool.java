package com.xpansive.layer.util;

import java.util.ArrayList;
import java.util.List;

public class Pool<T> {
	public interface PoolObjectFactory<T> {
		T createObject();
	}

	private final List<T> freeObjects;
	private final PoolObjectFactory<T> factory;

	public Pool(PoolObjectFactory<T> factory) {
		this.factory = factory;
		this.freeObjects = new ArrayList<T>();
	}

	public void free(T object) {
		//System.out.println("Freed object. FREE:" + freeObjects.size() + " USED:" + usedobj);
		freeObjects.add(object);
		usedobj--;
	}
int usedobj;
	public T newObject() {
		T object;
		usedobj++;
		if (freeObjects.isEmpty()) {
			object = factory.createObject();
		} else {
			object = freeObjects.remove(freeObjects.size() - 1);
		}

		return object;
	}
}
