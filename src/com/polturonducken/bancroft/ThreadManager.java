package com.polturonducken.bancroft;

import java.util.Timer;
import java.util.TimerTask;

public class ThreadManager 
{

	public static void main(String[] args)
	{
	    int milisInAMinute = 60000;
	    long time = System.currentTimeMillis();
	
	    Runnable update = new Runnable() {
	        public void run() {
	            // Do whatever you want to do when the minute changes
	        }
	    };
	
	    Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	        public void run() {
	            update.run();
	        }
	    }, time % milisInAMinute, milisInAMinute);
	
	    // This will update for the current minute, it will be updated again in at most one minute.
	    update.run();
	}

}
