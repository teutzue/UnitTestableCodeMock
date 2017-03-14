/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testex;

import java.util.Date;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ben
 */
public class DateFormatterTest {
    
    private IDateFormatter dFormatter;
    
    public DateFormatterTest() {
    }
    
    
    @Before
    public void setUp() {
        dFormatter = new DateFormatter();
    }
    
    @Test
    public void testTimeZoneCPH() throws Exception {
        String expDate = "17 Feb 2017 10:56 AM";
        String tZone = "Europe/Copenhagen";
        Date date = new Date(2017 - 1900, 1, 17, 10, 56);
        assertThat(dFormatter.getFormattedDate(tZone, date), is(expDate) );
    }
}
