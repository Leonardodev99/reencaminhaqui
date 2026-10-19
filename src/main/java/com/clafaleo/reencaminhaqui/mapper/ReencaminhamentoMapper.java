package com.clafaleo.reencaminhaqui.mapper;

import com.clafaleo.reencaminhaqui.entities.Mensagem;
import com.clafaleo.reencaminhaqui.entities.Reencaminhamento;
import com.clafaleo.reencaminhaqui.entities.dto.ReencaminhamentoDTO;

import java.util.List;
import java.util.stream.Collectors;

public final class ReencaminhamentoMapper {

    private ReencaminhamentoMapper() {}

    public static Reencaminhamento toEntity(ReencaminhamentoDTO dto) {
        if (dto == null) return null;
        Reencaminhamento r = new Reencaminhamento();
        r.setId(dto.getId());
        r.setTentativas(dto.getTentativas());
        r.setIdTransacaoProvedor(dto.getIdTransacaoProvedor());
        r.setMensagemErro(dto.getMensagemErro());
        r.setProcessadoEm(dto.getProcessadoEm());
        r.setStatusEnvio(dto.getStatusEnvio());
        if (dto.getMensagemId() != null) {
            Mensagem m = new Mensagem();
            m.setId(dto.getMensagemId());
            r.setMensagem(m);
        }
        return r;
    }

    public static ReencaminhamentoDTO toDto(Reencaminhamento entity) {
        if (entity == null) return null;
        return new ReencaminhamentoDTO(entity);
    }

    public static List<ReencaminhamentoDTO> toDtoList(List<Reencaminhamento> list) {
        return list == null ? List.of() : list.stream().map(ReencaminhamentoMapper::toDto).collect(Collectors.toList());
    }
}