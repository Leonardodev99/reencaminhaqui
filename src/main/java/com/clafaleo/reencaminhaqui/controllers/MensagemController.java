package com.clafaleo.reencaminhaqui.controllers;

import com.clafaleo.reencaminhaqui.entities.Mensagem;
import com.clafaleo.reencaminhaqui.entities.dto.MensagemDTO;
import com.clafaleo.reencaminhaqui.entities.dto.MensagemUpdateDTO;
import com.clafaleo.reencaminhaqui.mapper.MensagemMapper;
import com.clafaleo.reencaminhaqui.services.MensagemService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mensagens")
public class MensagemController {

    private final MensagemService service;

    public MensagemController(MensagemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MensagemDTO> criar(@Valid @RequestBody MensagemDTO dto) {
        Mensagem entidade = MensagemMapper.toEntity(dto);
        Mensagem salvo = service.salvar(entidade);
        MensagemDTO resposta = MensagemMapper.toDto(salvo);
        return ResponseEntity.created(URI.create("/api/mensagens/" + salvo.getId())).body(resposta);
    }

    @GetMapping
    public ResponseEntity<?> listar(@RequestParam(value = "rotaId", required = false) Long rotaId,
                                    Pageable pageable) {
        if (rotaId != null) {
            if (pageable.isPaged()) {
                Page<Mensagem> page = service.listarPorRota(rotaId, pageable);
                Page<MensagemDTO> dtoPage = page.map(MensagemMapper::toDto);
                return ResponseEntity.ok(dtoPage);
            } else {
                List<MensagemDTO> list = service.listarPorRota(rotaId).stream()
                        .map(MensagemMapper::toDto)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(list);
            }
        } else {
            if (pageable.isPaged()) {
                Page<Mensagem> page = service.listarTodos(pageable);
                Page<MensagemDTO> dtoPage = page.map(MensagemMapper::toDto);
                return ResponseEntity.ok(dtoPage);
            } else {
                List<MensagemDTO> list = service.listarTodos().stream()
                        .map(MensagemMapper::toDto)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(list);
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensagemDTO> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(MensagemMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensagemDTO> atualizar(@PathVariable Long id, @Valid @RequestBody MensagemDTO dto) {
        Mensagem entidade = MensagemMapper.toEntity(dto);
        Mensagem atualizado = service.atualizar(id, entidade);
        return ResponseEntity.ok(MensagemMapper.toDto(atualizado));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MensagemDTO> atualizarParcial(@PathVariable Long id, @RequestBody MensagemUpdateDTO dto) {
        Mensagem atualizado = service.atualizarParcial(id, dto);
        return ResponseEntity.ok(MensagemMapper.toDto(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}