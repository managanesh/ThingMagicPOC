package com.veda.cache;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Pseudo {

	public static void main(String args[]){
		long longDate = 1452647046115L;
		Long secAgo = 30000L;
		//DateTime dateTimeParis = new DateTime( milliseconds ).withZone( DateTimeZone.forID( "Europe/Paris" ) );
		Date date=new Date(longDate);
		System.out.println("Tag Scan Date: "+date);
		Date currentDate = new Date();
		System.out.println("Current Date: "+currentDate);
		Date newDate = new Date(currentDate.getTime() - secAgo);
		System.out.println("CurrentDate - 30 sec: "+newDate);
		SimpleDateFormat df2 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss z");
		if(date.getTime()<newDate.getTime()){
			System.out.println("======tag invalid====");
		}
	}
}
