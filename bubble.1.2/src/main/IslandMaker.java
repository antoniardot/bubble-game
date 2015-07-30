package main;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;

public class IslandMaker  {
	
	
	
	PApplet _p;
	Island[] _islands;
	int numberOfIslands = 2;
	
	int poppedIslands = 0;
	ColorScheme _scheme;
	
	boolean allIslandsConquered = false;
	
	public IslandMaker(PApplet parent, ColorScheme s) {
		
		_p = parent;
		_islands = new Island[numberOfIslands];
		_scheme = s;
		//create the islands
		createNewIslands();
		
	}
	
	/**
	 * creates a set of new islands, depending on set number of islands
	 */
	public void createNewIslands(){
		_islands = new Island[numberOfIslands];
		
		for(int i = 0; i < numberOfIslands; i++) {
			float r = _p.random(Bubble.MIN_RADIUS_BUBBLE,Bubble.MAX_RADIUS_BUBBLE);
			float x = _p.random(r,_p.width -r); //position island x so it doesnt overlap gameframe
			float y = _p.random(r,_p.height -r); //position island y so it doesnt overlap gameframe
			Island island = new Island(_p, x, y, r, _scheme);
			
			//check if it overlaps with other islands, if so make a new one
			int j = i;
			boolean addIsland = true;
			for(j = i-1; j > -1; j--) {
				if(_islands[j].collidesWith(island)){
					i--;
					addIsland = false;
				}
			}
			
			if(addIsland){
				_islands[i] = island;
			}
		}
		allIslandsConquered = false;
	}
	
	
	public void display(){
		for(int i = 0; i < _islands.length ; i++){
			_islands[i].display();
		}
	}

	
	/**
	 * This method checks, if all existing islands are being conquered
	 * @param bubbles List of players bubbles
	 */
	public void checkOverlapping(BubbleList bubbles) {
		
			int conqueredIslands = 0;
		
			for(int i = 0; i < _islands.length ; i++){
				for(int j =0; j < bubbles.size(); j++) {
					//if the bubble touches the island
					if(!_islands[i].isPopped && _islands[i].collidesWith(bubbles.get(j))) {
						_islands[i].setIsOverlapped(true);
						
						//if bubble fits island
						if(_islands[i].bubbleFitsOnIsland(bubbles.get(j))){
//							_islands[i].setPopped();
							conqueredIslands++;
						}
						
						break; //because if one bubble touches island, no need to check for the other
					}
					else {
						_islands[i].setIsOverlapped(false);
					}
				}
			}
			
			
			//if all islands are conquered, task is complete
			if(conqueredIslands == numberOfIslands) {
				allIslandsConquered = true;
			}
		
	}
	
	public boolean allIslandsPopped(){
//		for (int i = 0; i < _islands.length ; i++){
//			
//			//if one island is not popped, return false
//			if(!_islands[i].isPopped){
//				return false;
//			}
//		}
//		return true;
		return allIslandsConquered;
	}

}
