package com.polturonducken.bancroft;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.codename1.io.Externalizable;
import com.codename1.io.Util;

public class CalEvent implements Externalizable {
	
	//info for the event
	private String subject;
	private String date;
	private String startTime;
	private String endTime;
	private String eventNotes;
	private String eventDetails;
	private String eventType;
	
	public CalEvent(String subj, String date, String sTime, String eTime, String eNotes, String eDetails, String eType)
	{
		subject = subj;
		this.date = date;
		startTime = sTime;
		endTime = eTime;
		eventNotes = eNotes;
		eventDetails = eDetails;
		eventType = eType;
	}
	public CalEvent(DataInputStream out)
	{
		//Yeah kinda lazy... but it works
		try{
			internalize(1, out);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public int getVersion() //for backwards compat - states which version of storage is being used
	{
		return 1;
	}

	@Override
	public void externalize(DataOutputStream out) throws IOException //externalize! (store)
	{
		Util.writeUTF(subject, out);
		Util.writeUTF(date, out);
		Util.writeUTF(startTime, out);
		Util.writeUTF(endTime, out);
		Util.writeUTF(eventNotes, out);
		Util.writeUTF(eventDetails, out);
		Util.writeUTF(eventType, out);
	}

	@Override
	public void internalize(int version, DataInputStream in) throws IOException //internalize! (retrieve)
	{
		subject = Util.readUTF(in);
		date = Util.readUTF(in);
		startTime = Util.readUTF(in);
		endTime = Util.readUTF(in);
		eventNotes = Util.readUTF(in);
		eventDetails = Util.readUTF(in);
		eventType = Util.readUTF(in);
	}

	@Override
	public String getObjectId() //not object in the Java sense, but the storage object
	{
		return "CalEvent";
	}

}
