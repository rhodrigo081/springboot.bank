package com.example.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPF, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null) {

            return true;
        }

        String cleanedCpf = cpf.replaceAll("[^0-9]", "");

        if (!validateFormat(cleanedCpf)) {
            return false;
        }

        char dig10 = calculateCheckDigit(cleanedCpf.substring(0, 9), 10);
        char dig11 = calculateCheckDigit(cleanedCpf.substring(0, 10), 11);

        return (dig10 == cleanedCpf.charAt(9)) && (dig11 == cleanedCpf.charAt(10));
    }

    private boolean validateFormat(String cpf) {
        if (cpf.length() != 11) {
            return false;
        }

        return !cpf.matches("(\\d)\\1{10}");
    }

    private char calculateCheckDigit(String subCpf, int multiplier) {
        int sum = 0;
        int currentMultiplier = multiplier;

        for (int i = 0; i < subCpf.length(); i++) {
            int digit = Character.getNumericValue(subCpf.charAt(i));
            sum += (digit * currentMultiplier);
            currentMultiplier--;
        }

        int remainder = 11 - (sum % 11);

        if (remainder == 10 || remainder == 11) {
            return '0';
        } else {
            return (char) (remainder + '0');
        }
    }
}