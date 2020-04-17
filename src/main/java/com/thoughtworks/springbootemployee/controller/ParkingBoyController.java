package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-boys")
public class ParkingBoyController {

	@Autowired
	private ParkingBoyService parkingBoyService;

	@GetMapping
	public List<ParkingBoy> getAllParkingBoys() {
		return parkingBoyService.gettAllParkingBoys();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ParkingBoy addNewParkingBoy(@RequestBody ParkingBoy parkingBoy) {
		return parkingBoyService.createParkingBoy(parkingBoy);
	}
}
