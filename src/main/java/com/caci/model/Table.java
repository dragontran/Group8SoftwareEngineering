package main.java.com.caci.model;

import java.io.File;
import java.io.IOException;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import main.java.com.caci.resources.checksum.Checksum;

public class Table {
	
	private final SimpleObjectProperty<File> file; 
	private final SimpleStringProperty rName;
	private final SimpleLongProperty rSize;
	private final SimpleStringProperty rPath;
	//private final SimpleLongProperty rChecksum;
	Checksum checksum;
	
	public Table(File file){
		try {
			checksum = new Checksum(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.file = new SimpleObjectProperty<File>(file);
		this.rName = new SimpleStringProperty(file.getName());
		this.rSize = new SimpleLongProperty(checksum.getCheckSum());
		this.rPath = new SimpleStringProperty(file.getAbsolutePath());
		//this.rChecksum = new SimpleLongProperty(checksum.getCheckSum());
	}

	public File getFile() {
		return file.get();
	}
	
	public String getFileName() {
		return rName.get();
	}
	
	/*public long getChecksum(){
		return rChecksum.get();
	}*/
	public long getFileSize() {
		return rSize.get();
	}
	
	public String getFilePath() {
		return rPath.get();
	}
	
	public void setFile(File file) {
		this.file.set(file);
	}
	
	public void setFileName(File file) {
		rName.set(file.getName());
	}
	/*public void setFileSize(File file) {
		rSize.set(file.length());
	}*/
	
	/*public void setFilePath(File file) {
		rPath.set(file.getAbsolutePath());
	}*/
	
	@Override
	public boolean equals(Object o) {
	    if (o == this) return true;
	    if (!(o instanceof Table)) {
	        return false;
	    }
	    Table element = (Table) o;

	    return element.file.isEqualTo(file).get() &&
	        element.rName.isEqualTo(rName).get(); //&&
	        //element.rSize.isEqualTo(rSize).get(); //&&
	        //element.rPath.isEqualTo(rPath).get();
	}
}
