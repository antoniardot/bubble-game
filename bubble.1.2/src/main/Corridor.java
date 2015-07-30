package main;

import processing.core.PApplet;

public class Corridor {

	PApplet _p;
	
	int xspacing = 8; 	//steps within wall
	int w; 				//width of entire wave of one wall
	int maxwaves = 4; 	//number of waves added together to make it more irregular (?)
	
	float theta = 0.0f;
	float[] amplitude = new float[maxwaves]; //array for heights of waves
	float[]	dx = new float[maxwaves]; //values for incrementing x in each wave
	float[] yvalues;  //store the height values for the wave
	
	/**
	 * 
	 * 
	 * @param p
	 */
	public Corridor(PApplet p) {
		_p = p;
		
		w = _p.width;
		
		for (int i = 0; i < maxwaves; i++) {
			amplitude[i] = _p.random(5,10);
			float period = _p.random(100,300); // How many pixels before the wave repeats
			dx[i] = (_p.TWO_PI / period) * xspacing;
		}
		
		yvalues = new float[w/xspacing];
		calcWave();
	}
	
	
	public void calcWave() {
		theta += 0.02;
		
		for(int i =0; i< yvalues.length; i++){
			yvalues[i] = 0;
		}
		
		
		for(int j =0; j < maxwaves; j++) {
			float x = theta;
			
			for (int i = 0; i < yvalues.length; i++) {
				//every second wave uses cos instead of sin
				if(j %2 == 0) {
					yvalues[i] += _p.sin(x)*amplitude[j];
				}
				else {
					yvalues[i] += _p.cos(x)*amplitude[j];
				}
				
				x +=dx[j];
			}
		}
	}

	
	
	public void renderWave() {
		for(int x = 0; x < yvalues.length; x++) {
			
			_p.ellipse(x*xspacing,_p.height/2 + yvalues[x], 16, 16); //one wall
			_p.ellipse(x*xspacing,_p.height/2 + yvalues[x] + 120, 16, 16); //the other wall
			
		}
	}
	
	public void display(){
		_p.fill(200);
		_p.noStroke();
		
		renderWave();
		
//		_p.beginShape();
////	    for (int i = 0; i < 10; i++) {
////	    	int x = _p.width/10 *i + 10;
////	    	int y = 50 + i * (-1)^i;
////			_p.vertex(x,y);
////		}
//	    
//	    for (int i = 0; i < 200; i += 100) {
//	        _p.bezier(10, 50 + i, _p.width/4, 10 + 10 +i, _p.width/4 * 2, 10 + i, _p.width/4 *3, 50 + i);;
//	    	
//	      }
//	  _p.endShape();
	}
}
