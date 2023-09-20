package com.motoservice.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.motoservice.entity.Moto;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Integer>{
	
	List<Moto> findByUsuarioId(int usuarioId);
}
