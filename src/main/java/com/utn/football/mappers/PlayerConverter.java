package com.utn.football.mappers;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerConverter
{
    @NotNull
    private String name;

    @NotNull
    private String lastname;
}
