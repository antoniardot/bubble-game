package main;

import java.util.ArrayList;

public class BubbleList {

	ArrayList<Bubble> _bubbleList;
	
	public BubbleList(){
		_bubbleList = new ArrayList<Bubble>();
	}
	
	public synchronized boolean add(Bubble b) {
		boolean bool = _bubbleList.add(b);
		return bool;
	}
	
	public synchronized Bubble remove(int index){
		return _bubbleList.remove(index);
	}
	
	public synchronized int size() {
		return _bubbleList.size();
	}
	
	public synchronized Bubble get(int index){
		return _bubbleList.get(index);
	}
	
	public synchronized boolean isEmpty(){
		return _bubbleList.isEmpty();
	}
	
	public synchronized void clear(){
		_bubbleList.clear();
	}
}
