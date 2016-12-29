package com.polturonducken.bancroft;

import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Command;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.*;
import com.codename1.ui.NavigationCommand;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.components.WebBrowser;
import com.codename1.ui.layouts.*;
import com.codename1.notifications.LocalNotification;

/**
 * This file uses a general framework generated by <a href="https://www.codenameone.com/">Codename One</a> 
 * for the purpose of building native mobile applications using Java. It has been added to and modified 
 * to match the app's desired funtions by Andrew Turley, Ryan Polhemus, and Philip Onffroy.
 */

public class Bancroft {
	
	//current page the user is looking at
    private Form current;
    private Resources theme;

    //home page that pops up when app is open
    private Form home;
    
    //page that brings user to the Bancroft School website
    private Form website;
    
    //pages that shows user's upcoming schedule
    private Form schedule;
    
    //page in which user inputs their classes
    private Form inputClasses;
    
    //page that displays the priority of homework list
    private Form homeworkManager;

    //this and the method below reads the hight and width of the screen of the device being used
    private int screenWidth;
    private int screenHeight;

    //Tells whether user has inputted their class schedule yet
    private boolean schedInput = false;
    
    //the text the user inputs for their 7 classes
    private String[] scheduleInputs = new String[7];
	
    //reads in the screen dimentions 
    public void init(Object context) {
	    theme = UIManager.initFirstTheme("/theme");
	
	    // Enable Toolbar on all Forms by default
	    Toolbar.setGlobalToolbar(true);
	        
	    screenWidth = Display.getInstance().getDisplayWidth();
	    screenHeight = Display.getInstance().getDisplayHeight();
    }
    
  //gets called to start up the app
    public void start() {
        if (current != null) {
            current.show();
            return;
        }
        
        //Setup different pages
        setupHomeForm();      
        setupWebsiteForm();
        setupHomeworkForm();
        setupInputClassesForm();
        
        //Add navigation commands to the home Form
        setupNavigationCommands();

        //Add Edit, Add and Delete Commands to the home Form context Menu
        Image img = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, UIManager.getInstance().getComponentStyle("Command"));

        home.show();
    }
    
    //command to allow user to go back and forth from (leave and go back to) a Form page
    //currently creates a back button but hope to change it to navigation menu in the future
    protected void setBackCommand(Form f) {
        Command back = new Command("") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                home.showBack();
            }

        };
        //create icon in upper left corner (the switch-between-pages icon)
        Image img = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, UIManager.getInstance().getComponentStyle("TitleCommand"));
        back.setIcon(img);
        f.getToolbar().addCommandToLeftBar(back);
        f.getToolbar().setTitleCentered(true);
        f.setBackCommand(back);
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }

    public void destroy() {
    }
    
    public void login() {
    	// Implement logging in to portal. Easy-peasy
    }
    
    public void downloadCalendar() {
    	/*step one: student logs in to portal (possibly add the return value CSV instead of void)
    	 * Step 2: clicks a big, giant, DOWNLOAD CALENDAR button
    	 * Step 3: write code to automatically download an iCal / CSV of the calendar
    	 * 4: import the CSV/iCal data into this app, or google calendar
    	 * 5: read through the calendar data and display/use the data in the "priority of homework" tab
    	 * 6: students happy and know what homework to complete first
    	*/
    }
    
    // Create the starting default form
    public void setupHomeForm() {
        home = new Form("Home");
        home.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        //Title label
        Label welcome = new Label("Welcome to the Bancroft App!");
        
        //Username and password textfields
        TextField username = new TextField();
        username.setHint("Username");
        TextField password = new TextField();
        password.setHint("Password");
        password.setConstraint(TextArea.PASSWORD);
        
        //Login button
        Button enter = new Button("Enter");
        
        //Add components to form
        home.addComponent(welcome);
        home.addComponent(username);
        home.addComponent(password);
        home.addComponent(enter);
    }
    
    //connect website page to the Bancroft portal website
    public void setupWebsiteForm() {
        website = new Form("Website");
    	WebBrowser browser = new WebBrowser("https://www.bancroftschool.org/userlogin.cfm?");
    	website.addComponent(browser);
    	setBackCommand(website);
    }
    
    public void setupInputClassesForm() {
    	inputClasses = new Form("Input Classes");
		
        //Title label
        Label schedIntro = new Label("To set up your schedule, please");//backslash n doesn't appear to work
        Label schedIntro2 = new Label("input your classes below:");
	    
        //adding page content 
        TextField[] classes = new TextField[7];
        classes[0] = new TextField();
        classes[0].setHint("First Period Class");
        
        classes[1] = new TextField();
        classes[1].setHint("Second Period Class");
        
        classes[2] = new TextField();
        classes[2].setHint("Third Period Class");
        
        classes[3] = new TextField();
        classes[3].setHint("Fourth Period Class");
        
        classes[4] = new TextField();
        classes[4].setHint("Fifth Period Class");
        
        classes[5] = new TextField();
        classes[5].setHint("Sixth Period Class");
        
        classes[6] = new TextField();
        classes[6].setHint("Seventh Period Class");
        
        Button schedEnter = new Button("Enter");
        
	    //adding components
	    inputClasses.addComponent(schedIntro);
	    inputClasses.addComponent(schedIntro2);
	    for(int i = 0; i < classes.length; i++){
	    	inputClasses.addComponent(classes[i]);
	    }
	    inputClasses.addComponent(schedEnter);
	    setBackCommand(inputClasses);
	    
	    //Sets the schedule input to true if user presses the enter button
	    schedEnter.addActionListener((e) -> {
	    	scheduleInputs[0] = classes[0].getText();
	    	scheduleInputs[1] = classes[1].getText();
	    	scheduleInputs[2] = classes[2].getText();
	    	scheduleInputs[3] = classes[3].getText();
	    	scheduleInputs[4] = classes[4].getText();
	    	scheduleInputs[5] = classes[5].getText();
	    	scheduleInputs[6] = classes[6].getText();
	    	schedInput = true;
			setupScheduleForm();
			setupNavigationCommands();
        });
    }
    public void setupScheduleForm() {
    	schedule = new Form("Schedule");
    	/*Checks if the schedule has been inputted yet. If not, goes into this loop. --may be used later, not now
        if(!schedInput) {
            Label intro = new Label("Please input classes in the upper right menu from the home page.");
            schedule.addComponent(intro);
            setBackCommand(schedule);
            		
        }
        else{*/
             //display the desired list of upcoming classes here
             Label schedIntro = new Label("Classes up Next:");
             schedule.addComponent(schedIntro);
             
             //displaying the inputted classes below -- later will make this list revolve to put most recent at top
             Label[] afterClasses = new Label[7];
             for(int i = 0; i < afterClasses.length; i++){
            	 afterClasses[i] = new Label(scheduleInputs[i]);
            	 schedule.addComponent(afterClasses[i]);
             }
     	     
     	     setBackCommand(schedule);
     	     schedule.show();
    }
    
    public void setupHomeworkForm() {
    	homeworkManager = new Form("Homework Manager");
    	setBackCommand(homeworkManager);
    	Label homeIntro = new Label("Priority of Homework list:");//backslash n doesn't appear to work
        homeworkManager.addComponent(homeIntro);
    	sendNotification("ID", "Test", "A good message");
    }
    
    public void setupNavigationCommands() {
    	//Home navigation Form 
    	NavigationCommand homeCommand = new NavigationCommand("Home");
        homeCommand.setNextForm(home);
        home.getToolbar().addCommandToSideMenu(homeCommand);
        
        //Add website page to main navigation menu
        NavigationCommand websiteCommand = new NavigationCommand("Website");
        websiteCommand.setNextForm(website);
        home.getToolbar().addCommandToSideMenu(websiteCommand);
        
        //Add a input classses tab to the overflow menu
        NavigationCommand classesCommand = new NavigationCommand("Input Classes");
       	classesCommand.setNextForm(inputClasses);
       	home.getToolbar().addCommandToOverflowMenu(classesCommand); //addCommandToSideMenu(scheduleCommand);
       
       	//Add the Displaying Schedule page to main menu
       	if(schedInput){
       		NavigationCommand scheduleCommand;
       		scheduleCommand = new NavigationCommand("Schedule");
       		scheduleCommand.setNextForm(schedule);
       		home.getToolbar().addCommandToSideMenu(scheduleCommand);
       	}
       	
        //Add a Homework Manager tab to the toolBar
        NavigationCommand homeworkCommand = new NavigationCommand("Homework Manager");
        homeworkCommand.setNextForm(homeworkManager);
        home.getToolbar().addCommandToSideMenu(homeworkCommand);
    }
	
    public void sendNotification(String identification, String title, String message) {
	    /* Template for sending notifications (from codenameone, https://www.codenameone.com/manual/appendix-ios.html)
	     * To be implemented to send schedule, homework notifications */
	    LocalNotification n = new LocalNotification();
	    n.setId(identification);
	    n.setAlertBody(message);
	    n.setAlertTitle(title);
	    //n.setAlertSound("beep-01a.mp3");
	    
	    Display.getInstance().scheduleLocalNotification(
	            n,
	            System.currentTimeMillis() + 10, // fire date/time
	            LocalNotification.REPEAT_MINUTE  // Whether to repeat and what frequency
	    );
    }
}
