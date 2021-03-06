package com.polturonducken.bancroft;

import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.InfiniteContainer;
import com.codename1.ui.Label;
import com.codename1.ui.NavigationCommand;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.Button;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.MultiList;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteScrollAdapter;
import com.codename1.components.MultiButton;
import com.codename1.components.OnOffSwitch;
import com.codename1.components.WebBrowser;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.notifications.LocalNotification;

/**
 * This file uses a general framework generated by <a href="https://www.codenameone.com/">Codename One</a>
 * for the purpose of building native mobile applications using Java. It has been added to and modified
 * to match the app's desired funtions by Andrew Turley, Ryan Polhemus, and Philip Onffroy.
 */
//lets see
public class Bancroft {
    
    //current page the user is looking at
    private Form current;
    private Resources theme;
    
    private Form home;
  
    private Form website;

    private Form schedule;

    private Form inputClasses;
 
    private Form homeworkManager;
    
    private int screenWidth;
    private int screenHeight;
    
    private boolean schedInput = false;
    
    //the text the user inputs for their 7 classes
    private String[] scheduleInputs = new String[7];
    
    private Class[] classList = new Class[7];
    
    TextField[] classes;
    
    private long date;
    final long CLASS_MILLI = 4500000;
    final long BREAKLAB_MILLI = 2700000;
    final long XLUNCH_MILLI = 1800000;
    
    //Checker to see if classes were first inputted (only adds it if add = 1)
    int add = 0;
    
  
    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        
        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);
        
        screenWidth = Display.getInstance().getDisplayWidth();
        screenHeight = Display.getInstance().getDisplayHeight();
        date = System.currentTimeMillis();
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
        setupScheduleForm(1);//s
        //setupInputClassesForm(); -- to be set up after classes first inputted
        
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
    
    //Allows for updating class info
    public void setupInputClassesForm() {
        inputClasses = new Form("Update Classes");
        
        //Title label
        Label schedIntro = new Label("To update your schedule, please");//backslash n doesn't appear to work
        Label schedIntro2 = new Label("input your classes below:");
        
        //adding page content
        classes = new TextField[7];
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
        for (int i = 0; i < classes.length; i++) {
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
            
            createSchedule();
            
            schedInput = true;
            setupScheduleForm(1);
            setupNavigationCommands();
        });
    }
    
    private void createSchedule() {
    	Date now = new Date();
    	now.setTime(date+CLASS_MILLI);
    	Date later = new Date();
    	later.setTime(date+CLASS_MILLI);
        classList[0] = new Class(classes[0].getText(), later, now, 1);
        classList[1] = new Class(classes[1].getText(), later, now, 2);
        classList[2] = new Class(classes[2].getText(), later, now, 3);
        classList[3] = new Class(classes[3].getText(), later, now, 4);
        classList[4] = new Class(classes[4].getText(), later, now, 5);
        classList[5] = new Class(classes[5].getText(), later, now, 6);
        classList[6] = new Class(classes[6].getText(), later, now, 7);
        
        classList[0].setLetterDay(new String[]{"A", "B", "D", "F"});
        classList[1].setLetterDay(new String[]{"A", "C", "D", "F"});
        classList[2].setLetterDay(new String[]{"A", "C", "E", "F"});
        classList[3].setLetterDay(new String[]{"A", "C", "E", "G"});
        classList[4].setLetterDay(new String[]{"B", "C", "E", "G"});
        classList[5].setLetterDay(new String[]{"B", "D", "E", "G"});
        classList[6].setLetterDay(new String[]{"B", "D", "F", "G"});
    }
    
    public void setupScheduleForm(int nextClass) {
        schedule = new Form("Schedule");////////////fix here
        
        //Checks if the schedule has been inputted yet. If not, goes into this loop.
        if (!schedInput) {
            Label intro = new Label("Please input classes below:");
            schedule.addComponent(intro);
            setBackCommand(schedule);
            
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
            for (int i = 0; i < classes.length; i++) {
                schedule.addComponent(classes[i]);
            }
            schedule.addComponent(schedEnter);
            
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
                setupScheduleForm(1); // int is the next class
                setupInputClassesForm();
                add++;
                setupNavigationCommands();
            });
            
        }
        else {
            //display the desired list of upcoming classes here
            Label schedIntro = new Label("Classes up next:           ");
            schedule.addComponent(schedIntro);
            
            /*
            //displaying the inputted classes below -- later will make this list revolve to put most recent at top
            Label[] afterClasses = new Label[7];
            for (int i = 0; i < afterClasses.length; i++) {
                afterClasses[i] = new Label(scheduleInputs[i]);
                schedule.addComponent(afterClasses[i]);
            }
           */
           
           //Adds the newly inputted classes to the schedule page 
           //Adds them in the correct order of what classes are up next, using nextClass int and modulus
            
            ArrayList<Map<String, Object>> data = new ArrayList<>();
            for (int i = nextClass; i < nextClass+scheduleInputs.length; i++) {
            	data.add(createListEntry(scheduleInputs[i  % 7], "Period "+ ((i)%7+1))); 
            }
            

            DefaultListModel<Map<String, Object>> model = new DefaultListModel<>(data);
            MultiList ml = new MultiList(model);
            schedule.addComponent(ml);
            //schedule.paintComponent(screenshot.getGraphics(), true);
            setBackCommand(schedule);
            
            schedule.show();
        }
    }
    
    public void setupHomeworkForm() {
        homeworkManager = new Form("Homework Manager");
        setBackCommand(homeworkManager);
        /*
        Label homeIntro = new Label("Priority of Homework list:");
        homeworkManager.addComponent(homeIntro);
        sendNotification("ID", "Test", "A good message");
        */
        Image red = Image.createImage(100, 100, 0xffff0000);
        Image green = Image.createImage(100, 100, 0xff00ff00);
        Image blue = Image.createImage(100, 100, 0xff0000ff);
        Image gray = Image.createImage(100, 100, 0xffcccccc);

        ImageViewer iv = new ImageViewer(red);
        iv.setImageList(new DefaultListModel<>(red, green, blue, gray));
        homeworkManager.add(iv);
        OnOffSwitch onOff = new OnOffSwitch();
        homeworkManager.addComponent(onOff);
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        data.add(createListEntry("A Game of Thrones", "1996"));
        data.add(createListEntry("A Clash Of Kings", "1998"));
        data.add(createListEntry("A Storm Of Swords", "2000"));
        data.add(createListEntry("A Feast For Crows", "2005"));
        data.add(createListEntry("A Dance With Dragons", "2011"));
        data.add(createListEntry("The Winds of Winter", "2016 (please, please, please)"));
        data.add(createListEntry("A Dream of Spring", "Ugh"));

        DefaultListModel<Map<String, Object>> model = new DefaultListModel<>(data);
        MultiList ml = new MultiList(model);
        homeworkManager.add(ml);
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
        
        //Add a input classses tab to the overflow menu for updating classes
        NavigationCommand classesCommand = new NavigationCommand("Update Classes");
       	classesCommand.setNextForm(inputClasses);
       	if (add==1) {
            home.getToolbar().addCommandToOverflowMenu(classesCommand); //addCommandToSideMenu(scheduleCommand);
            add++;
       	}
       	
        //Add a Homework Manager tab to the toolBar
        NavigationCommand homeworkCommand = new NavigationCommand("Homework Manager");
        homeworkCommand.setNextForm(homeworkManager);
        home.getToolbar().addCommandToSideMenu(homeworkCommand);
        
       	//Add the Displaying Schedule page to main menu
       	NavigationCommand scheduleCommand = new NavigationCommand("Schedule");
       	scheduleCommand.setNextForm(schedule);
       	NavigationCommand afterScheduleCommand = new NavigationCommand("Schedule");
       	afterScheduleCommand.setNextForm(schedule);
       	
       	if (!schedInput) {
            home.getToolbar().addCommandToSideMenu(scheduleCommand);
       	}
       	else {
            home.removeCommand(scheduleCommand);
            home.getToolbar().addCommandToSideMenu(afterScheduleCommand);
            home.revalidate();
       	}
       	
        
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
//added methods


//for multilist to work
private Map<String, Object> createListEntry(String name, String date) {
    Map<String, Object> entry = new HashMap<>();
    entry.put("Line1", name);
    entry.put("Line2", date);
    return entry;
}
//for scrolling to work
int pageNumber = 1;
java.util.List<Map<String, Object>> fetchPropertyData(String text) {
    try {
        ConnectionRequest r = new ConnectionRequest();
        r.setPost(false);
        r.setUrl("http://api.nestoria.co.uk/api");
        r.addArgument("pretty", "0");
        r.addArgument("action", "search_listings");
        r.addArgument("encoding", "json");
        r.addArgument("listing_type", "buy");
        r.addArgument("page", "" + pageNumber);
        pageNumber++;
        r.addArgument("country", "uk");
        r.addArgument("place_name", text);
        NetworkManager.getInstance().addToQueueAndWait(r);
        Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
        Map<String, Object> response = (Map<String, Object>)result.get("response");
        return (java.util.List<Map<String, Object>>)response.get("listings");
    } catch(Exception err) {
        Log.e(err);
        return null;
    }
}
}
