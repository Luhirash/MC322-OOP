import java.util.Random;
import java.util.ArrayList;

/**
 * Classe abstrata que representa um inimigo no combate.
 * <p>
 * Estende {@link Entity} com comportamentos específicos de IA: escolha aleatória
 * de cartas dentro do limite de fôlego disponível e execução das cartas escolhidas
 * durante o turno do inimigo.
 * </p>
 *
 * @see Entity
 * @see JonJones
 * @see ConnnorMcGregor
 */
public abstract class Enemy extends Entity {
    
    /**
     * Constrói um inimigo com os atributos iniciais.
     *
     * @param name       nome do inimigo
     * @param maxHealth  vida máxima
     * @param maxStamina fôlego máxima
     */
    public Enemy(String name, int maxHealth, int maxStamina) {
        super(name, maxHealth, maxStamina);
    }

    /**
     * Executa um ataque direto de dano sobre o herói usando uma {@link DamageCard} específica.
     * <p>
     * Gasta a fôlego correspondente e aplica o dano ao herói.
     * </p>
     *
     * @param hero       o herói que receberá o dano
     * @param damageCard a carta de dano a ser usada
     */
    public void attack(Hero hero, DamageCard damageCard){
        this.spendStamina(damageCard.getStaminaCost());
        hero.receiveDamage(damageCard.getDamageInflicted());
        System.out.println(this.getName() + " golpeou com um " + damageCard.getName() + " causando " + damageCard.getDamageInflicted() + " de dano");
    }

    /**
     * Seleciona aleatoriamente até 3 cartas para usar no turno, respeitando o limite de fôlego.
     * <p>
     * A seleção para quando a fôlego disponível for insuficiente para a próxima carta
     * ou quando 3 cartas já foram escolhidas.
     * </p>
     *
     * @param hits array de cartas disponíveis para o inimigo
     * @return lista de cartas escolhidas para o turno
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
     * Exibe no console as intenções do inimigo para o turno atual (cartas que pretende usar).
     * Cada subclasse pode personalizar o texto de apresentação.
     *
     * @param chosenCards lista de cartas que o inimigo planeja usar
     */
    public abstract void printIntentions(ArrayList<Card> chosenCards);

    /**
     * Retorna o array de cartas disponíveis para este inimigo.
     *
     * @return array de {@link Card} com os golpes e habilidades do inimigo
     */
    public abstract Card[] getHits();

    /**
     * Imprime no console as cartas de uma lista, uma por linha.
     *
     * @param chosenCards lista de cartas a exibir
     */
    protected void printChosenCards(ArrayList<Card> chosenCards) {
        for (int i = 0; i < chosenCards.size(); i++)
            chosenCards.get(i).printCardStats();
    }

    /**
     * Escolhe uma carta aleatória do array de golpes disponíveis.
     *
     * @param hits array de cartas disponíveis
     * @return uma {@link Card} escolhida aleatoriamente
     */
    private Card chooseRandomCard(Card[] hits) {
        Random number = new Random();
        int action = number.nextInt(hits.length);
        return hits[action];
    }

}