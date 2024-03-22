package fr.rouen.mastergil.tptest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BankAccountWithDAOUnitTest {

    @Mock
    JdbcDAO dao;

    @InjectMocks
    BankAccountWithDAO bankAccountWithDAO;

    @BeforeEach
    public void init() {
        Connection connection = Mockito.mock(Connection.class);
        Mockito.when(dao.setUpConnection()).thenReturn(connection);
    }

    @Test
    public void shouldCreateAnAccountSuccessfully() {
        //GIVEN
        //THEN
        assertThatNoException().isThrownBy(
                //WHEN
                bankAccountWithDAO::creerCompte
        );
    }

    @Test
    public void shouldCreateAnAccountWithAConnectionIsReadOnly() {
        //GIVEN
        Connection connection = Mockito.mock(Connection.class);
        try {
            Mockito.when(connection.isReadOnly()).thenReturn(true);
        } catch (SQLException e) {
            // Impossible to reach
        }
        Mockito.when(dao.setUpConnection()).thenReturn(connection);
        //THEN
        assertThatExceptionOfType(ConnectException.class).isThrownBy(
                //WHEN
                bankAccountWithDAO::creerCompte
        );
    }

    @Test
    public void shouldCreateAnAccountWithAConnectionClosed() {
        //GIVEN
        Connection connection = Mockito.mock(Connection.class);
        try {
            Mockito.when(connection.isClosed()).thenReturn(true);
        } catch (SQLException e) {
            // Impossible to reach
        }
        Mockito.when(dao.setUpConnection()).thenReturn(connection);
        //THEN
        assertThatExceptionOfType(ConnectException.class).isThrownBy(
                //WHEN
                bankAccountWithDAO::creerCompte
        );
    }

    @Test
    public void shouldCreateAnAccountWithFailedDAO() {
        //GIVEN
        Connection connection = Mockito.mock(Connection.class);
        try {
            Mockito.when(connection.isClosed()).thenThrow(SQLException.class);
        } catch (SQLException e) {
            // Impossible to reach
        }
        Mockito.when(dao.setUpConnection()).thenReturn(connection);
        //THEN
        assertThatExceptionOfType(SQLException.class).isThrownBy(
                //WHEN
                bankAccountWithDAO::creerCompte
        );
    }

    @Test
    public void shouldCreateAnAccountWithValuesSuccessfully() {
        //GIVEN
        int amount = 10;
        Devise devise = Devise.DINAR;
        //THEN
        assertThatNoException().isThrownBy(
                //WHEN
                () -> bankAccountWithDAO.creerCompte(amount, devise)
        );
    }

    @Test
    public void shouldDisplayPaySuccessfully() {
        //GIVEN
        int amount = 10;
        Devise devise = Devise.DINAR;
        Money money = new Money(amount, devise);
        Mockito.when(dao.getSolde()).thenReturn(money);
        //WHEN
        String result = "";
        try {
            result = bankAccountWithDAO.consulterSolde();
        } catch (SQLException | ConnectException e) {
            //Impossible to reach
        }
        //THEN
        assertThat(result).matches("Votre solde actuel est de "
            + amount + " " + devise);
    }

    @Test
    public void shouldRetrieve3DollarsSuccessfully() {
        //GIVEN
        int amount = 3;
        Devise devise = Devise.DOLLAR;
        Money money = new Money(amount, devise);
        Mockito.when(dao.getSolde()).thenReturn(money);
        //WHEN
        Money result = null;
        try {
            result = bankAccountWithDAO.retirerArgent(amount);
        } catch (SQLException | ConnectException e) {
            //Impossible to reach
        }
        //THEN
        assertThat(result).isNotNull();
        assertThat(result.getMontant()).isEqualTo(money.getMontant());
        assertThat(result.getDevise()).isEqualTo(money.getDevise());
    }

    @Test
    public void shouldDeposit3DollarsSuccessfully() {
        //GIVEN
        int amount = 3;
        Devise devise = Devise.DOLLAR;
        Money money = new Money(amount, devise);
        Mockito.when(dao.getSolde()).thenReturn(money);
        //WHEN
        Money result = null;
        try {
            result = bankAccountWithDAO.deposerArgent(amount);
        } catch (SQLException | ConnectException e) {
            //Impossible to reach
        }
        //THEN
        assertThat(result).isNotNull();
        assertThat(result.getMontant()).isEqualTo(money.getMontant());
        assertThat(result.getDevise()).isEqualTo(money.getDevise());
    }

    @Test
    public void shouldIsSoldePositifReturnTrue() {
        //GIVEN
        int amount = 3;
        Devise devise = Devise.DOLLAR;
        Money money = new Money(amount, devise);
        Mockito.when(dao.getSolde()).thenReturn(money);
        //WHEN
        boolean result = false;
        try {
            result = bankAccountWithDAO.isSoldePositif();
        } catch (SQLException | ConnectException e) {
            //Impossible to reach
        }
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void shouldIsSoldePositifReturnFalse() {
        //GIVEN
        int amount = -3;
        Devise devise = Devise.DOLLAR;
        Money money = new Money(amount, devise);
        Mockito.when(dao.getSolde()).thenReturn(money);
        //WHEN
        boolean result = false;
        try {
            result = bankAccountWithDAO.isSoldePositif();
        } catch (SQLException | ConnectException e) {
            //Impossible to reach
        }
        //THEN
        assertThat(result).isFalse();
    }
}