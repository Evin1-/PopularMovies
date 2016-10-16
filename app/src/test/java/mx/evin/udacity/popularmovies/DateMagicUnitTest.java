package mx.evin.udacity.popularmovies;

import com.loopcupcakes.udacity.popularmovies.utils.DatesMagic;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by evin on 10/15/16.
 */

public class DateMagicUnitTest {
    @Test
    public void isDateCorrectlyFormatted() throws Exception {
        System.out.println(DatesMagic.generateDate(2));
        assertTrue(DatesMagic.generateDate(2).equals("2016-08-15"));
    }
}
