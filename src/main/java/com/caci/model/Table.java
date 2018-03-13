package main.java.com.caci.model;

import javafx.beans.property.SimpleStringProperty;

public class Table {
	
	private final SimpleStringProperty rName;
	private final SimpleStringProperty rSize;
	private final SimpleStringProperty rPath;
	private final SimpleStringProperty rChecksum;
	
	public Table(String rName, String rSize, String rPath, String rChecksum){
		this.rName = new SimpleStringProperty(rName);
		this.rSize = new SimpleStringProperty(rSize);
		this.rPath = new SimpleStringProperty(rPath);
		this.rChecksum = new SimpleStringProperty(rChecksum);
	}
	
	// GETTERS AND SETTERS
	public String getRName(){
		return rName.get();
	}
	public String getRSize(){
		return rSize.get();
	}
	public String getRPath(){
		return rPath.get();
	}
	public String getRChecksum(){
		return rChecksum.get();
	}
	public void setRName(String v){
		rName.set(v);
	}
	public void setRSize(String v){
		rSize.set(v);
	}
	public void setRPath(String v){
		rPath.set(v);
	}
	public void setRChecksum(String v){
		rChecksum.set(v);
	}
}
