package com.example.meusgastosA.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.meusgastosA.domain.Enum.ETipoTitulo;
import com.example.meusgastosA.domain.dto.dashboard.DashBoardResponseDTO;
import com.example.meusgastosA.domain.dto.titulos.TituloResponseDTO;

@Service
public class DashBoardService {
    @Autowired
    private TituloService tituloService;

    public DashBoardResponseDTO obterFluxoDeCaixa(String periodoInicial, String periodoFinal) {
        List<TituloResponseDTO> titulos = tituloService.obterPorDataDeVencimento(periodoInicial, periodoFinal);
        Double totalPagar = 0.0;
        Double totalReceber = 0.0;
        List<TituloResponseDTO> titulosPagar = new ArrayList<>();
        List<TituloResponseDTO> titulosReceber = new ArrayList<>();
        Double saldo = 0.0;
        for (TituloResponseDTO titulo : titulos) {
            if (titulo.getTipo() == ETipoTitulo.APAGAR) {
                totalPagar += titulo.getValor();
                titulosPagar.add(titulo);
            } else {
                totalReceber += titulo.getValor();
                titulosReceber.add(titulo);
            }
        }
        saldo = totalReceber - totalPagar;
        return new DashBoardResponseDTO(totalPagar, totalReceber, saldo, titulosPagar, titulosReceber);
    }
}