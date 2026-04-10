import java.util.Random;
import java.util.ArrayList;

/**
 * Classe abstrata que representa um inimigo controlado pela IA no combate.
 *
 * <p>Estende {@link Entity} com comportamentos de inteligência artificial: o inimigo
 * seleciona aleatoriamente um conjunto de cartas no início de cada rodada ({@link #chooseCards})
 * e as executa em sequência durante seu turno ({@link Turns#enemyTurn}).
 * As intenções do inimigo são exibidas ao jogador antes do turno do herói,
 * permitindo que ele planeje sua defesa.</p>
 *
 * <h2>Fluxo por rodada</h2>
 * <ol>
 *   <li>{@link #chooseCards} é chamado para selecionar até 3 cartas dentro do limite de fôlego.</li>
 *   <li>{@link #printIntentions} exibe as cartas que o inimigo pretende usar.</li>
 *   <li>O herói realiza seu turno.</li>
 *   <li>{@link Turns#enemyTurn} executa as cartas escolhidas sobre o herói.</li>
 * </ol>
 *
 * @see Entity
 * @see Turns#enemyTurn
 */
public abstract class Enemy extends Entity {
    
    /**
     * Constrói um inimigo com os atributos iniciais.
     *
     * @param name       nome do inimigo exibido nas mensagens de combate
     * @param maxHealth  vida máxima do inimigo
     * @param maxStamina fôlego máximo do inimigo (limita quantas cartas pode usar por turno)
     */
    public Enemy(String name, int maxHealth, int maxStamina) {
        super(name, maxHealth, maxStamina);
    }

    /**
     * Executa um ataque direto de dano sobre o herói usando uma {@link DamageCard} específica.
     *
     * <p>Gasta o fôlego correspondente ao custo da carta e aplica o dano base da carta
     * ao herói. Diferente de {@link DamageCard#useCard}, este método não considera bônus
     * de força e não passa pelo sistema de {@link Turns}.</p>
     *
     * @param hero       o herói que receberá o dano
     * @param damageCard a carta de dano a ser executada
     */
    public void attack(Hero hero, DamageCard damageCard){
        this.spendStamina(damageCard.getStaminaCost());
        hero.receiveDamage(damageCard.getDamageInflicted());
        System.out.println(this.getName() + " golpeou com um " + damageCard.getName() + " causando " + damageCard.getDamageInflicted() + " de dano");
    }

    /**
     * Seleciona aleatoriamente as cartas que o inimigo usará nesta rodada.
     *
     * <p>A seleção obedece às seguintes regras:</p>
     * <ul>
     *   <li>No máximo 3 cartas são selecionadas por rodada.</li>
     *   <li>O fôlego disponível para a seleção é igual ao {@link Entity#getMaxStamina() fôlego máximo}
     *       do inimigo (calculado antes do turno).</li>
     *   <li>Uma carta sorteada aleatoriamente só é adicionada se o fôlego restante for suficiente.</li>
     *   <li>A seleção para imediatamente se o fôlego for insuficiente para a próxima carta sorteada.</li>
     * </ul>
     *
     * @param hits array de cartas disponíveis para o inimigo
     * @return lista de cartas selecionadas para o turno (entre 0 e 3 cartas)
     */
    public ArrayList<Card> chooseCards(Card[] hits) {
        ArrayList<Card> chosenCards = new ArrayList<Card>();
        int availableStamina = getMaxStamina();
        int numberOfCards = 0;
        while (availableStamina > 0 && numberOfCards < 3) {
            Card possibleCard = chooseRandomCard(hits);
            if (availableStamina >= possibleCard.getStaminaCost()) {
                chosenCards.add(possibleCard);
                availableStamina -= possibleCard.getStaminaCost();
                numberOfCards++;
            }
            else
                break;
        }
        return chosenCards;
    }

    /**
     * Exibe no console as intenções do inimigo para o turno atual.
     *
     * <p>Chamado antes do turno do herói para que o jogador possa ver o que o inimigo
     * planeja fazer e tomar decisões estratégicas (ex: usar cartas de escudo).
     * Cada subclasse pode personalizar o texto de apresentação com estilo próprio.</p>
     *
     * @param chosenCards lista de cartas que o inimigo planeja usar nesta rodada
     */
    public abstract void printIntentions(ArrayList<Card> chosenCards);

    /**
     * Retorna o array de cartas disponíveis para este inimigo específico.
     * <p>Cada subclasse define seu próprio conjunto de golpes com custos e danos distintos.</p>
     *
     * @return array de {@link Card} com os golpes e habilidades do inimigo
     */
    public abstract Card[] getHits();

    /**
     * Imprime no console as cartas de uma lista, uma por linha, usando o formato de cada carta.
     *
     * @param chosenCards lista de cartas a exibir via {@link Card#printCardStats()}
     */
    protected void printChosenCards(ArrayList<Card> chosenCards) {
        for (int i = 0; i < chosenCards.size(); i++)
            chosenCards.get(i).printCardStats();
    }

    /**
     * Escolhe uma carta aleatória do array de golpes disponíveis do inimigo.
     *
     * @param hits array de cartas disponíveis
     * @return uma {@link Card} escolhida aleatoriamente do array
     */
    private Card chooseRandomCard(Card[] hits) {
        Random number = new Random();
        int action = number.nextInt(hits.length);
        return hits[action];
    }

}