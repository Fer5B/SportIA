package unaj.ajsw.sportia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unaj.ajsw.sportia.validation.PasswordMatches;
import unaj.ajsw.sportia.validation.ValidEmail;
import unaj.ajsw.sportia.validation.ValidPassword;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class DataUser {
    @NotNull
    @NotEmpty
    @Size(max = 32)
    private String name;
    @NotNull
    @NotEmpty
    @Size(max = 32)
    private String lastName;
    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;
    @ValidPassword
    private String password;
    @NotNull
    @NotEmpty
    private String matchingPassword;
}
