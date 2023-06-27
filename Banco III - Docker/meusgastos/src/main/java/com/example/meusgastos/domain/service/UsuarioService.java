package com.example.meusgastos.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.meusgastos.domain.dto.UsuarioRequestDTO;
import com.example.meusgastos.domain.dto.UsuarioResponseDTO;
import com.example.meusgastos.domain.exception.BadRequestException;
import com.example.meusgastos.domain.exception.ResourceNotFoundException;
import com.example.meusgastos.domain.model.Usuario;
import com.example.meusgastos.domain.repository.UserRepository;

@Service
public class UsuarioService implements
        ICRUDService<UsuarioRequestDTO, UsuarioResponseDTO> {
    @Autowired
    private UserRepository usuarioRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<UsuarioResponseDTO> obterTodos() {
        // TODO Auto-generated method stub
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> mapper.map(usuario, UsuarioResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO obterPorId(Long id) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        if (optUsuario.isEmpty()) {
            // lançar uma exceção
            throw new ResourceNotFoundException("Não foi possível encontrar o usuário com o id:");
        }
        return mapper.map(optUsuario.get(), UsuarioResponseDTO.class);
    }

    @Override
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        if (dto.getEmail() == null || dto.getSenha() == null) {
            throw new BadRequestException("Email e Senha são obrigatórios");
        }
        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(dto.getEmail());
        if (optUsuario.isPresent()) {
            throw new BadRequestException("Já existe um usuário cadastrado com esse email: " + dto.getEmail());
        }
        Usuario usuario = mapper.map(dto, Usuario.class);
        usuario.setDatacadastro(new Date());
        // encriptografar senha
        usuario = usuarioRepository.save(usuario);// salva usuario no banco
        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    @Override
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        obterPorId(id);
        if (dto.getEmail() == null || dto.getSenha() == null) {
            throw new BadRequestException("Email e Senha são obrigatórios");
        }
        Usuario usuario = mapper.map(dto, Usuario.class);
        usuario.setId(id);
        
        usuario = usuarioRepository.save(usuario);
        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    @Override
    public void deletar(Long id) {
        //APAGAR DA BASE
        //obterPorId(id);
        //usuarioRepository.deleteById(id);
        //INATIVAR

        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
         if (optUsuario.isEmpty()) {
            // lançar uma exceção
            throw new ResourceNotFoundException("Não foi possível encontrar o usuário com o id:");
        }
        
        Usuario usuario = optUsuario.get();
        usuario.setDataInativacao(new Date());
        usuarioRepository.save(usuario);

    }

}