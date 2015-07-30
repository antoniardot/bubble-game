package main;

import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import net.java.games.input.*;

import processing.core.PApplet;

public class MouseWheelEventListener implements MouseWheelListener {
	
	
	PApplet parent;
	BubbleList _bubbles;
	
	ControllerEnvironment cEnvironment;
	ArrayList<Mouse> cArray;
	
	/**
	 * Constructor
	 * @param p the mainSketch PApplet
	 */
	public MouseWheelEventListener(PApplet p, BubbleList bubs){
		parent = p;
		_bubbles = bubs;
		parent.addMouseWheelListener(this);
		p.println("JInput version: " + Version.getVersion());
		
		
		cEnvironment = ControllerEnvironment.getDefaultEnvironment(); 
		Controller[] allControllerArray = cEnvironment.getControllers(); 
		Controller.Type type;
	    String name;
	    
	    cArray = new ArrayList<Mouse>();
		
		for (int i = 0; i < allControllerArray.length; i++) { //go through all controllores
			type = allControllerArray[i].getType();
			name = allControllerArray[i].getName();
			if (type == Controller.Type.MOUSE && !name.equals("Apple Internal Keyboard / Trackpad")) { //filter for type mouse
				p.println(i + " " + name );
				Mouse m = (Mouse) allControllerArray[i];
				String s = getComponentInfo(m.getWheel());
				p.println(s);
				
				cArray.add(m);
				
			}
		}
		
	}

	
	/**
	 * called when any mousewheel of all the mouses attached moves
	 */
	@Override
	public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
	   int notches = e.getWheelRotation();
	   Object o = e.getSource();
	   
	   for(int i =0; i < cArray.size();i++){
		   Mouse m = cArray.get(i);
		   
		   m.poll();
	       Component w = m.getWheel();
	       if (w != null && w.isAnalog()){
	    	   
	    	   float f = w.getPollData();
	    	   
	    	   if(f != 0.0) {
	    		   //adjust the radius of the bubble;
	    		   if (notches < 0) {   //if wheel went up
	    			   _bubbles.get(i).changeRadius(1);
	    		   } 
	    		   else { 				//if wheel went down
	    			   _bubbles.get(i).changeRadius(-1);
	    		   }
	    	   }
	       }
	     }
	}
	
	
	
	private String getComponentInfo(Component c) {
		if (c == null) {
			return "none";
		}			
		else {
			return (c.getName() + " - " + c.getIdentifier() + " - " + (c.isRelative() ? "relative" : "absolute") + " - " + (c.isAnalog() ? "analog" : "digital") + " - " + c.getDeadZone() );
		}
	} 



}
