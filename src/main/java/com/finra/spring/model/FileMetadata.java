package com.finra.spring.model;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class FileMetadata {

	private int id ;
	private String fname;
	private String contentType;
	private Date created=new Date();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Date getCreated() {
		return created;
	}

//	public List<FileMetadata> getFilesInLastHour(){
//		
//	}
	@Override
	public String toString() {
		return "FileMetadata [id=" + id + ", fname=" + fname + ", contentType=" + contentType + ", created=" + created
				+ "]";
	}


	
	
}
