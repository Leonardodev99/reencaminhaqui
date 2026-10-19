package com.clafaleo.reencaminhaqui.controllers;

import com.clafaleo.reencaminhaqui.entities.Rota;
import com.clafaleo.reencaminhaqui.entities.dto.RotaDTO;
import com.clafaleo.reencaminhaqui.entities.dto.RotaUpdateDTO;
import com.clafaleo.reencaminhaqui.mapper.RotaMapper;
import com.clafaleo.reencaminhaqui.services.RotaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rotas")
public class RotaController {

    private final RotaService service;

    public RotaController(RotaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RotaDTO> criar(@Valid @RequestBody RotaDTO dto) {
        Rota rota = RotaMapper.toEntity(dto);
        Rota salvo = service.salvar(rota);
        RotaDTO resposta = RotaMapper.toDto(salvo);
        return ResponseEntity.created(URI.create("/api/rotas/" + salvo.getId())).body(resposta);
    }

    @GetMapping
    public ResponseEntity<?> listar(@RequestParam(value = "canalId", required = false) Long canalId,
                                    Pageable pageable) {
        if (canalId != null) {
            // retorna paginação quando o client fornece pageable params
            Page<Rota> page = service.listarPorCanal(canalId, pageable);
            Page<RotaDTO> dtoPage = page.map(RotaMapper::toDto);
            return ResponseEntity.ok(dtoPage);
        } else {
            // se o client forneceu parâmetros de paginação, devolve página; caso contrário, devolve lista completa
            if (pageable.isPaged()) {
                Page<Rota> page = service.listarTodos(pageable);
                Page<RotaDTO> dtoPage = page.map(RotaMapper::toDto);
                return ResponseEntity.ok(dtoPage);
            } else {
                List<RotaDTO> list = service.listarTodos().stream()
                        .map(RotaMapper::toDto)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(list);
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RotaDTO> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(RotaMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Rota não encontrada com id: " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RotaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody RotaDTO dto) {
        Rota entidade = RotaMapper.toEntity(dto);
        Rota atualizado = service.atualizar(id, entidade);
        return ResponseEntity.ok(RotaMapper.toDto(atualizado));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RotaDTO> atualizarParcial(@PathVariable Long id, @RequestBody RotaUpdateDTO dto) {
        Rota atualizado = service.atualizarParcial(id, dto);
        return ResponseEntity.ok(RotaMapper.toDto(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}