/*
 * UploadController handles the files to be uploaded
 *"/uploadFile" GET URI -- gets the uploadForm page
 *"/uploadFile" POSt URI -- uploads the file in the form
 * "/files" GET URI -- gets all the list of uploaded files
 * "/downloadfiles/files/{filename:.+}" -- GET URI -- alows to download the files 
 */

package com.finra.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.finra.spring.database.Database;
import com.finra.spring.model.FileMetadata;
import com.finra.spring.service.StorageService;

@Controller
public class UploadController {

	@Autowired
	StorageService service;

	List<String> files = new ArrayList<String>();


	@GetMapping("/uploadFile")
	public String listUploadedFiles(Model model){
		return "uploadForm";
	}

	@PostMapping(value="/uploadFile",produces="text/html")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			Model model){
		try{
			service.store(file);
			try{
				FileMetadata metadata = new FileMetadata();
				metadata.setFname(file.getOriginalFilename());
				metadata.setId(Database.getFilemetadata().size()+1);
				metadata.setContentType(file.getContentType());
				Database.getFilemetadata().put((long) metadata.getId(), metadata);
				System.out.println(Database.getFilemetadata().values());
			}catch(Exception e){
				System.out.println("To insert metadata into hashmap exception");
			}

			model.addAttribute("message", "Congrats!!!Successfully uploaded the file - " + file.getOriginalFilename());;
			files.add(file.getOriginalFilename());
		}catch(Exception e){
			System.out.println(e.getMessage());
			model.addAttribute("message","Failed to upload! -- " +file.getOriginalFilename());
		}
		return "uploadForm";

	}
	
	
	@PostMapping(value="/uploadFile")
	@ResponseBody
	public  String handleFileUploadREST(@RequestParam("file") MultipartFile file){
		String status = "not uploaded";
		int key = 0;
		try{
			service.store(file);
			try{
				FileMetadata metadata = new FileMetadata();
				metadata.setFname(file.getOriginalFilename());
				metadata.setId(Database.getFilemetadata().size()+1);
				key = metadata.getId();
				metadata.setContentType(file.getContentType());
				Database.getFilemetadata().put((long) metadata.getId(), metadata);
				System.out.println(Database.getFilemetadata().values());
			}catch(Exception e){
				System.out.println("To insert metadata into hashmap exception");
			}

				files.add(file.getOriginalFilename());
				status="File uploaded successfully - "+file.getOriginalFilename()+" and file Id is - "+key;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return status;

	}
	
	

	@GetMapping(value="/files",produces="text/html")
	public String getListFiles(Model model) {
		model.addAttribute("files",
				files.stream()
				.map(fileName -> MvcUriComponentsBuilder
						.fromMethodName(UploadController.class, "getFile", fileName).build().toString())
				.collect(Collectors.toList()));
		model.addAttribute("totalFiles", "TotalFiles: " + files.size());
		return "listFiles";
	}
	
	
	@RequestMapping(value="/files")
	@ResponseBody
	public  List<String> getListFilesREST() {
		
		return		files.stream()
				.map(fileName -> MvcUriComponentsBuilder
						.fromMethodName(UploadController.class, "getFile", fileName).build().toString())
				.collect(Collectors.toList());
		
		
	}


	

	@GetMapping("/downloadfiles/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = service.loadFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
}
