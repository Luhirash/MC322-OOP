package Effects;

import Entities.Entity;
import Cards.Card;
import Core.GameManager;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes responsável por validar a lógica base e o comportamento 
 * compartilhado de todos os efeitos (Effect) do jogo.
 */
public class EffectTest {

    /**
     * Classe interna utilitária (Dummy) que simula uma Entidade.
     * Utilizada exclusivamente para atuar como o dono (Owner) dos efeitos durante os testes.
     */
    class EntityDummy extends Entity{
            public EntityDummy() { 
                super("Owner", 100, 10);}

            @Override public Card[] getHits(){ 
                return new Card[0];}
        }

    /**
     * Implementação concreta (Dummy) da classe abstrata Effect.
     * Permite instanciar e testar os métodos base de gerenciamento de efeitos, 
     * como controle de intensidade e busca em listas.
     */
    class EffectDummy extends Effect {
            public EffectDummy(String name, Entity owner, int intensity) {
                super(name, owner, intensity);
            }
        
        @Override public String getString() { return ""; }
        @Override protected void useEffect() {}
        @Override public void beNotified(GameManager gm) {}
        }

    /**
     * Verifica se o método de adicionar intensidade atualiza o valor corretamente.
     * Garante que a intensidade possa ser tanto incrementada (valores positivos)
     * quanto decrementada (valores negativos).
     */
    @Test
    void MustChangeTheIntensityWhenAddedOrSubtracted(){
        Entity Owner = new EntityDummy();
        Effect effect = new EffectDummy("effect", Owner, 3);
        effect.addIntensity(2);
        assertEquals(5, effect.getIntensity(), "The intensity must add correctly");
        effect.addIntensity(-4);
        assertEquals(1, effect.getIntensity(), "The intensity must subtract correctly");
    }

    /**
     * Verifica a lógica de expiração dos efeitos baseada em sua intensidade.
     * Assegura que efeitos com intensidade maior que zero permanecem ativos,
     * enquanto efeitos com intensidade zero ou negativa são marcados como expirados.
     */
    @Test
    void ExpireWhenIntensityIsZero(){
        Entity Owner = new EntityDummy();

        Effect AliveEffect = new EffectDummy("Alive", Owner, 1);
        Effect ZeroedEffect = new EffectDummy("Zero", Owner, 0);
        Effect NegativeEffect = new EffectDummy("Negative", Owner, -2);

        assertFalse(AliveEffect.isExpired(), "Effects with intensity > 0 must be active");
        assertTrue(ZeroedEffect.isExpired(), "Effects with intensity = 0 must not be active");
        assertTrue(NegativeEffect.isExpired(), "Effects with intensity < 0 must not be active");
    }

    /**
     * Verifica se o método getIndex é capaz de encontrar a posição correta de um efeito
     * dentro de um ArrayList. A busca deve ser precisa, comparando tanto o nome do efeito
     * quanto a referência do dono (Owner).
     */
    @Test
    void CorrectIndexInEffectsArray(){
        Entity Owner = new EntityDummy();
        Entity otherOwner = new EntityDummy();

        Effect targetEffect = new EffectDummy("target", Owner, 1);

        ArrayList<Effect> array = new ArrayList<>();

        array.add(new EffectDummy("ice", Owner, 2));
        array.add(new EffectDummy("Fogo", otherOwner, 2)); // Same name otherOwner
        array.add(targetEffect); // Index 2
        array.add(new EffectDummy("Veneno", Owner, 1));

        int Index = targetEffect.getIndex(array);
        
        assertEquals(2, Index, "Must return the correct index with the same name and owner");
    }

    /**
     * Testa o comportamento do método getIndex quando o efeito procurado não existe no ArrayList.
     * Garante que retorne -1 caso o efeito não seja encontrado, incluindo cenários
     * onde o nome do efeito existe, mas pertence a um dono (Owner) diferente.
     */
    @Test
    void MustReturnMinusOneWhenNotInTheArray(){
        Entity Owner = new EntityDummy();
        Entity notOwner = new EntityDummy();
        Effect notTargetEffect = new EffectDummy("target", notOwner, 1);
        Effect targetEffect = new EffectDummy("target", Owner, 1);

        ArrayList<Effect> Array = new ArrayList<>();
        Array.add(notTargetEffect);
        int Index = targetEffect.getIndex(Array);
        assertEquals(-1, Index, "The returned index must be -1 when the effect isnt int the array or the array is empty");
    }
}