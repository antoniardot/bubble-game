package main;

import java.util.ArrayList;

import processing.core.*;

/***
 * The colorscheme can be chosen in the beginning of the game and determines the colors used in the game
 * @author Bruno
 *
 */
public class ColorScheme {

	
	PApplet _p;
	
	int _bSmall; //color for bubble when small
	int _bMedium; //color for bubble when medium sized
	int _bLarge; //... when large
	
	int _bFill;
	int _bOverlap;
	
	int _background;
	int _backgroundBlobs;
	int _backgroundIslands;
	int _backgroundCorrdior;
	
	//COLORS
	
	int red1;
	int yellow1;
	int yellow2;
	int green1 ;
	int green2;
	
	int lightGreen;
	int pink;
	
	int beige;
	int black;
	
	
	//List of all Colors
	
	ArrayList<Integer> _allColors;
	
//	_bSmall = _p.color(120, 181, 202);
//	_bMedium = _p.color(91,133,170);
//	_bLarge = _p.color(124,222,220);
	
	
	
	public ColorScheme(PApplet p, boolean dark){
		_p = p;
		
		_allColors = new ArrayList<Integer>();
		
		red1 = _p.color(240,2,0, 150);
		yellow1 = _p.color(252,186,6, 150);
		yellow2 = _p.color(255,210,117, 150);
		green1 = _p.color(133,176,146);
		green2 = _p.color(162,221,147);
		lightGreen = _p.color(165, 255, 151);
		
		pink = _p.color(211,12,123);
		
		beige = _p.color(202,202,170);
		black = _p.color(0);
		
		_allColors.add(red1);
		_allColors.add(yellow1);
		_allColors.add(yellow2);
		_allColors.add(green1);
		_allColors.add(green2);
		_allColors.add(lightGreen);
		_allColors.add(pink);
		_allColors.add(black);
		

		//for now there are two color-schemes dark and bright
		setScheme(dark);
	}
	
	public void setScheme(boolean dark){
		if(dark) {
			_bSmall = yellow2;
			_bMedium = yellow1;
			_bLarge = red1;
			_bOverlap = _p.color(255,100);
			_bFill = _p.color(255,100);
			_background = black;
			_backgroundBlobs = black;
			_backgroundIslands = black;
			_backgroundCorrdior = black;
			
		}
		else {
			_bSmall = lightGreen;
			_bMedium = yellow2;
			_bLarge = pink;
			_bOverlap = _p.color(255,100);
			_bFill = _p.color(255,100);
			_background = black;
			_backgroundBlobs = black;
			_backgroundIslands = black;
			_backgroundCorrdior = black;
		}
	}
	
	
	public int getRandomColor(){
		int x  = (int) _p.random(_allColors.size());
		return _allColors.get(x);
	}
	
//	_bSmall = _p.color(82, 63, 129);
//	_bMedium = _p.color(138,71,122);
//	_bLarge = _p.color(73,125,159);
//	_bOverlap = _p.color(88,74,144);
//	_bFill = _p.color(255,100);
	
	
//	_bSmall = _p.color(93, 134, 94);
//	_bMedium = _p.color(130,141,46);
//	_bLarge = _p.color(165,125,57);
//	_bOverlap = _p.color(24,108,107);
//	_bFill = _p.color(255,100);
	
}
