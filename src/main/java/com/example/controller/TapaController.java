package com.example.controller;

import java.net.URI;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.config.PresentationException;
import com.example.model.Tapa;
import com.example.services.TapaServices;

@RestController
@RequestMapping("/tapas")
public class TapaController {
	
	@Autowired
	private TapaServices tapaServices;
	
	@GetMapping
	public List<Tapa> getAll(@RequestParam(name = "min", required = false) Double min, 
			 @RequestParam(name = "max", required = false) Double max){
		
		List<Tapa> tapas = null;

		if(min != null && max != null) {
			tapas = tapaServices.getBetweenPriceRange(min, max);
		} else {
			tapas = tapaServices.getAll();
		}
			
		return tapas;
	}
		
	@GetMapping("/{id}")
	public Tapa read(@PathVariable Long id) {
		
		Optional<Tapa> optional = tapaServices.read(id);
		
		if(!optional.isPresent()) {
			throw new PresentationException("No se encuentra el producto con id " + id, HttpStatus.NOT_FOUND);
		}
		
		return optional.get();
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Tapa t, UriComponentsBuilder ucb) {
		
		Long codigo = null;
		
		try {
			codigo = tapaServices.create(t);
		} catch(IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		
		URI uri = ucb.path("/tapas/{codigo}").build(codigo);
		
		return ResponseEntity.created(uri).build();
	}
		
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		
		try {
			tapaServices.delete(id);
		} catch(IllegalStateException e) {
			throw new PresentationException("No se encuentra el producto con id [" + id + "]. No se ha podido eliminar.", HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody Tapa t) {
		
		try {
			tapaServices.update(t);
		} catch(IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
}
