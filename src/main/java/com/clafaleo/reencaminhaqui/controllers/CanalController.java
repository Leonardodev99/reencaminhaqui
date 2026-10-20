package com.clafaleo.reencaminhaqui.controllers;

import com.clafaleo.reencaminhaqui.entities.dto.CanalDTO;
import com.clafaleo.reencaminhaqui.entities.dto.CanalUpdateDTO;
import com.clafaleo.reencaminhaqui.entities.enums.StatusCanal;
import com.clafaleo.reencaminhaqui.mapper.CanalMapper;
import com.clafaleo.reencaminhaqui.services.CanalService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/canais")
public class CanalController {

    private final CanalService service;

    public CanalController(CanalService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CanalDTO> criar(@Valid @RequestBody CanalDTO dto) {
        var entidade = CanalMapper.toEntity(dto);
        var salvo = service.salvar(entidade);
        var resposta = CanalMapper.toDto(salvo);
        return ResponseEntity.created(URI.create("/api/canais/" + salvo.getId())).body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CanalDTO> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(CanalMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CanalDTO>> listar(@RequestParam(required = false) StatusCanal status) {
        var canais = (status != null) ? service.buscarPorStatus(status) : service.listarTodos();
        return ResponseEntity.ok(canais.stream().map(CanalMapper::toDto).toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CanalDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CanalDTO dto) {
        var entidade = CanalMapper.toEntity(dto);
        var atualizado = service.atualizar(id, entidade);
        return ResponseEntity.ok(CanalMapper.toDto(atualizado));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CanalDTO> atualizarParcial(@PathVariable Long id,
                                                    @Valid @RequestBody CanalUpdateDTO dto) {
        return ResponseEntity.ok(CanalMapper.toDto(service.atualizarParcial(id, dto)));
    }
}