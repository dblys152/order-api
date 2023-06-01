package com.ys.order.domain.core;

import com.ys.refs.user.domain.UserId;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class Orderer {

    @NotNull
    UserId userId;
    @NotNull
    String name;
    @NotNull
    String phone;
}
