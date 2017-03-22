package com.polturonducken.bancroft;

import java.util.Date;

public class Class extends Block {
    private int period;
    private String[] letterDay;
    private CalEvent calEvent;
    private boolean special;
    
    public Class(String name, Date endTime, Date startTime, int period, String[] letterDay, CalEvent calEvent) {
        super(name, endTime, startTime);
        this.period = period;
        this.letterDay = letterDay;
        this.calEvent = calEvent;
    }
    
    public Class(String name, Date endTime, Date startTime, int period) {
        super(name, endTime, startTime);
        this.period = period;
    }
    
    public Class(String name, int period) {
        super(name);
        this.period = period;
    }
    
    public int getPeriod() {return period;}
    public void setPeriod(int period) {this.period = period;}
    
    public String[] getLetterDay() {return letterDay;}
    public void setLetterDay(String[] letterDay) {this.letterDay = letterDay;}
    
    public CalEvent getCalEvent() {return calEvent;}
}
