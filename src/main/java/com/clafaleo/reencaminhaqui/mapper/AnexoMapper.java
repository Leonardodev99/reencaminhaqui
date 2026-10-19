package com.clafaleo.reencaminhaqui.mapper;

import com.clafaleo.reencaminhaqui.entities.Anexo;
import com.clafaleo.reencaminhaqui.entities.Mensagem;
import com.clafaleo.reencaminhaqui.entities.dto.AnexoDTO;

import java.util.List;
import java.util.stream.Collectors;

public final class AnexoMapper {
    private AnexoMapper() {}

    public static Anexo toEntity(AnexoDTO dto) {
        if (dto == null) return null;
        Anexo a = new Anexo();
        a.setId(dto.getId());
        a.setUrlOrigem(dto.getUrlOrigem());
        a.setUrlLocalS3(dto.getUrlLocalS3());
        a.setMimeType(dto.getMimeType());
        if (dto.getMensagemId() != null) {
            Mensagem m = new Mensagem();
            m.setId(dto.getMensagemId());
            a.setMensagem(m);
        }
        return a;
    }

    public static AnexoDTO toDto(Anexo entity) {
        if (entity == null) return null;
        return new AnexoDTO(entity);
    }

    public static List<AnexoDTO> toDtoList(List<Anexo> list) {
        return list == null ? List.of() : list.stream().map(AnexoMapper::toDto).collect(Collectors.toList());
    }
}