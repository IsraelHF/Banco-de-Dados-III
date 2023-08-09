package com.example.meusgastosA.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.meusgastosA.domain.model.CentroDeCusto;
import com.example.meusgastosA.domain.model.Usuario;

public interface CentroDeCustosRepository extends JpaRepository<CentroDeCusto, Long> {

    List<CentroDeCusto> findByUsuario(Usuario usuario);

}
