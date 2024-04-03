package fr.rouen.mastergil.tptest.meteo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

class StationMeteoIntegrationTest {

    private final ByteArrayOutputStream consoleContent = new ByteArrayOutputStream();

    private PrintStream stream = System.out;
    @BeforeEach
    public void beforeTest() {
        // Redirect all System.out to consoleContent.
        System.setOut(new PrintStream(consoleContent));
    }

    @AfterEach
    public void afterTest() {
        System.setOut(stream);
    }

    @Test
    public void shouldMajPrevisionReturnForecastForParis() {
        //GIVEN
        IWeatherProvider weatherProvider = new OpenWeatherMapProvider();
        StationMeteo stationMeteo = new StationMeteo(weatherProvider);
        String city = "Paris,FR";
        //WHEN
        List<Prevision> result = stationMeteo.majPrevision(city);
        //THEN
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();

    }

    @Test
    public void shouldMajPrevisionThrowIllegalArgumentException() {
        //GIVEN
        IWeatherProvider weatherProvider = new OpenWeatherMapProvider();
        StationMeteo stationMeteo = new StationMeteo(weatherProvider);
        String city = null;
        //THEN
        assertThatException().isThrownBy(
                //WHEN
                () -> stationMeteo.majPrevision(city)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldMajPrevisionThrowRuntimeException() {
        //GIVEN
        IWeatherProvider weatherProvider = new OpenWeatherMapProvider();
        StationMeteo stationMeteo = new StationMeteo(weatherProvider);
        String city = "gilbertonin";
        //THEN
        assertThatException().isThrownBy(
                //WHEN
                () -> stationMeteo.majPrevision(city)
        ).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void shouldMainReturnSuccessAndPrintString() {
        //GIVEN
        OpenWeatherMapProvider provider = new OpenWeatherMapProvider();
        StationMeteo stationMeteo = new StationMeteo(provider);
        List<Prevision> previsions = stationMeteo.majPrevision("Paris,FR");
        //WHEN
        StationMeteo.main(null);
        //THEN
        assertThat(previsions.toString()).isEqualTo(consoleContent.toString().trim());
    }
}