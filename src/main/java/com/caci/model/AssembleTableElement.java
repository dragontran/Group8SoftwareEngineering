package main.java.com.caci.model;

import java.io.File;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class AssembleTableElement {
	private SimpleObjectProperty<File> file;
	private SimpleStringProperty fileName;
	private SimpleLongProperty fileSize;
	private SimpleStringProperty filePath;
	
	public AssembleTableElement(File file) {
		this.file = new SimpleObjectProperty<File> (file);
		this.fileName = new SimpleStringProperty (file.getName());
		this.fileSize = new SimpleLongProperty (file.length());
		this.filePath = new SimpleStringProperty (file.getAbsolutePath());
	}
	
	public File getFile() {
		return file.get();
	}
	
	public String getFileName() {
		return fileName.get();
	}
	
	public long getFileSize() {
		return fileSize.get();
	}
	
	public String getFilePath() {
		return filePath.get();
	}
	
	public void setFile(File file) {
		this.file.set(file);
	}
	
	public void setFileName(File file) {
		fileName.set(file.getName());
	}
	
	public void setFileSize(File file) {
		fileSize.set(file.length());
	}
	
	public void setFilePath(File file) {
		filePath.set(file.getAbsolutePath());
	}
	
	@Override
	public boolean equals(Object o) {
	    if (o == this) return true;
	    if (!(o instanceof AssembleTableElement)) {
	        return false;
	    }
	    AssembleTableElement element = (AssembleTableElement) o;

	    return element.file.isEqualTo(file).get() &&
	        element.fileName.isEqualTo(fileName).get() &&
	        element.fileSize.isEqualTo(fileSize).get() &&
	        element.filePath.isEqualTo(filePath).get();
	}
}

