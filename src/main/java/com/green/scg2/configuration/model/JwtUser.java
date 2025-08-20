package com.green.scg2.configuration.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class JwtUser {
    private final long signedUserId;
    private final List<String> roles;
}
