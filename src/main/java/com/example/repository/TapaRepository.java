package com.example.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Tapa;

public interface TapaRepository extends JpaRepository<Tapa, Long>{
	List<Tapa> findByPrecioBetweenOrderByPrecioAsc(double min, double max);
}
