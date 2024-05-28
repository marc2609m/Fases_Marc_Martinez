package com.example.servicesimpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Tapa;
import com.example.repository.TapaRepository;
import com.example.services.TapaServices;

@Service
public class TapaServicesImpl implements TapaServices{

	@Autowired
	private TapaRepository tapaRepository;
	
	@Override
	@Transactional
	public Long create(Tapa t) {
		if(t.getId() != null) {
			throw new IllegalStateException("No se puede crear un producto con código not null");
		}
		
		Long id = System.currentTimeMillis();
		t.setId(id);
		
		tapaRepository.save(t);
		
		return id;
	}

	@Override
	public Optional<Tapa> read(Long id) {
		return tapaRepository.findById(id);
	}

	@Override
	@Transactional
	public void update(Tapa t) {
		Long id = t.getId();
		
		if(id == null) {
			throw new IllegalStateException("No se puede actualizar un producto con código not null");
		}
		
		boolean existe = tapaRepository.existsById(id);
		
		if(!existe) {
			throw new IllegalStateException("El producto con código " + id + " no existe. No se puede actualizar.");
		}
		
		tapaRepository.save(t);
		
	}

	@Override
	@Transactional
	public void delete(Long id) {
		tapaRepository.deleteById(id);
		
	}

	@Override
	public List<Tapa> getAll() {
		List<Tapa> tapas = tapaRepository.findAll();
		System.err.println(tapas);
		return tapas;
	}

}
