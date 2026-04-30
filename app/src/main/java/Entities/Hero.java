package Entities;
import Core.Turns;
import Cards.*;
import Piles.PurchasePile;
import java.util.ArrayList;
import java.util.List;
import Relics.RelicStrategy;

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
 *   <tr><td>Sangramento ({@link BleedingCard})</td>
 *       <td>golpe lascerante, cotovelada cortante</td></tr>
 *   <tr><td>Recuperação por turno ({@link HealingCard})</td>
 *       <td>pedir tempo técnico, beber suco secreto</td></tr>
 * </table>
 *
 * @see Entity
 * @see Turns#HeroTurn
 */
public class Hero extends Entity{

    private int heroCoins;
    /**
     * Constrói o herói com os atributos iniciais especificados.
     *
     * @param name       nome do herói exibido nas mensagens de combate
     * @param maxHealth  vida máxima do herói
     * @param maxStamina fôlego máximo do herói (determina quantas cartas pode usar por turno)
     */
    public Hero(String name, int maxHealth, int maxStamina){
        super(name, maxHealth, maxStamina);
        this.heroCoins = 0;
    }

    /** Lista de relíquias (itens passivos) adquiridas pelo herói na loja. */
    private ArrayList<RelicStrategy> relics = new ArrayList<>();

    /**
     * Deck completo de cartas disponíveis para o herói.
     *
     * <p>Este array é usado pelo {@link PurchasePile} para montar o baralho do jogo:
     * cópias das cartas aqui definidas são sorteadas aleatoriamente e adicionadas ao baralho.
     * O deck contém golpes de diferentes custos e poderes para oferecer variedade estratégica
     * ao jogador a cada partida.</p>
     */
    private ArrayList<Card> heroHits = new ArrayList<Card>(List.of(
            new DamageCard("jab", 3, 5, "desfere um soco com mão esquerda na cabeça do inimigo"),
            new DamageCard("direto", 5, 8, "desfere um soco com a mão direita na cabeça do inimigo"),
            new DamageCard("chute na perna", 6, 10, "desfere um chute com a perna direita na perna do inimigo"),
            //new DamageCard("chute na cabeça", 7, 12, "desfere um chute com a perna direita na cabeça do inimigo")
            //new DamageCard("soco cruzado", 5, 9, "desfere um soco lateral na cabeça do inimigo"),
            // new DamageCard("uppercut", 6, 11, "desfere um soco ascendente na cabeça do inimigo"),

            new ShieldCard("focar", 2, 5, "concentra-se no próximo movimento adverário, reduzindo o dano causado"),
            new ShieldCard("desviar", 3, 8, "se esquiva do ataque do inimigo"),
            // new ShieldCard("andar para trás", 1, 2, "dá um passo para trás, fungindo do inimigo"),
            // new ShieldCard("agachar", 2, 4, "busca se esconder do inimigo rapidamente"),

            new HealthCard("curativo", 2, 4, "usa um curativo para se curar"),
            // new HealthCard("anestésico", 6, 6, "diminui a sensação de dor"),
            //new HealthCard("massagem do senhor Myiagi", 2, 5, "recebe uma massagem milenar do karate kid"),

            new BleedingCard("golpe lascerante", 3, 3, "um golpe de raspão no rosto que aplica 3 de intensidade de sangramento no inimigo"),
            // new BleedingCard("cotovelada cortante", 4, 4, "uma cotovelada precisa na cabeça que aplica 4 de intensidade de sangramento no inimigo"),
            new HealingCard("pedir tempo técnico", 3, 2, "pausa a luta e aplica 2 de intensidade de cura no herói")
            // new HealingCard("beber suco secreto", 2, 3, "bebe 'água' que confere 3 de intensidade de cura")
    ));
    /**
     * Retorna o deck de cartas disponíveis para o herói.
     *
     * <p>Chamado pelo {@link PurchasePile#fillPile(int)} para construir o baralho
     * e por {@link Turns#HeroTurn} para disponibilizar as cartas na mão do jogador.</p>
     *
     * @return array com todas as cartas do herói
     */
    public ArrayList<Card> getHits() {
        return heroHits;
    }

    /**
     * Retorna a quantidade de moedas que o herói possui.
     * @return inteiro de moedas
     */
    public int getCoins() {
        return this.heroCoins;
    }

    /** 
     * Modifica o número de moedas que o herói possui
     * 
     * @param cois inteiro do número de moedas
    */
    private void setCoins(int coins) {
        this.heroCoins = coins;
    }
    
    /**
     * Adiciona um valor ao número atual de moedas do herói
     * @param coins valor que será adicionado (ou removido) do número de moedas
     */
    public void addCoins(int coins) {
        setCoins(getCoins() + coins);
    }

    /**
     * Adiciona uma relíquia ao inventário do herói.
     *
     * @param relic a relíquia a ser equipada
     */
    public void addRelic(RelicStrategy relic) {
        this.relics.add(relic);
    }

        /**
     * Verifica se o herói já possui uma relíquia com o nome fornecido.
     * Usado pela loja para evitar compra duplicada.
     *
     * @param relicName nome da relíquia a verificar
     * @return {@code true} se o herói já possui a relíquia
     */
    public boolean hasRelic(String relicName) {
        return relics.stream().anyMatch(r -> r.getName().equals(relicName));
    }

        /**
     * Reduz moedas do saldo do herói após uma compra na loja.
     * Não permite saldo negativo.
     *
     * @param amount valor a ser deduzido (deve ser positivo)
     * @throws IllegalArgumentException se {@code amount} for negativo
     */
    public void spendCoins(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Valor de gasto não pode ser negativo.");
        this.heroCoins = Math.max(0, this.heroCoins - amount);
    }

        /**
     * Dispara o hook {@code onBattleStart} de todas as relíquias equipadas.
     * Deve ser chamado em {@link Events.Battle#executeBattle} logo após
     * criar as pilhas de cartas, antes do loop de rodadas.
     *
     * <p>Exemplo de uso em {@code Battle.executeBattle}:</p>
     * <pre>{@code
     *   hero.triggerBattleStartRelics();  // <- adicionar esta linha
     *   while (hero.isAlive() && enemy.isAlive()) { ... }
     * }</pre>
     */
    public void triggerBattleStartRelics() {
        if (!relics.isEmpty()) {
            System.out.println("\n⚡ Ativando relíquias de início de batalha...");
            for (RelicStrategy relic : relics) {
                relic.onBattleStart(this);
            }
        }
    }

        /**
     * Dispara o hook {@code onTurnStart} de todas as relíquias equipadas.
     * Deve ser chamado em {@link Core.Turns#HeroTurn} no início de cada turno do herói.
     *
     * <p>Exemplo de uso em {@code Turns.HeroTurn}:</p>
     * <pre>{@code
     *   hero.triggerTurnStartRelics();  // <- adicionar no início do método
     *   // ... resto da lógica do turno
     * }</pre>
     */
    public void triggerTurnStartRelics() {
        for (RelicStrategy relic : relics) {
            relic.onTurnStart(this);
        }
    }

    /**
    * Retorna uma cópia somente-leitura das relíquias do herói.
    * Útil para exibir o inventário no HUD ou no início da batalha.
    *
    * @return lista imutável de relíquias
    */
    public List<RelicStrategy> getRelics() {
        return java.util.Collections.unmodifiableList(relics);
    }
}