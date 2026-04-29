package Cards;
import java.util.ArrayList;
import Entities.Entity;
import Entities.Hero;
import Effects.Effect;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes dedicada a verificar a lógica base e os atributos compartilhados de todas as cartas (Card).
 */
class CardTest {
    
    /**
     * Implementação concreta (Dummy) da classe abstrata Card para permitir testes de seus métodos base.
     */
    class DummyCard extends Card {
        public DummyCard(String name, int staminaCost, String description) {
            super(name, staminaCost, description);
        }
        @Override public Effect useCard(Entity attacker, Entity receiver) { return null; }
        @Override public void printCardStats() {}
        @Override public int getMainStat() { return 0; }
        @Override public void upgrade() {}
    }
    
    /**
     * Implementação Dummy de um Herói que permite configurar a stamina inicial facilmente.
     */
    class HeroDummy extends Hero {
        public HeroDummy(int currentStamina) {
            super("HeroDummy", 100, 10);
            this.setStamina(currentStamina);
        }
        @Override public ArrayList<Card> getHits() { return new ArrayList<Card>(); }
    }

    /**
     * Verifica a inicialização básica dos atributos de uma carta e atesta que 
     * seu estado inicial "wasUsed" é estritamente falso.
     */
    @Test
    void CorrectCardIntializationAndCorrectReturnOfAttributes() {
        Card card = new DummyCard("Cruzado", 4, "Soco lateral");

        assertEquals("Cruzado", card.getName(), "The name must be correct");
        assertEquals(4, card.getStaminaCost(), "The stamina cost must be correct");
        assertEquals("Soco lateral", card.get_description(), "The description must be correct");
        assertFalse(card.getWasUsed(), "Every card must be initialized with the status 'was used' false");
    }

    /**
     * Verifica se o estado de uso da carta (wasUsed) pode ser alterado e lido corretamente.
     */
    @Test
    void CanChangeTheUsedStatus() {
        Card card = new DummyCard("Cruzado", 4, "Soco lateral");
        card.setWasUsed(true);
        assertTrue(card.getWasUsed(), "The setter must change the status to true");
    }

    /**
     * Testa o cenário de sucesso onde a entidade possui fôlego suficiente para usar a carta.
     * Garante que o método retorna true e a carta é marcada como usada.
     */
    @Test
    void MustReturnTrueAndBeUsedIfHeroHasEnoughStamina() {
        Card card = new DummyCard("Golpe Pesado", 5, "Custa 5 de fôlego");
        Hero hero = new HeroDummy(10);
        boolean used_or_not = card.tryCard(hero);

        assertTrue(used_or_not, "Must return true because hero has enough stamina");
        assertTrue(card.getWasUsed(), "The card must be marked as used");
    }

    /**
     * Testa o cenário de falha onde a entidade não possui fôlego suficiente para usar a carta.
     * Garante que o método retorna false e a carta não é marcada como usada.
     */
    @Test
    void MustReturnFalseAndNotBeUsedIfHeroHasNotEnoughStamina() {
        Card card = new DummyCard("Golpe Pesado", 5, "Custa 5 de fôlego");
        Hero hero = new HeroDummy(2); // Herói cansado! Só tem 2 de fôlego.
        boolean used_or_not = card.tryCard(hero);

        assertFalse(used_or_not, "Must return false because the stamina is not enough");
        assertFalse(card.getWasUsed(), "The card must not be marked as used");
    }
}