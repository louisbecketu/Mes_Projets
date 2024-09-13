import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals; 

public class JoueurTest {
    private Joueur j1;

    @BeforeEach
    public void initialization() {
        j1 = new Joueur();
        j1.setNom("Joueur 1");
    }

    @Test
    public void addScoreTest(){
        j1.addScore(10);
        assertEquals(j1.getScore(), 10);
    }
    
}
