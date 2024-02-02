package fr.rouen.mastergil.tptest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.*;

public class MoneyUnitAssertJTest {
    //GIVEN préparation d'netrée sortie, les valeurs
    // WHEN action (une seule ligne de code la fonction qu'on teste avec les valeurs qu'on teste)
    //THEN vérifications JUnit propose une bibliothèque d'assertions

    @Test
    public void shouldConstructMoneyWithZeroEuro() {
        //GIVEN
        int amount = 0;
        Devise currency = Devise.EURO;
        //WHEN
        Money money = new Money();

        //THEN
        assertThat(money.getMontant()).isEqualTo(amount);
        assertThat(money.getDevise()).isEqualTo(currency);
    }

    @Test
    public void shouldConstructMoneyWithAValidAmountAndCurrency() {
        //GIVEN
        int amount = 10;
        Devise currency = Devise.YEN;
        //WHEN
        Money money = new Money(amount, currency);
        //THEN
        assertThat(money.getMontant()).isEqualTo(amount);
        assertThat(money.getDevise()).isEqualTo(currency);
    }

    @Test
    public void shouldConstructMoneyWithAValidAmountAndNoCurrency() {
        //GIVEN
        int amount = 10;

        //THEN
        assertThatThrownBy(() ->
                //WHEN
                new Money(amount, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldReturnTrueIfAmountIsPositive() {
        //GIVEN
        int amount = 10;
        Money money = new Money(amount, Devise.EURO);
        //WHEN
        boolean result = money.isPositif();
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalseIfAmountIsNegative() {
        //GIVEN
        int amount = -10;
        Money money = new Money(amount, Devise.EURO);
        //WHEN
        boolean result = money.isPositif();
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void shouldSetCurrencyWithValidCurrency() {
        //GIVEN
        Devise currency = Devise.DINAR;
        Money money = new Money();
        //WHEN
        money.setDevise(currency);
        //THEN
        assertThat(money.getDevise()).isEqualTo(currency);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWithInvalidCurrency() {
        //GIVEN
        Money money = new Money();
        //WHEN
        assertThatThrownBy(() ->
                //WHEN
                money.setDevise(null)).isInstanceOf(IllegalArgumentException.class);
    }
}
