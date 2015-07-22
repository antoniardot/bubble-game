package main;
import processing.core.*;
import tsps.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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
	
	//elements of the game
	Collection<TspsPerson> _people; //the players, should only be two
	
	static int _numberBlobs = 50;
	ArrayList<Blob> _blobs;
	ArrayList<MovableObject> _allMovableObjects;
	ArrayList<Bubble> _bubbles;
	
	Bubble defaultBubble;
	PImage img;

	
	
	
	
	/***
	 * this method is called once in the beginning to initialize
	 * 
	 */
	  public void setup() {

		//initialize window
		//size(1200,800);
	    size(580,420);
	    background(0,0,0);
	    img = loadImage("/Users/Bruno/projects/IGITTIGIT/bubble.1.2/img/forest.jpg");
	    
	    // initialize Receiver
	    tspsReceiver= null;
	    receiver = new OSCHandler(this);
//	    int x = receiver.getNumPeople();
//	    println("Number of people at beginning: " + x);
	    
	    
	    
	    //initialize elements
	    _allMovableObjects = new ArrayList<MovableObject>(); //this list can be configured by the OSCHandler! bad solution
	    _blobs = new ArrayList<Blob>();
	    //intialize Blobs
	    for (int i = 0; i < _numberBlobs; i++) {
	    	Blob b = new Blob(this, i);
	        _blobs.add(b);
	        _allMovableObjects.add(_blobs.get(i));
	      }
	    
	    
	    //the list of Bubbles
	    _bubbles = new ArrayList<Bubble>();
	    
	    //only for testing
	    defaultBubble = new Bubble(this, 0.4f , 0.5f);
	    _bubbles.add(defaultBubble);
	    defaultBubble.display();
	    
	    //set the state
	    currentState = STATE_FLYINGBLOBS_QUEST;
	    
	  }

	  
	  
	  
	  
	  /***
	   * this method is called repeatedly, in a loop
	   * 
	   */
	  public void draw() {
		  // call noloop() when wanting to stop the draw-loop, back is loop()
		  
		  //get the info of the players
//		  _people = new ArrayList<TspsPerson>(receiver.getPeopleList());
		  _bubbles = new ArrayList<Bubble>(receiver.getBubbleList());
		  _bubbles.add(defaultBubble);
		  
		  
		  
		  //get the info of the controller
		  // ...jaja kommt ja noch
		  
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
	    	fill(200,200,200);
	    	ellipse(mouseX,mouseY,10,10);
	    	defaultBubble.updateOrignialCoordinates(mouseX, mouseY);;
	    }
//	    defaultBubble.display();
	  
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
//			image(img,0,0);
		    
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
		      for(Bubble b: _bubbles){
		    	  if (_blobs.get(i).collidesWith(b)){
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
	  
	  private void loopIslandState(){
		  fill(200,200,200);
		  ellipse(5,5,10,10);
	  }
	  
	  
	  private void loopCorridorState(){
		  
	  }
	  
	  
	  //update position + color of the player-bubbles
	  private void drawBubbles() {
		    
		    for (Bubble b: _bubbles) {
		    	
		    	
		    	//b.bounceOff(_allMovableObjects);
		    	
		    	//check if bubble is colliding with other bubble
//		    	if(b.collidesWith(defaultBubble)){
//		    		fill(255,0,0);
//		    	}
//		    	else {
//		    		noFill();
//		    	}
		    	b.display();
		    }
	  }
	  
	  
	  
	  
}
