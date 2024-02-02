package fr.rouen.mastergil.tptest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyUnitJUnitTest {
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
        assertEquals(amount, money.getMontant());
        assertEquals(currency, money.getDevise());
    }

    @Test
    public void shouldConstructMoneyWithAValidAmountAndCurrency() {
        //GIVEN
        int amount = 10;
        Devise currency = Devise.YEN;
        //WHEN
        Money money = new Money(amount, currency);
        //THEN
        assertEquals(amount, money.getMontant());
        assertEquals(currency, money.getDevise());
    }

    @Test
    public void shouldConstructMoneyWithAValidAmountAndNoCurrency() {
        //GIVEN
        int amount = 10;

        //THEN
        assertThrows(IllegalArgumentException.class, () ->
                //WHEN
                new Money(amount, null)
        );
    }

    @Test
    public void shouldReturnTrueIfAmountIsPositive() {
        //GIVEN
        int amount = 10;
        Money money = new Money(amount, Devise.EURO);
        //WHEN
        boolean result = money.isPositif();
        //THEN
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfAmountIsNegative() {
        //GIVEN
        int amount = -10;
        Money money = new Money(amount, Devise.EURO);
        //WHEN
        boolean result = money.isPositif();
        //THEN
        assertFalse(result);
    }

    @Test
    public void shouldSetCurrencyWithValidCurrency() {
        //GIVEN
        Devise currency = Devise.DINAR;
        Money money = new Money();
        //WHEN
        money.setDevise(currency);
        //THEN
        assertEquals(currency, money.getDevise());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWithInvalidCurrency() {
        //GIVEN
        Money money = new Money();
        //WHEN
        assertThrows(IllegalArgumentException.class, () ->
                //WHEN
                money.setDevise(null)
        );
    }
}