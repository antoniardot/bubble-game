package main;
import processing.core.*;
import ddf.*;
import tsps.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import old.TspsPerson;

public class MainSketch extends PApplet {

	// declare global variables
	
	
	//for input
	private OSCHandler receiver;
	private MouseWheelEventListener wheelListener;
	
	//LOGIC: game states
	final static byte STATE_INTRO = 0;
	final static byte STATE_STARTGAME = 1;
	final static byte STATE_FLYINGBLOBS_QUEST = 2;
	final static byte STATE_ISLAND_QUEST = 3;
	final static byte STATE_CORRIDOR_QUEST = 4;
	final static byte STATE_LOADING = 5;
	final static byte STATE_CHARGE_ROBOT = 6;
	
	private byte _currentState;
	private byte _nextState; //this is set for the LoadingState
	private int _repeatCounter; //current number of repeats per quest
	private int _numberOfRepeats; //number of total repeats per quest
	private int _frameCounter;
	
	//VISUALS
	
	final static int WIDTH = 660;
	final static int HEIGHT = 460;
	
	private PImage startBGimg;
	
	private ColorScheme colorsA;
	private ColorScheme colorsB;
	
	private ColorScheme _currentColorScheme;
	
	//elements of the game
//	Collection<TspsPerson> _people; //the players, should only be two
	
	private ArrayList<MovableObject> _allMovableObjects;

	private static int _numberBlobs = 20;
	private ArrayList<Blob> _blobs;
	
	private static int _numberIslandSteps = 10;
	private IslandMaker _islandMaker;
	
	private Corridor _corridor;
	
	private WallE wallE;
	
	//for the bubbles
	private BubbleList _bubbles;
	private int _overlapCounter;
	private int _overlapTimer; //counts Frames of overlapping Bubbles
	
	
	
	
	//AUDIOS
	Sound sound_beep = new Sound("/Users/Bruno/projects/IGITTIGIT/bubble.1.2/sounds/beeping.wav");
	Sound sound_spark = new Sound("/Users/Bruno/projects/IGITTIGIT/bubble.1.2/sounds/sparks.wav");
	Sound sound_blob = new Sound("/Users/Bruno/projects/IGITTIGIT/bubble.1.2/sounds/blob.wav");
	Sound sound_achievement = new Sound("/Users/Bruno/projects/IGITTIGIT/bubble.1.2/sounds/achievement.wav");
	Sound sound_shutDown = new Sound("/Users/Bruno/projects/IGITTIGIT/bubble.1.2/sounds/shutDown.wav");
	Sound sound_fail = new Sound("/Users/Bruno/projects/IGITTIGIT/bubble.1.2/sounds/fail.wav");
	
	
	//FOR TESTING
	private Bubble defaultBubble1;
	private Bubble defaultBubble2;
	
	

	
	
	
	
	/***
	 * this method is called once in the beginning to initialize
	 * 
	 */
	  public void setup() {

		//initialize window
//		size(1000,700);
	    size(WIDTH,HEIGHT);
	    background(0,0,0);
	    
//	    startBGimg = loadImage("/Users/Bruno/projects/IGITTIGIT/bubble.1.2/img/neutral.png");

	    colorsA = new ColorScheme(this, true); //the colorscheme can be configured by the players, later on
	    colorsB = new ColorScheme(this, false); //the colorscheme can be configured by the players, later on
	    _currentColorScheme = new ColorScheme(this,false); //in case the the start-state is jumped
	    
	    //initialize elements
	    _allMovableObjects = new ArrayList<MovableObject>(); //this list can be configured by the OSCHandler! bad solution

	    
	    //initialize the intro
	    wallE = new WallE(this);
	    
	    //intialize Blobs
	    _blobs = new ArrayList<Blob>();
	    for (int i = 0; i < _numberBlobs; i++) {
	    	Blob b = new Blob(this, i);
	        _blobs.add(b);
	        _allMovableObjects.add(_blobs.get(i));
	      }
	    
	    //intialise islandMaker
	    _islandMaker = new IslandMaker(this,_currentColorScheme);
	    
	    //initialise the Corridor
	    _corridor = new Corridor(this);
	    
	    //the list of Bubbles
	    _bubbles = new BubbleList();
	    _overlapCounter = 0;
	    _overlapTimer = 0;
	    
	    //only for testing
	    defaultBubble1 = new Bubble(this, 0, 0, 0.25f, 0,_currentColorScheme);
	    defaultBubble2 = new Bubble(this,0 ,0, 1, 1, _currentColorScheme);
	    _bubbles.add(defaultBubble1);
	    _bubbles.add(defaultBubble2);
	    
	    
	    //set the state
	    _currentState = STATE_INTRO;
//	    _currentState = STATE_STARTGAME;
//	    _currentState = STATE_FLYINGBLOBS_QUEST;
//	    _currentState = STATE_ISLAND_QUEST;
//	    _currentState = STATE_CORRIDOR_QUEST;
	    _currentState = STATE_CHARGE_ROBOT;
	    
	    
	    //number of repetitions per game
	    _numberOfRepeats = 5;
	    _repeatCounter = _numberOfRepeats;
	    
	    // initialize Receiver and other listeners
	    receiver = new OSCHandler(this, _bubbles, _currentColorScheme);
	    wheelListener = new MouseWheelEventListener(this,_bubbles);
	  }

	  
	  
	  
	  
	  /***
	   * this method is called repeatedly, in a loop
	   * 
	   */
	  public void draw() {
		  // call noloop() when wanting to stop the draw-loop, back is loop()
		  
		  switch(_currentState) {
		  case STATE_INTRO:
			  loopShowIntro();
			  break;
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
		  case STATE_LOADING:
			  loopLoadingState();
			  break;
		  case STATE_CHARGE_ROBOT:
			  loopChargeRobot();
			  break;
			  
		  default:
			  println("unknown state in MainSketch.draw()");
			  exit();
			  break;
		  }
		  

	   
	    if(mousePressed) {
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
	    
	    if ((keyPressed == true) && (key == 'a')) {
	    	 _bubbles.get(1).changeRadius(1);
	     }
	    if ((keyPressed == true) && (key == 's')) {
	    	 _bubbles.get(1).changeRadius(-1);
	     }
	    
	    if ((keyPressed == true) && (key == 'k')) {
	    	 _bubbles.get(0).changeRadius(1);
	     }
	    if ((keyPressed == true) && (key == 'l')) {
	    	 _bubbles.get(0).changeRadius(-1);
	     }
	    
	  }//end draw loop
	  
	  
	  /**
	   * 
	   */
	  private void loopShowIntro() {
		  background(_currentColorScheme._background);
		  wallE.display();
		  
		  if(!sound_beep.isPlaying()) {
			  sound_beep.play();
		  }
		  
		  if(wallE.dead()) {
			  
			  
			  if(!sound_shutDown.isPlaying()){
				  sound_shutDown.play();
			  }
			  
			  if(mousePressed){
				  _currentState = STATE_STARTGAME;
			  }
		  }
	  }
	  
	  
	  
	  /***
	   * INITIAL STATE, CHOOSE OPTIONS 
	   */
	  private void loopStartState(){
		  
//		  image(startBGimg,0,0);
		  background(_currentColorScheme._background);
		  ButtonRound[] buttons = drawColorButtons();
		  
			  
			  //check if the buttons are clicked = if a bubble is hovering over them
			  for(int i=0; i < buttons.length; i++){
				  for(int j =0; j < _bubbles.size(); j++){
			    	if (buttons[i].isCentredWith(_bubbles.get(j),10)){
			    		
			    		//set colorscheme to selected scheme
			    		switch(buttons[i]._value){
			    		case "A":
			    			_currentColorScheme.setScheme(true);;
			    			break;
			    		case "B":
			    			_currentColorScheme.setScheme(false);;
			    			break;
			    		default:
			    			_currentColorScheme.setScheme(true);;
			    			break;
			    		}
			    			
			    		
			    		_repeatCounter = _numberOfRepeats;
			    		_currentState = STATE_FLYINGBLOBS_QUEST;
			    		break;
					}  
			      }
			  }
		  handleBubbles();
	  }
	  
	  
	 
	  
	  
	/**
	   * 
	   * 
	   * draws the quest with the flying blobs
	   */
	  private void loopBlobsState(){
		  background(_currentColorScheme._backgroundBlobs); //make everything blank to render it
		    
		  //if all the blobs have been popped change the state, either to repeat the quest or move on after n repetitions  
		  if(_blobs.isEmpty()){
			  
			  sound_achievement.play();
			  
			  if(_repeatCounter > 0){
				  _repeatCounter --;
				  for (int i = 0; i < _numberBlobs; i++) {
			    	Blob b = new Blob(this, i);
			        _blobs.add(b);
			        _allMovableObjects.add(_blobs.get(i));
			      }
			  }
			  else {
				  _currentState = STATE_LOADING;
				  _frameCounter = 0;
				  _nextState = STATE_ISLAND_QUEST;
				  _repeatCounter = _numberOfRepeats;
			  }
		  }
		  else {
		  
		    //draw the blobs
		    for (int i = 0; i < _blobs.size(); i++) {
		      _blobs.get(i).move();
		      
		      boolean popped = false;
		      
		      //check if blobs are pooped by close bubble
		      for(int j =0; j < _bubbles.size(); j++){
		    	  if (_blobs.get(i).collidesWith(_bubbles.get(j))){ //if they collide with a bubble, let them pop
						_blobs.get(i).pop();
						_blobs.remove(i);
						popped = true;
						sound_blob.play();
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
		    handleBubbles();
		  }
	  } //end loop blobs
	  
	  
	  /***
	   * 
	   */
	  private void loopIslandState(){
		  background(_currentColorScheme._backgroundIslands);
		  
		  //if repetitons of this quest still left
		  if(_repeatCounter > 0){
			  if(_islandMaker.allIslandsPopped()){
				  sound_achievement.play();
				  
				  _repeatCounter--;
				  _islandMaker.createNewIslands();
			  }
			  else {
				  _islandMaker.checkOverlapping(_bubbles);
				  _islandMaker.display();
				  handleBubbles();
			  }
			  
		  }
		  else {
			  _currentState = STATE_LOADING;
			  _frameCounter = 0;
//			  _nextState = STATE_CORRIDOR_QUEST;
			  _nextState = STATE_CHARGE_ROBOT;
			  _repeatCounter = _numberOfRepeats;
		  }
		  
	  }
	  
	  /**
	   * 
	   */
	  private void loopCorridorState(){
		  background(_currentColorScheme._backgroundCorrdior);
		  _corridor.display();
		  
		  handleBubbles();
	  }
	  
	  
	  /**
	   * 
	   */
	  private void loopLoadingState(){
		  
		  background(_currentColorScheme._background);
		  
		  if(_frameCounter < 500) {
			  for (int i = 0; i < _bubbles.size(); i++) {
				  
				  //check if bubbles overlap
				  for(int j = 0; j < _bubbles.size(); j++){
					  if(j != i) { //dont compare bubble with itself
						  Bubble b1  = _bubbles.get(i);
						  Bubble b2  = _bubbles.get(j);
						  
						  boolean sparks = b1.checkForSparks(b2);
						  
						  if(sparks) {
							  increaseOverlapTimer();
							  sound_spark.play();
						  }
					  }
				  }
				  _bubbles.get(i).displayCrazy();
			    }
			  _frameCounter++;
		  }
		  else {
			  _currentState = _nextState;
		  }
	  }
	  
	  
	  private void loopChargeRobot(){
		  background(_currentColorScheme._background);
		  
		  
		  for (int i = 0; i < _bubbles.size(); i++) {
			  
			  //check if bubbles overlap
			  for(int j = 0; j < _bubbles.size(); j++){
				  if(j != i) { //dont compare bubble with itself
					  Bubble b1  = _bubbles.get(i);
					  Bubble b2  = _bubbles.get(j);
					  
					  boolean sparks = b1.checkForSparks(b2);
					  boolean charge = b1.checkForSparks(wallE._chargeCircle);
					  
					  if(sparks) {
						  increaseOverlapTimer();
					  }
					  
					  if(charge){
						  wallE._currentCharge ++;
					  }
					  
					  if(charge || sparks){
						  sound_spark.play();
					  }
				  }
			  }
			  _bubbles.get(i).display();
		    }
		  
		  
		  wallE.displayCharge();
		  
	  }
	  
	  
	  
	  /*
	   * ###########################################################################################################
	   */
	  
	  /**
	   * DRAW BUBBLES
	   */
	  private void handleBubbles() {
		  for (int i = 0; i < _bubbles.size(); i++) {
			  
			  //check if bubbles overlap
			  for(int j = 0; j < _bubbles.size(); j++){
				  if(j != i) { //dont compare bubble with itself
					  Bubble b1  = _bubbles.get(i);
					  Bubble b2  = _bubbles.get(j);
					  
					  boolean sparks = b1.checkForSparks(b2);
					  
					  if(sparks) {
						  increaseOverlapTimer();
						  sound_spark.play();
					  }
				  }
			  }
			  
			  
			  _bubbles.get(i).display();
		    }
		  
		  
		  //if bubbles have overlapped more than three times, set game back to beginning
		  if(_overlapCounter > 2) {
			  
			  sound_fail.play();
			  
			  println("RESETTING REPEATS TO BEGINNING");
//			  _repeatCounter = _numberOfRepeats;
			  _overlapCounter = 0;
			  
			  _currentState = STATE_STARTGAME;
		  }
	  }
	  
	  
	  
	  
	  private ButtonRound[] drawColorButtons() {
		  
		  ButtonRound[] array = new ButtonRound[2];
		  //left lightampel
		  array[0] = new ButtonRound(this, width/4, height/2, 75, colorsA._bLarge, "A");
		  array[0].display();
		  fill(colorsA._bMedium);
		  noStroke();
		  ellipse(width/4,height/2,100,100);
		  fill(colorsA._bSmall);
		  ellipse(width/4,height/2,50,50);
		  //To get the input of the mouseclick attach a bubble
		  
		  //right ligthampel
		  array[1] = new ButtonRound(this, width/4*3, height/2, 75, colorsB._bLarge, "B");
		  array[1].display();
		  fill(colorsB._bMedium);
		  noStroke();
		  ellipse(width/2 + width/4,height/2,100,100);
		  fill(colorsB._bSmall);
		  ellipse(width/2 + width/4,height/2,50,50);
		  
		  return array;
		}
	  
	  
	  private void increaseOverlapTimer(){
		  _overlapTimer ++;
		  
		  if(_overlapTimer > 70) {
			  _overlapCounter ++;
			  _overlapTimer = 0;
			  
			  println("OVERLAPs: " + _overlapCounter );
		  }
	  }
	  
	  
	  
	  
}
