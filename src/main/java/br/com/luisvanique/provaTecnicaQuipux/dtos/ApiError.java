package br.com.luisvanique.provaTecnicaQuipux.dtos;

import lombok.Builder;

import java.util.List;

@Builder
public record ApiError(
        List<String> errors,
        String status,
        Integer code
) {
}
