package com.ddokddak.member.dto;

import com.ddokddak.member.entity.AuthProviderType;
import com.ddokddak.member.entity.RoleType;
import com.ddokddak.member.entity.Status;
import com.ddokddak.member.entity.TemplateType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record MemberResponse(
        @NotNull @Size(min = 5, max = 100) String email,
        @NotNull @Size(min = 1, max = 50) String nickname,
        RoleType role,
        Status status,
        AuthProviderType authProviderType,
        TemplateType templateType
) {
    @Builder
    public MemberResponse {}
}
