package com.fullcycle.admin.catalogo.infrastructure.castmember.presenter;

import com.fullcycle.admin.catalogo.application.castmember.retrieve.get.CastMemberOutput;
import com.fullcycle.admin.catalogo.application.castmember.retrieve.list.CastMemberListOutput;
import com.fullcycle.admin.catalogo.infrastructure.castmember.models.CastMemberListResponse;
import com.fullcycle.admin.catalogo.infrastructure.castmember.models.CastMemberResponse;

public interface CastMemberPresenter {

    static CastMemberResponse present(CastMemberOutput aMember) {

        return new CastMemberResponse(
                aMember.id(),
                aMember.name(),
                aMember.type().name(),
                aMember.createdAt().toString(),
                aMember.updatedAt().toString()
        );
    }

    static CastMemberListResponse present(CastMemberListOutput aMember) {

        return new CastMemberListResponse(
                aMember.id(),
                aMember.name(),
                aMember.type().name(),
                aMember.createdAt().toString()
        );
    }
}
