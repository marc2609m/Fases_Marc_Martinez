package com.example.services;

import java.util.List;

import java.util.Optional;

import com.example.model.Tapa;

public interface TapaServices {
	Long create(Tapa t);	    // C
	
	Optional<Tapa> read(Long id);   // R
	
	void update(Tapa t);		// U

	void delete(Long id);				// D
	
	List<Tapa> getAll();
	
	List<Tapa> getBetweenPriceRange(double min, double max);
}
