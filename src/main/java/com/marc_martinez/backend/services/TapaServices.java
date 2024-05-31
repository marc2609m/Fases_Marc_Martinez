package com.marc_martinez.backend.services;

import java.util.List;
import java.util.Optional;

import com.marc_martinez.backend.model.Tapa;


public interface TapaServices {

	Long create(Tapa tapa);           //C
	Optional<Tapa> read(Long id);     //R
	List<Tapa> getAll();
	
}
