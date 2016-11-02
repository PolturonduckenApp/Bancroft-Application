package com.polturonducken.bancroft;

import com.codename1.ui.Display;
import com.codename1.ui.Form;
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

/**
 * This file is build upon one generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * for building native mobile applications using Java. It has been added to and modified to match
 * the app's desired funtions by Andrew Turley, Ryan Polhemus, and Philip Onffroy.
 */
public class Bancroft {
	
	//current page the user is looking at
    private Form current;
    private Resources theme;

    //home page that pops up when app is open
    private Form home;
    
    //page that brings user to the Bancroft School website
    private Form website;
    
    //this and the method below reads the hight and width of the screen of the device being used
    private int screenWidth;
    private int screenHeight;

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
        

        //create and build the home Form
        home = new Form("Home");
        home.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Label welcome = new Label("Welcome to the Bancroft App!");
        home.addComponent(welcome);
        TextField username = new TextField();
        username.setHint("Username");
        TextField password = new TextField();
        password.setHint("Password");
        password.setConstraint(TextArea.PASSWORD);
        Button enter = new Button("Enter");
        home.addComponent(username);
        home.addComponent(password);
        home.addComponent(enter);
        
        //Create website page and set a Back Command to navigate back to the home Form        
        website = new Form("Website");
        setupWebsiteForm();
        setBackCommand(website);

        //Add navigation commands to the home Form
        NavigationCommand homeCommand = new NavigationCommand("Home");
        homeCommand.setNextForm(home);
        home.getToolbar().addCommandToSideMenu(homeCommand);
        
        //Add navigation commands to the website Form
        NavigationCommand websiteCommand = new NavigationCommand("Website");
        websiteCommand.setNextForm(website);
        home.getToolbar().addCommandToSideMenu(websiteCommand);

        //Add Edit, Add and Delete Commands to the home Form context Menu
        Image im = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, UIManager.getInstance().getComponentStyle("Command"));
        

        home.show();
    }
    
    //command to allow user to go back and forth from (leave and go back to) a Form page 
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
    //connect website page to the Bancroft portal website
    public void setupWebsiteForm() {
    	WebBrowser browser = new WebBrowser("https://www.bancroftschool.org/userlogin.cfm?");
    	website.setLayout(new BorderLayout());
    	website.addComponent(BorderLayout.CENTER, browser);
    }

}
