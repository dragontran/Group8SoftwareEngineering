package main.java.com.caci.model;

import java.io.File;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Table {
	
	private final SimpleObjectProperty<File> file; 
	private final SimpleStringProperty rName;
	private final SimpleLongProperty rSize;
	private final SimpleStringProperty rPath;
	//private final SimpleStringProperty rChecksum;
	
	public Table(File file){
		this.file = new SimpleObjectProperty<File>(file);
		this.rName = new SimpleStringProperty(file.getName());
		this.rSize = new SimpleLongProperty(file.length());
		this.rPath = new SimpleStringProperty(file.getAbsolutePath());
		//this.rChecksum = new SimpleStringProperty();
	}

	// GETTERS AND SETTERS
	public SimpleObjectProperty<File> getFile() {
		return file;
	}

	public SimpleStringProperty getrName() {
		return rName;
	}

	public SimpleLongProperty getrSize() {
		return rSize;
	}

	public SimpleStringProperty getrPath() {
		return rPath;
	}
	
	public void setFile(File file) {
		this.file.set(file);
	}
	
	public void setFileName(File file) {
		rName.set(file.getName());
	}
	
	public void setFileSize(File file) {
		rSize.set(file.length());
	}
	
	public void setFilePath(File file) {
		rPath.set(file.getAbsolutePath());
	}
	
	@Override
	public boolean equals(Object o) {
	    if (o == this) return true;
	    if (!(o instanceof Table)) {
	        return false;
	    }
	    Table element = (Table) o;

	    return element.file.isEqualTo(file).get() &&
	        element.rName.isEqualTo(rName).get() &&
	        element.rSize.isEqualTo(rSize).get() &&
	        element.rPath.isEqualTo(rPath).get();
	}
}
