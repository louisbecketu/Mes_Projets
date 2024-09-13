import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals; 

public class CardTest {
    private Card card1, card2, card3;

    @BeforeEach
    public void initialization() {
        card1 = new Card("ACE", "COEUR");
        card2 = new Card("DEUX", "PIQUE");
        card3 = new Card("DEUX", "COEUR");
    }

    @Test
    public void compareTest(){
        assertEquals(card1.compareTo(card2), 1);
        assertEquals(card2.compareTo(card1), -1);
        assertEquals(card2.compareTo(card3), 0);
    }

    @Test
    public void toStringTest(){
        assertEquals(card1.toString(), "\n ______\n|A     |\n|  ♥   |\n|     A|\n ‾‾‾‾‾‾ \n");
        assertEquals(card2.toString(), "\n ______\n|2     |\n|  ♠   |\n|     2|\n ‾‾‾‾‾‾ \n");
    }
}
