package com.fullcycle.admin.catalogo.application.castmember.delete;

import com.fullcycle.admin.catalogo.Fixture;
import com.fullcycle.admin.catalogo.IntegrationTest;
import com.fullcycle.admin.catalogo.domain.castmember.CastMember;
import com.fullcycle.admin.catalogo.domain.castmember.CastMemberGateway;
import com.fullcycle.admin.catalogo.domain.castmember.CastMemberID;
import com.fullcycle.admin.catalogo.infrastructure.castmember.persistence.CastMemberJpaEntity;
import com.fullcycle.admin.catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@IntegrationTest
class DeleteCastMemberUseCaseIT {

    @Autowired
    private DeleteCastMemberUseCase useCase;

    @Autowired
    private CastMemberRepository castMemberRepository;

    @SpyBean
    private CastMemberGateway castMemberGateway;

    @Test
    void givenAValidId_whenCallsDeleteCastMember_shouldDeleteIt() {
        // given
        final var aMember = CastMember.newMember(Fixture.name(), Fixture.CastMember.type());
        final var aMemberTwo = CastMember.newMember(Fixture.name(), Fixture.CastMember.type());

        final var expectedId = aMember.getId();

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));
        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMemberTwo));

        Assertions.assertEquals(2, this.castMemberRepository.count());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then
        verify(castMemberGateway).deleteById(eq(expectedId));

        Assertions.assertEquals(1, this.castMemberRepository.count());
        Assertions.assertFalse(this.castMemberRepository.existsById(expectedId.getValue()));
    }

    @Test
    void givenAnInvalidId_whenCallsDeleteCastMember_shouldBeOk() {
        // given
        final var aMember = CastMember.newMember(Fixture.name(), Fixture.CastMember.type());
        final var expectedId = CastMemberID.from("123");

        this.castMemberRepository.saveAndFlush(
                CastMemberJpaEntity.from(
                        CastMember.newMember(Fixture.name(), Fixture.CastMember.type())
                )
        );

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then
        verify(castMemberGateway).deleteById(eq(expectedId));
        Assertions.assertEquals(1, this.castMemberRepository.count());
    }

    @Test
    void givenAValidId_whenCallsDeleteCastMemberAndGatewayThrowsException_shouldReceiveException() {
        // given
        final var aMember = CastMember.newMember(Fixture.name(), Fixture.CastMember.type());

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        final var expectedId = aMember.getId();

        Assertions.assertEquals(1, this.castMemberRepository.count());

        // precisaria apenas reforçando
        doThrow(new IllegalStateException("Gateway error"))
                .when(castMemberGateway).deleteById(any());

        // when
        final var actualException = Assertions.assertThrows(
                IllegalStateException.class, () -> useCase.execute(expectedId.getValue())
        );

        // then
        verify(castMemberGateway).deleteById(eq(expectedId));

        Assertions.assertEquals(1, this.castMemberRepository.count());
    }
}
