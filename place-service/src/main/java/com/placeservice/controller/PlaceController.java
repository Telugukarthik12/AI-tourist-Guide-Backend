package com.placeservice.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.placeservice.dto.PlaceDTO;
import com.placeservice.service.PlaceService;

@RestController
@CrossOrigin(origins = "*")	//Frontend Connection
public class PlaceController {

	@Autowired
	public PlaceService placeservice;

	@PostMapping("/addplace")
	public ResponseEntity<String> addPlace(@RequestBody PlaceDTO placeDto) {
		return placeservice.addPlace(placeDto);
	}

	@GetMapping("/getallplaces")
	public ResponseEntity<List<PlaceDTO>> getAllPlaces() {
		return placeservice.getAllPlaces();
	}

	@GetMapping("/getplacebyid/{placeId}")
	public ResponseEntity<PlaceDTO> getPlaceById(@PathVariable(name = "placeId") Integer placeId) throws Throwable {
		return placeservice.getPlaceById(placeId);
	}

	@GetMapping("/getplacebyname/{placeName}")
	public ResponseEntity<List<PlaceDTO>> getPlaceByName(@PathVariable(name = "placeName") String placeName)
			throws Throwable {
		return placeservice.getPlaceByName(placeName);
	}

	@GetMapping("/getplacebytags/{tags}")
	public ResponseEntity<PlaceDTO> getPlaceByTags(@PathVariable(name = "tags") String tags) throws Throwable {
		return placeservice.getPlaceByTags(tags);
	}

	@PutMapping("/updateplaces/")
	public ResponseEntity<String> updatePlaces(@RequestBody PlaceDTO placeDto) throws Throwable {
		return placeservice.updatePlaces(placeDto);
	}

	@DeleteMapping("/deletePlaceByName/{placeName}")
	public ResponseEntity<String> deletePlaceByName(@PathVariable(name = "placeName") String placeName) throws Throwable {
		return placeservice.deletePlaceByPlaceName(placeName);
	}
	
	@DeleteMapping("/deletePlaceByPlaceId")
	public ResponseEntity<String> deletePlaceById(@RequestParam Integer placeId) throws Throwable {
		return placeservice.deletePlaceById(placeId);
	}
	
//	client methods 

	@GetMapping("/place/getplacefortour/{placeId}")
	public Integer getPlacefortour(@PathVariable(name = "placeId") Integer placeId) throws Throwable {
		return placeservice.getPlacefortour(placeId);
	}

	@PostMapping("/place/getListOfPlacedtoById")
	public ResponseEntity<List<PlaceDTO>> getListOfPlaceDTOByPlaceId(@RequestBody List<Integer> placeIds)
			throws Throwable {
		return placeservice.getListOfPlaceDTOByPlaceId(placeIds);
	}

//	returns list of selected placeId's
	@PostMapping("/place/getListOfPlaceIds")
	public ResponseEntity<List<Integer>> getListOfPlaceIds(@RequestBody List<Integer> placeIds) throws Throwable {
		return placeservice.getListOfPlaceIdsl(placeIds);
	}
	
	
	 @PostMapping("/{placeId}/upload-image")
	    public String handleImageUpload(
	        @PathVariable int placeId,
	        @RequestParam("image") MultipartFile image) {

	        if (image != null) {
	            try {
	                // Generate a unique filename for the image to avoid naming conflicts
	                String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();

	                // Set the path where you want to save the image
	                String imagePath = "C:/Users/telug/Downloads/Lavan/" + filename;

	                File imageFile = new File(imagePath);

	                // Save the image file
	                image.transferTo(imageFile);

	                // Link the image path to the product in your database
	                placeservice.linkImageToPlace(placeId, imagePath);

	                return "Image uploaded successfully";
	            } catch (IOException e) {
	                return "Failed to upload image";
	            }
	        } else {
	            return "No image file provided";
	        }
	    }
	
}
