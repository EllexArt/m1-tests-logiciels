package fr.rouen.mastergil.tptest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class BankAccountUnitTest {

    @Test
    public void shouldCreateABankAccountWithNoValuesSuccessfully() {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        //WHEN
        bankAccount.creerCompte();
        //THEN
        assertThat(bankAccount.solde).isNotNull();
    }

    @Test
    public void shouldCreateABankAccountWithValidValuesSuccessfully() {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        int amount = 10;
        Devise devise = Devise.DOLLAR;
        //WHEN
        bankAccount.creerCompte(amount, devise);
        //THEN
        assertThat(bankAccount.solde).isNotNull();
        assertThat(bankAccount.solde.getMontant()).isEqualTo(amount);
        assertThat(bankAccount.solde.getDevise()).isEqualTo(devise);
    }

    @Test
    public void shouldDisplayPayWithAValidAmountAndDeviseSuccessfully() {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        int amount = 10;
        Devise devise = Devise.DOLLAR;
        bankAccount.solde = new Money(amount, devise);
        //WHEN
        String displayPay = bankAccount.consulterSolde();
        //THEN
        assertThat(displayPay).matches("Votre solde actuel est de "
                + bankAccount.solde.getMontant() + " " + bankAccount.solde.getDevise().name());
    }

    @Test
    public void shouldDisplayPayWithNoAmountAndDefaultDeviseSucessfully() {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        bankAccount.solde = new Money();
        //WHEN
        String displayPay = bankAccount.consulterSolde();
        //THEN
        assertThat(displayPay).matches("Votre solde actuel est de "
                + bankAccount.solde.getMontant() + " " + bankAccount.solde.getDevise().name());
    }

    @Test
    public void shouldDepositMoneySuccessfullyWithValidAmount() {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        bankAccount.solde = new Money();
        int amount = 10;
        //WHEN
        bankAccount.deposerArgent(amount);
        //THEN
        assertThat(bankAccount.solde.getMontant()).isEqualTo(amount);
    }

    @Test
    public void shouldDepositMoneySuccessfullyWithValidAmountAndAnInitialAmount() {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        int amount = 10;
        bankAccount.solde = new Money(amount, Devise.DINAR);
        //WHEN
        bankAccount.deposerArgent(amount);
        //THEN
        assertThat(bankAccount.solde.getMontant()).isEqualTo(2 * amount);
    }

    @Test
    public void shouldWithdrawMoneySuccessfullyWithValidAmountAndAnInitialAmount() {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        int amount = 10;
        bankAccount.solde = new Money(amount, Devise.DINAR);
        //WHEN
        bankAccount.retirerArgent(amount);
        //THEN
        assertThat(bankAccount.solde.getMontant()).isEqualTo(0);
    }

    @Test
    public void shouldWithdrawMoneySuccessfullyWithInvalidAmountAndAnInitialAmount() {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        int initialAmount = 10;
        bankAccount.solde = new Money(initialAmount, Devise.DINAR);
        int withdrawAmount = 15;
        //WHEN
        bankAccount.retirerArgent(withdrawAmount);
        //THEN
        assertThat(bankAccount.solde.getMontant()).isEqualTo(initialAmount - withdrawAmount);
    }

    @Test
    public void shouldWithdrawMoneySuccessfullyWithNoInitialAmount() {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        bankAccount.solde = new Money();
        int withdrawAmount = 15;
        //WHEN
        bankAccount.retirerArgent(withdrawAmount);
        //THEN
        assertThat(bankAccount.solde.getMontant()).isEqualTo( - withdrawAmount);
    }

    @Test
    public void shouldNotBePositivePay() {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        bankAccount.solde = new Money(-5, Devise.DINAR);
        //WHEN
        boolean isPositive = bankAccount.isSoldePositif();
        //THEN
        assertThat(isPositive).isFalse();
    }

    @Test
    public void shouldBePositivePay() {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        bankAccount.solde = new Money(5, Devise.DINAR);
        //WHEN
        boolean isPositive = bankAccount.isSoldePositif();
        //THEN
        assertThat(isPositive).isTrue();
    }
}