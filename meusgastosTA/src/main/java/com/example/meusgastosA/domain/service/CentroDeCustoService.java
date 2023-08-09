package com.example.meusgastosA.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.meusgastosA.domain.dto.centrodecusto.CentroDeCustoRequestDTO;
import com.example.meusgastosA.domain.dto.centrodecusto.CentroDeCustoResponseDTOP;
import com.example.meusgastosA.domain.exception.ResourceNotFoundException;
import com.example.meusgastosA.domain.model.CentroDeCusto;
import com.example.meusgastosA.domain.model.Usuario;
import com.example.meusgastosA.domain.repository.CentroDeCustosRepository;

@Service
public class CentroDeCustoService implements ICRUDService<CentroDeCustoRequestDTO, CentroDeCustoResponseDTOP> {
    @Autowired
    private CentroDeCustosRepository centroDeCustosRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<CentroDeCustoResponseDTOP> obterTodos() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CentroDeCusto> lista = centroDeCustosRepository.findByUsuario(usuario);
        return lista.stream().map(centroDeCusto -> mapper.map(centroDeCusto, CentroDeCustoResponseDTOP.class))
                .collect(Collectors.toList());

    }

    @Override
    public CentroDeCustoResponseDTOP obterPorId(Long id) {
        System.out.println();
        Optional<CentroDeCusto> optCentroDeCusto = centroDeCustosRepository.findById(id);
        if (optCentroDeCusto.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possível encontrar o centro de custo com o id:  " + id);
        }
        return mapper.map(optCentroDeCusto.get(), CentroDeCustoResponseDTOP.class);
    }

    @Override
    public CentroDeCustoResponseDTOP cadastrar(CentroDeCustoRequestDTO dto) {
        CentroDeCusto centroDeCusto = mapper.map(dto, CentroDeCusto.class);
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        centroDeCusto.setUsuario(usuario);
        centroDeCusto.setId(null);
        centroDeCusto = centroDeCustosRepository.save(centroDeCusto);
        return mapper.map(centroDeCusto, CentroDeCustoResponseDTOP.class);
    }

    @Override
    public CentroDeCustoResponseDTOP atualizar(Long id, CentroDeCustoRequestDTO dto) {
        obterPorId(id);
        CentroDeCusto centroDeCusto = mapper.map(dto, CentroDeCusto.class);
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        centroDeCusto.setUsuario(usuario);
        centroDeCusto.setId(id);
        centroDeCusto = centroDeCustosRepository.save(centroDeCusto);
        return mapper.map(centroDeCusto, CentroDeCustoResponseDTOP.class);
    }

    @Override
    public void deletar(Long id) {
        obterPorId(id);
        centroDeCustosRepository.deleteById(id);
    }

}
