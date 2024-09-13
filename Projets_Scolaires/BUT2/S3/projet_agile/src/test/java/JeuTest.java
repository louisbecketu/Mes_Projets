import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JeuTest {
    public Jeu jeu;
    public ArrayList<Card> packet;

    @BeforeEach
    public void initialization() {
        jeu = new Jeu();
        packet = jeu.genererPacket();
    }

    @Test
    public void packetTest(){
        assertEquals(packet.size(), 52);
        jeu.distribuerCarte(packet);
        assertEquals(jeu.pioche.size(), 42);
    }
}
