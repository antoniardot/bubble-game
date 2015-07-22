package main;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import netP5.NetAddress;
import oscP5.*;
import processing.core.*;

public class OSCHandler {

	OscP5 oscP5;
	NetAddress myRemoteLocation;
	
	PApplet _p; //the parent that is calling the handler
	HashMap<Integer, TspsPerson> _personList;
	int _numPeople;
	
	ArrayList<Bubble> _bubbleList;
	
	public OSCHandler(PApplet parent){
		
		_p = parent;
		_numPeople = 0;
		_personList = new HashMap<Integer, TspsPerson>();
		_bubbleList = new ArrayList<Bubble>();
		
		// start oscP5, telling it to listen for incoming messages at port 5001 */
		oscP5 = new OscP5(this,12000); 
		// set the remote location to be the localhost on port 5001
		myRemoteLocation = new NetAddress("127.0.0.1",12000);
		
		parent.println("OSC-Handler initialized");
	}
	
	
	/**
	 * This method is called, everytime an OSCMessage is received
	 * @param oscMessage
	 */
	void oscEvent(OscMessage oscMessage) {
//			_p.println("there was an OSC message");
			
			try{
				
				//filter the messgage for type, then deal with infor
				
				if(oscMessage.checkAddrPattern("/TSPS/scene")){
					_numPeople = oscMessage.get(1).intValue(); // number of people
//					int a = oscMessage.get(0).intValue(); //time
//					float c = oscMessage.get(2).floatValue(); //?
//					float d = oscMessage.get(3).floatValue(); //average motion x
//					float e = oscMessage.get(4).floatValue(); //average motion y
//					int f = oscMessage.get(5).intValue(); //?
					
				}
				
				
				if(oscMessage.checkAddrPattern("/TSPS/personEntered/")){

					int id 						= oscMessage.get(0).intValue();
//					int oid 					= oscMessage.get(1).intValue();
					int age 					= oscMessage.get(2).intValue();
					float centroidx 			= oscMessage.get(3).floatValue();
					float centroidy 			= oscMessage.get(4).floatValue();
//					_p.println("pos: " + centroidx + ", " + centroidy);
					
					TspsPerson p  = new TspsPerson(_p, id, age, centroidx, centroidy);
					_personList.put(id, p);

					_p.println("PERSON ENTERED: " + id);
				}

				
				if(oscMessage.checkAddrPattern("/TSPS/personUpdated/")){
//					_p.println("PERSON UPDATED");
					
					int id 						= oscMessage.get(0).intValue();
					float centroidx 			= oscMessage.get(3).floatValue();
					float centroidy 			= oscMessage.get(4).floatValue();
					
					TspsPerson p = _personList.get(id);
					p.updatePos(centroidx, centroidy);
					
				}

				if(oscMessage.checkAddrPattern("/TSPS/personWillLeave/")){
					int id 						= oscMessage.get(0).intValue();
					_personList.remove(id);
					
					_p.println("PERSON LEFT: " + id);
				}
				
				
				
//				
//				//println(id + "/" + age + "/"  + centroidx +"/" +centroidy);
//				
//				_p.println(oscMessage.addrPattern()); //personWillLeave
				
			}
			catch (Exception e) {
				//_p.println("EXCEPTION THROOOOOOOOOWN");
			} 
			
			
			
	}
	
	/**
	 * 
	 * @return the number of currently detected people
	 */
	public int getNumPeople() {
		return _numPeople;
	}

	/**
	 * 
	 * @return a List of Persons, that have been detected
	 */
	public ArrayList<TspsPerson> getPeopleList() {
		ArrayList<TspsPerson> valuesList = new ArrayList<TspsPerson>(_personList.values());
		return  valuesList;
	}
	
	public ArrayList<Bubble> getBubbleList() {
		
		_bubbleList = new ArrayList<Bubble>();
		
		for(TspsPerson p: getPeopleList()) {
			_bubbleList.add(p._bubble);
		}
		return _bubbleList;
	}
	
	
	
	
}
