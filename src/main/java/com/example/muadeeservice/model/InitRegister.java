package com.example.muadeeservice.model;

import com.example.commonapi.model.BasicRequest;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InitRegister extends BasicRequest {
    @NotNull
    private CardRegister cardRegister;
}
