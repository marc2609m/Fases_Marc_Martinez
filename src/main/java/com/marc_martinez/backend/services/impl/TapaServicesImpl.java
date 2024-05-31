package com.marc_martinez.backend.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marc_martinez.backend.model.Tapa;
import com.marc_martinez.backend.repository.TapaRepository;
import com.marc_martinez.backend.services.TapaServices;

@Service
public class TapaServicesImpl implements TapaServices{
	
	@Autowired
	private TapaRepository tapaRepository;

	@Override
	public Long create(Tapa tapa) {
		if(tapa.getId() != null) {
			throw new IllegalStateException("No se puede crear un producto con código not null");
		}
		
		Long id = System.currentTimeMillis();
		tapa.setId(id);

		tapaRepository.save(tapa); 
		return id;
	}

	@Override
	public Optional<Tapa> read(Long id) {
		return tapaRepository.findById(id);
	}

	@Override
	public List<Tapa> getAll() {
		return tapaRepository.findAll();
	}
}
