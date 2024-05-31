package com.marc_martinez.backend.restcontroller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marc_martinez.backend.config.RespuestaError;
import com.marc_martinez.backend.model.Tapa;
import com.marc_martinez.backend.services.TapaServices;

@RestController
@RequestMapping("/tapas")
public class TapaController {
	
	@Autowired
	private TapaServices tapaServices;
	
	@GetMapping("/tapa")
	public List<Tapa> getAll(){
		return tapaServices.getAll();
	}

	@GetMapping("/tapa/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		
		if(id > 500) {
			throw new RuntimeException("El número " + id + " no es válido.");
		}
		
		Optional<Tapa> optional = tapaServices.read(id);
		
		if (optional.isEmpty()) {
			RespuestaError respuestaError = new RespuestaError("No se encuentra la tapa con id " + id);
			return new ResponseEntity<>(respuestaError, HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(optional.get());
	}
	
	@ExceptionHandler({IllegalArgumentException.class, ClassCastException.class})
	public ResponseEntity<?> gestor1(Exception e){
		return ResponseEntity.badRequest().body(new RespuestaError(e.getMessage()));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> gestor2(Exception e){
		return ResponseEntity.badRequest().body(new RespuestaError(e.getMessage()));
	}
}
