package main;
import processing.core.*;
import tsps.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MainSketch extends PApplet {

	// declare global variables
	TSPS tspsReceiver;
	OSCHandler receiver;
	
	Collection<TspsPerson> people;
	
	int numberOfPeople;
	Bubble defaultBubble;

	Blob[] blobs = new Blob[50];
	
	/***
	 * this method is called once in the beginning to initialize
	 * 
	 */
	  public void setup() {
//	    size(1200,800);
	    size(560,400);
	    background(255,255,255);
	    
	    // initialize Receiver
//	    tspsReceiver= new TSPS(this);
	    tspsReceiver= null;
	    receiver = new OSCHandler(this);
	    int x = receiver.getNumPeople();
	    println("Number of people at beginning: " + x);
	    
	    
	    //intialize Blobs
	    for (int i = 0; i < blobs.length; i++) {
	        blobs[i] = new Blob(this);
	      }
	    
	    
	    noFill();
		strokeWeight(4);
		stroke(100,100, 100);
	    defaultBubble = new Bubble(this, 0.4f , 0.5f);
	    defaultBubble.display();
	  }

	  
	  
	  
	  
	  /***
	   * this method is called repeatedly, in a loop
	   * 
	   */
	  public void draw() {
		  // call noloop() when wanting to stop the draw-loop, back is loop()
	    background(0,0,0); //make everything blank to render it
	    noFill();
		strokeWeight(4);
		stroke(255,255, 255);
	    
	    
	    //draw blobs
	    for (int i = 0; i < blobs.length; i++) {
	      blobs[i].move();
	      blobs[i].display();
	    }
	    
	    //draw the player's Bubbles
	    drawBubbles();
	    
	    
	    
	    // do other stuff
	    noFill();
		strokeWeight(4);
		stroke(100,100, 100, 200);
	    defaultBubble.display();
	  }
	  
	  
	  //update position + color of the bubbles
	  public void drawBubbles() {
		  Bubble b;
		    people = receiver.getPeopleList();
		    for (TspsPerson p: people) {
		    	
		    	b = p._bubble;
		    	
		    	//check if bubble is colliding with other bubble
		    	if(b.collidesWith(defaultBubble)){
		    		fill(255,0,0);
		    	}
		    	else {
		    		fill(0, 0, 0);
		    	}
		    	b.display();
		    }
	  }
	  
	  
	  
	  
}
