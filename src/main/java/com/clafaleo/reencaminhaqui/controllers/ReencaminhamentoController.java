package com.clafaleo.reencaminhaqui.controllers;

import com.clafaleo.reencaminhaqui.entities.Reencaminhamento;
import com.clafaleo.reencaminhaqui.entities.dto.ReencaminhamentoDTO;
import com.clafaleo.reencaminhaqui.entities.dto.ReencaminhamentoUpdateDTO;
import com.clafaleo.reencaminhaqui.mapper.ReencaminhamentoMapper;
import com.clafaleo.reencaminhaqui.services.ReencaminhamentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reencaminhamentos")
public class ReencaminhamentoController {

    private final ReencaminhamentoService service;

    public ReencaminhamentoController(ReencaminhamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ReencaminhamentoDTO> criar(@Valid @RequestBody ReencaminhamentoDTO dto) {
        Reencaminhamento entidade = ReencaminhamentoMapper.toEntity(dto);
        Reencaminhamento salvo = service.salvar(entidade);
        ReencaminhamentoDTO resposta = ReencaminhamentoMapper.toDto(salvo);
        return ResponseEntity.created(URI.create("/api/reencaminhamentos/" + salvo.getId())).body(resposta);
    }

    @GetMapping
    public ResponseEntity<?> listar(@RequestParam(value = "mensagemId", required = false) Long mensagemId,
                                    Pageable pageable) {
        if (mensagemId != null) {
            if (pageable.isPaged()) {
                Page<Reencaminhamento> page = service.listarPorMensagem(mensagemId, pageable);
                Page<ReencaminhamentoDTO> dtoPage = page.map(ReencaminhamentoMapper::toDto);
                return ResponseEntity.ok(dtoPage);
            } else {
                List<ReencaminhamentoDTO> list = service.listarPorMensagem(mensagemId).stream()
                        .map(ReencaminhamentoMapper::toDto)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(list);
            }
        } else {
            if (pageable.isPaged()) {
                Page<Reencaminhamento> page = service.listarTodos(pageable);
                Page<ReencaminhamentoDTO> dtoPage = page.map(ReencaminhamentoMapper::toDto);
                return ResponseEntity.ok(dtoPage);
            } else {
                List<ReencaminhamentoDTO> list = service.listarTodos().stream()
                        .map(ReencaminhamentoMapper::toDto)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(list);
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReencaminhamentoDTO> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ReencaminhamentoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReencaminhamentoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ReencaminhamentoDTO dto) {
        Reencaminhamento entidade = ReencaminhamentoMapper.toEntity(dto);
        Reencaminhamento atualizado = service.atualizar(id, entidade);
        return ResponseEntity.ok(ReencaminhamentoMapper.toDto(atualizado));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReencaminhamentoDTO> atualizarParcial(@PathVariable Long id, @RequestBody ReencaminhamentoUpdateDTO dto) {
        Reencaminhamento atualizado = service.atualizarParcial(id, dto);
        return ResponseEntity.ok(ReencaminhamentoMapper.toDto(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}