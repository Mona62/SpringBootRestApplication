/*
 * FileMetaDataController handles to get the meta-data of the files uploaded 
 * using Rest URI's 
 * "/files/{fileId}" -- GET URI provides the requested file id meta-data
 */


package com.finra.spring.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.finra.spring.database.Database;
import com.finra.spring.model.FileMetadata;

@Controller
public class FileMetaDataController {

	
	@GetMapping(value="/files/{fileId}",produces="text/html")
	public String getFileMetaData(@RequestParam("fileId") long fileId,Model model){
		try{
		 model.addAttribute("filesMetaData", Database.getFilemetadata().get(fileId));
		 
		}catch(Exception e){
			model.addAttribute("message", "Could not find the file id.\nPlease check the file id again.");
		}
		 return "listFilesMetaData";
		
	}
	
//	@RequestMapping(value="/{id}", produces="application/json")
	@RequestMapping(value="/files/{fileId}")
	@ResponseBody
	public FileMetadata getFileMetaDataREST(@PathVariable("fileId") long fileId){
		System.out.println(Database.getFilemetadata().get(fileId));
		if(Database.getFilemetadata().get(fileId) == null){
			throw new com.finra.spring.Exception.FileNotExistException();
		}
		 return Database.getFilemetadata().get(fileId);
	
		
	}
	
	@ExceptionHandler(value = com.finra.spring.Exception.FileNotExistException.class)
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	@ResponseBody
	public String handleExceptionREST(com.finra.spring.Exception.FileNotExistException e){
		e.printStackTrace();
		return e.getMessage();
	}
	
	
	@GetMapping(value="/files/filesInLastHour",produces="text/html")
	public String getFilesInLastHour(Model model){
		Date now = new Date();
		List<FileMetadata> list = new ArrayList<>();
		try{
		 for(FileMetadata filemdata : Database.getFilemetadata().values())
		 {
			 Date date = filemdata.getCreated();;
			Double t = ((now.getTime() - date.getTime()) /1000.00/60.0);
			System.out.println(t);
			 if(t <=60 && t > 0){
				 list.add(filemdata);
			 }
		 }
		 model.addAttribute("filesMdata", list);
		}catch(Exception x){
			System.out.println("Exception occured to get last hr's files");
		}
	
		return "filesInLastHr";		
	}
	
	@RequestMapping(value="/files/filesInLastHour")
	@ResponseBody
	public List<FileMetadata> getFilesInLastHourREST(){
		Date now = new Date();
		List<FileMetadata> list = new ArrayList<>();
		try{
		 for(FileMetadata filemdata : Database.getFilemetadata().values())
		 {
			 Date date = filemdata.getCreated();;
			Double t = ((now.getTime() - date.getTime()) /1000.00)/60.0;
			 if(t <=60 && t > 0){
				 list.add(filemdata);
			 }
		 }
		}catch(Exception x){
			System.out.println("Exception occured to get last hr's files");
		}
	
		return list;
		
	}
	
	
	

	
	
}
