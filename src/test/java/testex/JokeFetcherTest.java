/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testex;

import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import testex.jokefetching.ChuckNorris;
import testex.jokefetching.EduJoke;
import testex.jokefetching.IFetcherFactory;
import testex.jokefetching.IJokeFetcher;
import testex.jokefetching.Moma;
import testex.jokefetching.Tambal;

/**
 *
 * @author Ben
 */
@RunWith(MockitoJUnitRunner.class)
public class JokeFetcherTest {
    Joke testEdu = new Joke("This is an educational joke", "EducationalJokes.org");
    Joke testChuck = new Joke("When Chuck Norris kicks a roundhouse...", "ChuckNorrisJokes.org");
    Joke testMoma = new Joke("Yo mama is so ugly...", "JoMamaJokes.org");
    Joke testTambal = new Joke("Tambalaaa...", "TambalaJokes.org");
    
    @Mock
    private IDateFormatter dfMock;
    
    @Mock
    private IFetcherFactory ffMock;
    
    @Mock
    Moma moma;
    
    @Mock
    ChuckNorris chuck;
    
    @Mock
    EduJoke edu;
    
    @Mock
    Tambal tambal;
    
  
    private JokeFetcher jFetcher;
    
    @Before
    public void setup() {
        List<IJokeFetcher> fetchers = Arrays.asList(edu, chuck, moma, tambal);
        when(ffMock.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal")).thenReturn(fetchers);
        List<String> types = Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");

        when(ffMock.getAvailableTypes()).thenReturn(types);
        jFetcher = new JokeFetcher(dfMock, ffMock);
        
        given(edu.getJoke()).willReturn(testEdu);
        given(chuck.getJoke()).willReturn(testChuck);
        given(moma.getJoke()).willReturn(testMoma);
        given(tambal.getJoke()).willReturn(testTambal);
    }
    /**
     * Test of getAvailableTypes method, of class JokeFetcher.
     */
    @Test
    public void testGetAvailableTypesFour() {
        assertThat(jFetcher.getAvailableTypes().size(), is(4));
        assertThat(jFetcher.getAvailableTypes(), hasItems("EduJoke", "ChuckNorris", "Moma", "Tambal"));
    }
    
    @Test
    public void testCheckIfValidToken() {
        String validTokens = "EduJoke,ChuckNorris,Moma,Tambal";
        assertThat(jFetcher.isStringValid(validTokens), is(true));
        String invalidTokens = "ben,chuckmorris,mammia,tambo";
        assertThat(jFetcher.isStringValid(invalidTokens), is(false));
    }

    @Test
    public void testGetJokes() throws Exception {
         given(dfMock.getFormattedDate(eq("Europe/Copenhagen"), anyObject())).willReturn("17 feb. 2017 10:56 AM");
         assertThat(jFetcher.getJokes("eduprog,chucknorris,moma,tambal", "Europe/Copenhagen")
                 .getTimeZoneString(), is("17 feb. 2017 10:56 AM"));
    }
    
     @Test
    public void testEduJoke() throws Exception {
        String expectedJoke = "This is an educational joke";
        String expectedReference = "EducationalJokes.org";
        Jokes jokes = jFetcher.getJokes("EduJoke,ChuckNorris,Moma,Tambal", "Europe/Copenhagen");
        assertThat(jokes.getJokes().get(0).getJoke(), is(expectedJoke));
        assertThat(jokes.getJokes().get(0).getReference(), is(expectedReference));
    }
    
    @Test
    public void testChuckNorrisJoke() throws Exception {
        String expectedJoke = "When Chuck Norris kicks a roundhouse...";
        String expectedReference = "ChuckNorrisJokes.org";
        Jokes jokes = jFetcher.getJokes("EduJoke,ChuckNorris,Moma,Tambal", "Europe/Copenhagen");
        assertThat(jokes.getJokes().get(1).getJoke(), is(expectedJoke));
        assertThat(jokes.getJokes().get(1).getReference(), is(expectedReference));
    }
    
    @Test
    public void testYoMamaJoke() throws Exception {
        String expectedJoke = "Yo mama is so ugly...";
        String expectedReference = "JoMamaJokes.org";
        Jokes jokes = jFetcher.getJokes("EduJoke,ChuckNorris,Moma,Tambal", "Europe/Copenhagen");
        assertThat(jokes.getJokes().get(2).getJoke(), is(expectedJoke));
        assertThat(jokes.getJokes().get(2).getReference(), is(expectedReference));
    }
    
     @Test
    public void testTambalaJoke() throws Exception {
        String expectedJoke = "Tambalaaa...";
        String expectedReference = "TambalaJokes.org";
        Jokes jokes = jFetcher.getJokes("EduJoke,ChuckNorris,Moma,Tambal", "Europe/Copenhagen");
        assertThat(jokes.getJokes().get(3).getJoke(), is(expectedJoke));
        assertThat(jokes.getJokes().get(3).getReference(), is(expectedReference));
    }
   
    
}
