package org.university.payment_for_utilities.domains.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.abstract_class.TableInfo;
import org.university.payment_for_utilities.enumarations.TokenType;
import org.university.payment_for_utilities.pojo.responses.user.TokenResponse;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Table(name = "tokens")
public class Token extends TableInfo {
    @Column(unique = true)
    private String accessToken;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked;

    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    public RegisteredUser user;

    @Override
    public TokenResponse getResponse() {
        var responseBuilder = TokenResponse.builder();
        return super
                .responseBuilder(responseBuilder)
                .accessToken(this.accessToken)
                .user(this.user.getResponse())
                .build();
    }
}
