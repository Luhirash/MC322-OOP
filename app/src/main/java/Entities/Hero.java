package Entities;
import Core.Turns;
import Cards.Card;
import Cards.DamageCard;
import Cards.HealingCard;
import Cards.HealthCard;
import Cards.ShieldCard;
import Cards.bleedingCard;
import Piles.PurchasePile;

/**
 * Representa o herói controlado pelo jogador.
 *
 * <p>Estende {@link Entity} com um deck fixo e variado de cartas que cobre
 * todas as categorias disponíveis no jogo: dano, escudo, recuperação de vida instantânea,
 * sangramento (efeito periódico no inimigo), recuperação ao longo dos turnos e bônus de força.
 * O herói é controlado diretamente pelo jogador a cada turno via {@link Turns#HeroTurn}.</p>
 *
 * <h2>Cartas disponíveis ({@link #heroHits})</h2>
 * <table border="1">
 *   <caption>Deck do herói</caption>
 *   <tr><th>Categoria</th><th>Cartas</th></tr>
 *   <tr><td>Dano direto ({@link DamageCard})</td>
 *       <td>jab, direto, chute na perna, chute na cabeça, soco cruzado, uppercut</td></tr>
 *   <tr><td>Escudo ({@link ShieldCard})</td>
 *       <td>focar, desviar, andar para trás, agachar</td></tr>
 *   <tr><td>Vida instantânea ({@link HealthCard})</td>
 *       <td>curativo, anestésico, massagem do senhor Miyagi</td></tr>
 *   <tr><td>Sangramento ({@link bleedingCard})</td>
 *       <td>golpe lascerante, cotovelada cortante</td></tr>
 *   <tr><td>Recuperação por turno ({@link HealingCard})</td>
 *       <td>pedir tempo técnico, beber suco secreto</td></tr>
 * </table>
 *
 * @see Entity
 * @see Turns#HeroTurn
 */
public class Hero extends Entity{

    /**
     * Constrói o herói com os atributos iniciais especificados.
     *
     * @param name       nome do herói exibido nas mensagens de combate
     * @param maxHealth  vida máxima do herói
     * @param maxStamina fôlego máximo do herói (determina quantas cartas pode usar por turno)
     */
    public Hero(String name, int maxHealth, int maxStamina){
        super(name, maxHealth, maxStamina);
    }

    /**
     * Deck completo de cartas disponíveis para o herói.
     *
     * <p>Este array é usado pelo {@link PurchasePile} para montar o baralho do jogo:
     * cópias das cartas aqui definidas são sorteadas aleatoriamente e adicionadas ao baralho.
     * O deck contém golpes de diferentes custos e poderes para oferecer variedade estratégica
     * ao jogador a cada partida.</p>
     */
    private Card[] heroHits = {
            new DamageCard("jab", 3, 5, "desfere um soco com mão esquerda na cabeça do inimigo"),
            new DamageCard("direto", 5, 8, "desfere um soco com a mão direita na cabeça do inimigo"),
            new DamageCard("chute na perna", 6, 10, "desfere um chute com a perna direita na perna do inimigo"),
            new DamageCard("chute na cabeça", 7, 12, "desfere um chute com a perna direita na cabeça do inimigo"),
            new DamageCard("soco cruzado", 5, 9, "desfere um soco lateral na cabeça do inimigo"),
            new DamageCard("uppercut", 6, 11, "desfere um soco ascendente na cabeça do inimigo"),

            new ShieldCard("focar", 2, 5, "concentra-se no próximo movimento adverário, reduzindo o dano causado"),
            new ShieldCard("desviar", 3, 8, "se esquiva do ataque do inimigo"),
            new ShieldCard("andar para trás", 1, 2, "dá um passo para trás, fungindo do inimigo"),
            new ShieldCard("agachar", 2, 4, "busca se esconder do inimigo rapidamente"),

            new HealthCard("curativo", 4, 4, "usa um curativo para se curar"),
            new HealthCard("anestésico", 6, 6, "diminui a sensação de dor"),
            new HealthCard("massagem do senhor Myiagi", 2, 5, "recebe uma massagem milenar do karate kid"),

            new bleedingCard("golpe lascerante", 3, 3, "um golpe de raspão no rosto que aplica 3 de intensidade de sangramento no inimigo"),
            new bleedingCard("cotovelada cortante", 4, 4, "uma cotovelada precisa na cabeça que aplica 4 de intensidade de sangramento no inimigo"),
            new HealingCard("pedir tempo técnico", 4, 2, "pausa a luta e aplica 2 de intensidade de cura no herói"),
            new HealingCard("beber suco secreto", 2, 3, "bebe 'água' que confere 3 de intensidade de cura")
        };

    /**
     * Retorna o deck de cartas disponíveis para o herói.
     *
     * <p>Chamado pelo {@link PurchasePile#fillPile(int)} para construir o baralho
     * e por {@link Turns#HeroTurn} para disponibilizar as cartas na mão do jogador.</p>
     *
     * @return array com todas as cartas do herói
     */
    public Card[] getHits() {
        return heroHits;
    }
}