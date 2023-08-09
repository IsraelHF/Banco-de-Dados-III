package com.example.meusgastosA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.meusgastosA.domain.dto.dashboard.DashBoardResponseDTO;
import com.example.meusgastosA.domain.service.DashBoardService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/dashboard")
public class DashBoardController {
    @Autowired
    private DashBoardService dashboardService;

    @GetMapping
    public ResponseEntity<DashBoardResponseDTO> obterFluxoDeCaixa(
            @RequestParam(name = "periodoInicial") String periodoInicial,
            @RequestParam(name = "periodoFinal") String periodoFinal) {
        return ResponseEntity.ok(dashboardService.obterFluxoDeCaixa(periodoInicial, periodoFinal));
    }
}