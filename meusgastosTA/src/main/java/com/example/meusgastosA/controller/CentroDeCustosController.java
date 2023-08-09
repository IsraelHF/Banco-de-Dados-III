package com.example.meusgastosA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.meusgastosA.domain.dto.centrodecusto.CentroDeCustoRequestDTO;
import com.example.meusgastosA.domain.dto.centrodecusto.CentroDeCustoResponseDTOP;
import com.example.meusgastosA.domain.service.CentroDeCustoService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/centrodecustos")
public class CentroDeCustosController {
    @Autowired
    private CentroDeCustoService centroDeCustoService;

    @GetMapping
    public ResponseEntity<List<CentroDeCustoResponseDTOP>> obtertodos() {
        return ResponseEntity.ok(centroDeCustoService.obterTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CentroDeCustoResponseDTOP> obterPorId(@PathVariable Long id) {
        return ResponseEntity.ok(centroDeCustoService.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<CentroDeCustoResponseDTOP> cadastrar(@RequestBody CentroDeCustoRequestDTO dto) {
        CentroDeCustoResponseDTOP responseDTOP = centroDeCustoService.cadastrar(dto);
        return new ResponseEntity<>(responseDTOP, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CentroDeCustoResponseDTOP> atualizar(@PathVariable Long id,
            @RequestBody CentroDeCustoRequestDTO dto) {
        CentroDeCustoResponseDTOP responseDTOP = centroDeCustoService.atualizar(id, dto);
        return new ResponseEntity<>(responseDTOP, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
