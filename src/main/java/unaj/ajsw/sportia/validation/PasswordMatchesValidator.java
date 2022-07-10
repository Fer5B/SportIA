package unaj.ajsw.sportia.validation;

import unaj.ajsw.sportia.dto.DataUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, DataUser> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(DataUser dataUser, ConstraintValidatorContext constraintValidatorContext) {
        return dataUser.getPassword().equals(dataUser.getMatchingPassword());
    }
}
