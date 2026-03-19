# A Luta — Jogo de Combate por Turnos em Java

## Descrição

**A Luta** é um jogo de combate por turnos inspirado em *Slay the Spire*, desenvolvido em Java. O jogador controla o herói **Anderson Silva** em uma batalha contra o inimigo **Jon Jones**.

A cada turno, o jogador dispõe de um recurso chamado **fôlego** (stamina), que é gasto ao usar cartas de ação. As cartas podem causar dano ao inimigo ou conceder escudo ao herói, bloqueando parte do dano recebido. Após o turno do jogador, o inimigo age automaticamente com movimentos aleatórios. O combate continua até que um dos lutadores seja derrotado.

---

## Mecânicas do Jogo

- **Vida (Health):** Cada personagem possui uma quantidade de vida. Ao chegar a zero, o personagem é derrotado.
- **Fôlego (Stamina):** Recurso gasto ao usar cartas. É totalmente restaurado no início de cada turno.
- **Reflexo (Shield):** Absorve dano antes que ele reduza a vida. É zerado no início de cada novo turno de ataque.
- **Turnos:** A cada turno são selecionadas 2 cartas de ataque e 1 de defesa como opção para o herói. O jogador começa jogando com o primeiro turno, e a cada turno que se passa o fôlego/stamina retorna para o valor máximo.

---

## Cartas Disponíveis

| # | Carta             | Tipo    | Efeito         | Custo de Fôlego |
|---|-------------------|---------|----------------|-----------------|
| 1 | Jab               | Ataque  | 5 de dano      | 3               |
| 2 | Direto (Cross)    | Ataque  | 8  de dano     | 5               |
| 3 | Chute na perna    | Ataque  | 10 de dano     | 6               |
| 4 | Chute na cabeça   | Ataque  | 12 de dano     | 7               |
| 5 | Soco cruzado      | Ataque  | 9  de dano     | 5               |
| 6 | Uppercut          | Ataque  | 11 de dano     | 6               |
| 7 | Focar             | Escudo  | 8  de reflexo  | 3               |
| 8 | Desviar           | Escudo  | 8  de reflexo  | 3               |
| 9 | Andar para trás   | Escudo  | 2  de reflexo  | 1               |
|10 | Agachar           | Escudo  | 4 de reflexo   | 2               |


---

## Estrutura do Projeto

```
├── App.java          # Ponto de entrada; gerencia o loop principal do jogo
├── Hero.java         # Classe do herói controlado pelo jogador
├── Enemy.java        # Classe do inimigo com escolhas aleatórias de turno automático
├── DamageCard.java   # Carta de ataque que causa dano ao inimigo
├── ShieldCard.java   # Carta de defesa que concede escudo/reflexos ao herói
└── Turns.java        # Classe que gerencia os turnos 
└── Entity.java       # Classe abstrata para as entidades(herois e viloes) 
└── Card.java        # Classe abstrata para as cartas(dano e shield)
└── PileOfCards.java       # Classe abstrata para as entidades(herois e viloes) 


```


## Compilação

No terminal, dentro da pasta do projeto, compile todos os arquivos `.java`:

```bash
javac -d bin $(find src -name "*.java")
```

---

## Execução

Após compilar, execute o programa com:

```bash
java -cp bin App
```

O jogo iniciará no terminal e solicitará suas escolhas a cada turno. Digite o número da ação desejada e pressione **Enter**.

---

