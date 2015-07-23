package main;
import processing.core.*;
import tsps.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import old.TspsPerson;

public class MainSketch extends PApplet {

	// declare global variables
	TSPS tspsReceiver;
	OSCHandler receiver;
	
	
	//game states
	final static byte STATE_STARTGAME = 0;
	final static byte STATE_FLYINGBLOBS_QUEST = 1;
	final static byte STATE_ISLAND_QUEST = 2;
	final static byte STATE_CORRIDOR_QUEST = 3;
	
	byte currentState;
	
	
	PImage img;
	PImage img2;

	//elements of the game
	Collection<TspsPerson> _people; //the players, should only be two
	
	ArrayList<MovableObject> _allMovableObjects;

	static int _numberBlobs = 20;
	ArrayList<Blob> _blobs;
	
	static int _numberIslandSteps = 10;
	IslandMaker _islandMaker;
	
	
	BubbleList _bubs;
	
	//FOR TESTING
	Bubble defaultBubble1;
	Bubble defaultBubble2;
	MouseWheelEventListener wheelListener;
	
	

	
	
	
	
	/***
	 * this method is called once in the beginning to initialize
	 * 
	 */
	  public void setup() {

		//initialize window
		size(1000,700);
//	    size(580,420);
	    background(0,0,0);
	    img = loadImage("/Users/Bruno/projects/IGITTIGIT/bubble.1.2/img/forest_bg.png");
	    img2 = loadImage("/Users/Bruno/projects/IGITTIGIT/bubble.1.2/img/forest2.png");
	    
	    //initialize elements
	    _allMovableObjects = new ArrayList<MovableObject>(); //this list can be configured by the OSCHandler! bad solution

	    //intialize Blobs
	    _blobs = new ArrayList<Blob>();
	    for (int i = 0; i < _numberBlobs; i++) {
	    	Blob b = new Blob(this, i);
	        _blobs.add(b);
	        _allMovableObjects.add(_blobs.get(i));
	      }
	    
	    //intialise islandMaker
	    _islandMaker = new IslandMaker(this);
	    
	    //the list of Bubbles
	    _bubs = new BubbleList();
	    
	    //only for testing
	    defaultBubble1 = new Bubble(this, 0, 0, 0.4f , 0.5f);
	    defaultBubble2 = new Bubble(this,0 ,0, 0.7f, 0.8f);
	    _bubs.add(defaultBubble1);
	    _bubs.add(defaultBubble2);
	    
	    
	    //set the state
	    currentState = STATE_FLYINGBLOBS_QUEST;
//	    currentState = STATE_ISLAND_QUEST;
	    
	    
	    
	    // initialize Receiver and other listeners
	    tspsReceiver= null;
	    receiver = new OSCHandler(this, _bubs);
//	    int x = receiver.getNumPeople();
//	    println("Number of people at beginning: " + x);
	    wheelListener = new MouseWheelEventListener(this,_bubs);
	    
	  }

	  
	  
	  
	  
	  /***
	   * this method is called repeatedly, in a loop
	   * 
	   */
	  public void draw() {
		  // call noloop() when wanting to stop the draw-loop, back is loop()
		  
		  //get the info of the players
//		  _people = new ArrayList<TspsPerson>(receiver.getPeopleList());
//		  _bubbles = new ArrayList<Bubble>(receiver.getBubbleList());
//		  HandleControllerInput(_bubbles);
//		  _bubbles.add(defaultBubble1);
//		  _bubbles.add(defaultBubble2);
		  
		  
		  
		  switch(currentState) {
		  case STATE_STARTGAME:
			  loopStartState();
			  break;
		  case STATE_FLYINGBLOBS_QUEST:
			  loopBlobsState();
			  break;
		  case STATE_ISLAND_QUEST:
			  loopIslandState();
			  break;
		  case STATE_CORRIDOR_QUEST:
			  loopCorridorState();
			  break;
			  
		  default:
			  println("unknown state in MainSketch.draw()");
			  exit();
			  break;
		  }
		  
	    
//	    while (mousePressed != true) {
//		    try {
//				Thread.sleep(50);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	    }
	   
	    if(mousePressed) {
//	    	fill(200,200,200);
//	    	ellipse(mouseX,mouseY,10,10);
	    	defaultBubble1.updateOrignialCoordinates(mouseX, mouseY);;
	    }
	    if(keyPressed == true && key == CODED) {
	    	if(keyCode == RIGHT){
	    		defaultBubble2._centroid.x += 3; 
	    	}
	    	else if (keyCode == LEFT) {
	    		defaultBubble2._centroid.x -= 3; 
	    		
	    	}
	    	else if (keyCode == UP) {
	    		defaultBubble2._centroid.y -= 3; 
	    	}
	    	else if (keyCode == DOWN) {
	    		defaultBubble2._centroid.y += 3; 
	    		
	    	}
	    }
	    
	  
	  }//end draw loop
	  
	  
	  
	  private void loopStartState(){
		  
	  }
	  
	  
	  
	  
	  
	  
	  /**
	   * 
	   * 
	   * draws the quest with the flying blobs
	   */
	  private void loopBlobsState(){
		  background(255,255,255); //make everything blank to render it
			image(img,0,0);
		    
		  //if all the blobs have been popped change the state  
		  if(_blobs.isEmpty()){
			  currentState = STATE_ISLAND_QUEST;
		  }
		  
		  else {
			  
		  
		    //draw the blobs
		    for (int i = 0; i < _blobs.size(); i++) {
		      _blobs.get(i).move();
		      
		      boolean popped = false;
		      //handle collision with bubbles
//		      for(Bubble b: _bubbles){
//		    	  if (_blobs.get(i).collidesWith(b)){
//						//if they collide with a bubble, let them pop
//							_blobs.get(i).pop();
//							_blobs.remove(i);
//							popped = true;
//							break;
//					}  
//		      }
		      
		      
		      for(int j =0; j < _bubs.size(); j++){
		    	  if (_blobs.get(i).collidesWith(_bubs.get(j))){
						//if they collide with a bubble, let them pop
							_blobs.get(i).pop();
							_blobs.remove(i);
							popped = true;
							break;
					}  
		      }
		      
		      //if the blob hasn't been popped
		      if (!popped) {
			      //handle collision with other objects
			      for(int j = 1 + _blobs.get(i)._index; j < _allMovableObjects.size(); j++ ) {
					
			    	  //if they collide with another object
			    	  if (_blobs.get(i).collidesWith(_allMovableObjects.get(j))){
							_blobs.get(i).bounceOff(_allMovableObjects.get(j));
							_blobs.get(i).display();
			    	  }
			    	  
			    	  _blobs.get(i).display();
			      }
		      }
		      
		    }//end blob forloop
		    
		    //draw the player's Bubbles
		    noFill();
			strokeWeight(4);
			stroke(0,0, 0);
		    drawBubbles();
		  }
	  } //end loop
	  
	  
	  /***
	   * 
	   */
	  private void loopIslandState(){
		  background(255,255,255); //make everything blank to render it
		  image(img2,0,0);
		  _islandMaker.checkOverlapping(_bubs);
		  _islandMaker.display();
		  drawBubbles();
	  }
	  
	  
	  private void loopCorridorState(){
		  
	  }
	  
	  
	  //update position + color of the player-bubbles
	  private void drawBubbles() {
		    
//		    for (Bubble b: _bubbles) {
//		    	
//		    	
//		    	//b.bounceOff(_allMovableObjects);
//		    	
//		    	//check if bubble is colliding with other bubble
////		    	if(b.collidesWith(defaultBubble)){
////		    		fill(255,0,0);
////		    	}
////		    	else {
////		    		noFill();
////		    	}
//		    	b.display();
//		    }
		  for (int i = 0; i < _bubs.size(); i++) {
		    	
		    	
		    	//b.bounceOff(_allMovableObjects);
		    	
		    	//check if bubble is colliding with other bubble
//		    	if(b.collidesWith(defaultBubble)){
//		    		fill(255,0,0);
//		    	}
//		    	else {
//		    		noFill();
//		    	}
			  
			  _bubs.get(i).display();
		    }
	  }
	  
	  
	  private void HandleControllerInput(ArrayList<Bubble> bubbles) {
		  int counter = 0;
		  
		  for(Bubble b: bubbles){
			if(counter < 2) {

				//do the controller-stuff to the bubbles
				
				counter++;
			}
			else {
				break;
			}
			
			
		  }
	  }
	  
	  
	  
	  
}
