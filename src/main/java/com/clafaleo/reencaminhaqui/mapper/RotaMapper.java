package com.clafaleo.reencaminhaqui.mapper;

import com.clafaleo.reencaminhaqui.entities.Canal;
import com.clafaleo.reencaminhaqui.entities.Rota;
import com.clafaleo.reencaminhaqui.entities.dto.RotaDTO;

import java.util.List;
import java.util.stream.Collectors;

public final class RotaMapper {

    private RotaMapper() {}

    public static Rota toEntity(RotaDTO dto) {
        if (dto == null) return null;
        Rota rota = new Rota();
        rota.setId(dto.getId());
        rota.setNumeroOrigem(dto.getNumeroOrigem());
        rota.setNumeroDestino(dto.getNumeroDestino());
        rota.setCriadoEm(dto.getCriadoEm());
        rota.setStatusRota(dto.getStatusRota());

        if (dto.getCanalId() != null) {
            Canal c = new Canal();
            c.setId(dto.getCanalId());
            rota.setCanal(c);
        } else {
            rota.setCanal(null);
        }

        return rota;
    }

    public static RotaDTO toDto(Rota entity) {
        if (entity == null) return null;
        return new RotaDTO(entity);
    }

    public static List<RotaDTO> toDtoList(List<Rota> rotas) {
        return rotas == null ? List.of() : rotas.stream().map(RotaMapper::toDto).collect(Collectors.toList());
    }
}