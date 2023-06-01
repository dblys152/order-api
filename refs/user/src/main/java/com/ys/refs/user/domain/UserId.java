package com.ys.refs.user.domain;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value(staticConstructor = "of")
public class UserId {

    @NotNull
    String id;
}
