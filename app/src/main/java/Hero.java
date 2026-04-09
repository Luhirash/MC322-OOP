public class Hero extends Entity{


    public Hero(String name, int maxHealth, int maxStamina){//funcao que cria o heroi
        super(name, maxHealth, maxStamina);
    }

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
            //cartas de efeito
            new bleedingCard("golpe lascerante", 3, 3, "aplica 3 de intensidade de sangramento no inimigo"),
            new timeoutCard("pedir tempo técnico", 4, 2, "recupera 2 pontos de vida por turno por 2 turnos")
        };

    public Card[] getHits() {
        return heroHits;
    }
}
