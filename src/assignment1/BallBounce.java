package assignment1;
import acm.program.GraphicsProgram;
import acm.graphics.GOval;
import acm.graphics.GRect;

import java.awt.Color;
import java.lang.Math;

public class BallBounce extends GraphicsProgram{
	
	/*
	 * 
	 * ECSE 202 Assignment #1 
	 * Brendan Marks (260866323)
	 * 
	 * This assignment consists of a ball bouncing according to Newtonian Mechanics
	 * with inputed initial height and energy loss factor (energy lost at each bounce)
	 * 
	 * 
	 */
	
	private static final double G = 9.8; // m/s^2
	private static final int WIDTH = 800; //pixels
	private static final int HEIGHT = 800;
	private static final double TIME_OUT = 30; //seconds
	private static final double INTERVAL_TIME = 0.1; //seconds
	private static final int FLOOR_HEIGHT = 600; //setting where floor appears in simulation
	private static final int DIAMETER = 100; // setting diameter of the ball
	static final int MAX_FALL = FLOOR_HEIGHT - DIAMETER; // maximum distance ball can fall before it will hit ground (customizable)
	
	public void run(){ 
		
		
		
		
		double totalTime = 0; //time for entirety of program
		double time = 0; //time for individual falls, gets reset 
		double initialXPos = 5; //in m
		double xPos; //this is the value that will move with horizontal velocity	
		this.resize(WIDTH, HEIGHT); // creating canvas
		GOval ball = new GOval(0, 0, DIAMETER, DIAMETER); // creating ball (position doesn't matter because will be inputed later
		ball.setFilled(true);
		ball.setColor(Color.RED);
		add(ball);
		GRect floor = new GRect(0, FLOOR_HEIGHT, WIDTH, 3); //creating a floor in shape of a rectangle 
		floor.setFilled(true);
		floor.setColor(Color.BLACK);
		add(floor);
		
		//getting input for initial height
		print("Enter the initial height of the ball between [0, 60]: ");
		double initialHeight = readDouble();
		
		//getting input for energy loss parameter
		print("Enter the energy loss parameter between [0, 1]: ");
		double energyLossFactor = readDouble();
	
		double horizontalVelocity = 2; // m/s
		double height = initialHeight; 
		
		ball.setLocation(initialXPos, MAX_FALL - initialHeight * 10); //setting ball to inputed position
		boolean directionUp = false;
		double vt = Math.sqrt(2 * G * (initialHeight)); // initializing bounce velocity 
		double energyLoss = (1 - energyLossFactor); // initializing energy loss
		
		double initialUpPosition = 0; // initializing variable
		
		while (totalTime < TIME_OUT) {
			xPos = initialXPos + totalTime * horizontalVelocity; //in pixels 
			GOval trace = new GOval((xPos * 10) + DIAMETER / 2, (MAX_FALL - height * 10) + DIAMETER / 2, 1, 1); //creating circles for trace line
			trace.setFilled(true);
			trace.setColor(Color.BLACK);
			add(trace);
			
			if (!directionUp) { //going down
				height = (initialHeight - (0.5 * G * Math.pow(time, 2))); //formula for height during fall
				ball.setLocation(xPos * 10, MAX_FALL - height * 10); // current position of ball in simulation units
				time += INTERVAL_TIME; //incrementing time
					if (height <= 0) { //when ball hits ground
						initialUpPosition = height; 
						initialHeight = height;
						vt *=  energyLoss; // ball loses energy again 
						time = 0; //reset fall time				
						directionUp = true;
						ball.setLocation(xPos * 10, MAX_FALL - initialUpPosition * 10);
						
						
					}
			} 
			
			else { //going up
						height = initialUpPosition + (vt * time - 0.5 * G * Math.pow(time,  2)); //formula for height during bounce
					   
						if (height >= initialHeight) { // still going up
							initialHeight = height; // keeping track of last highest point
							ball.setLocation(xPos * 10, MAX_FALL - height * 10);
							time += INTERVAL_TIME;
							
						}
						else {
							directionUp = false; //starting to go down
							time = 0; //resetting time for going down
							
						}
						
			}
				String totalTimeAsString = String.format("%.2f", totalTime); //formatting data for cleaner output values
				String xPosAsString = String.format("%.2f", xPos);           // ^^
				String heightAsString = String.format("%.2f", height);       // ^^
				
				//Outputting data points 
				println("Time: " + totalTimeAsString + " X: " + xPosAsString + " Y: " + heightAsString);
				
				//incrementing time 
				totalTime += INTERVAL_TIME;
				
				//pausing program so that it actually runs in seconds
				pause(INTERVAL_TIME * 1000); // units are ms
			}
		}
		
		
		
	}
	

	

