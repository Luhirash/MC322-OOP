# A Luta — Jogo de Combate por Turnos em Java

## Descrição

**A Luta** é um jogo de combate por turnos inspirado em *Slay the Spire*, desenvolvido em Java.

O jogador controla o herói **Anderson Silva** e escolhe um inimigo para enfrentar: **Jon Jones**, **Connor McGregor** ou **Kenneth Allen**. A cada turno, cartas são compradas de um baralho e o jogador decide quais usar, gastando **fôlego** (stamina). As cartas podem causar dano ao inimigo ou conceder **bloqueio** (escudo) ao herói. Após o turno do jogador, o inimigo age automaticamente com movimentos escolhidos no início do turno. O combate continua até que um dos lutadores seja derrotado.

## Como compilar

O projeto utiliza **Gradle** como sistema de build. A partir da **raiz do repositório**, execute:

```bash
./gradlew build
```

> Também é possível compilar manualmente sem Gradle:
> ```bash
> javac -d bin $(find src -name "*.java")
> ```

## Como executar

```bash
./gradlew run
```

> Também é possível executar manualmente sem Gradle:
> ```bash
> java -cp bin App
> ```

## Documentação (Javadoc)

Todas as classes e métodos do projeto estão documentados com **Javadoc**. Para gerar a documentação em HTML, execute:

```bash
./gradlew javadoc
```

A documentação gerada estará disponível em `build/docs/javadoc/index.html`.

---

## Funcionamento do jogo

O jogador controla **Anderson Silva** em uma batalha de cartas contra um dos inimigos disponíveis:

- **Jon Jones** — lutador resistente que aplica Força em si mesmo
- **Connor McGregor** — lutador rápido que aplica sangramento no herói
- **Kenneth Allen** — lutador mais fraco e com pouca defesa que aplica uma "paulistinha" no herói

A cada turno:
1. O inimigo revela suas intenções (cartas que pretende usar)
2. O jogador compra cartas do baralho para compor sua mão e escolhe quais jogar, gastando fôlego
3. Ao encerrar o turno, o inimigo executa suas ações

O combate termina quando o herói ou o inimigo chega a 0 de vida.

---

## Efeitos implementados

### 1. Sangramento (`Bleeding`)
- **Tipo:** Debuff (aplicado no inimigo ou no herói)
- **Gatilho:** Final do turno do oponente (ex: `HEROFINISH` se o inimigo estiver sangrando)
- **Comportamento:** A entidade afligida sofre X de dano, onde X é a quantidade atual de acúmulos. Em seguida, perde 1 acúmulo. Quando os acúmulos chegam a zero, o efeito é removido.
- **Exemplo:** 3 acúmulos de Sangramento → 3 de dano no turno 1, 2 no turno 2, 1 no turno 3, depois removido.

### 2. Recuperação (`Healing`)
- **Tipo:** Buff (aplicado na própria entidade)
- **Gatilho:** Início do turno do dono (`HEROSTART` para o herói, `ENEMYSTART` para inimigos)
- **Comportamento:** O dono recupera X pontos de vida, onde X é a quantidade de acúmulos. Em seguida, perde 1 acúmulo. Quando os acúmulos chegam a zero, o efeito é removido.
- **Exemplo:** 2 acúmulos de Recuperação → cura 2 de vida no turno 1, cura 2 no turno 2 (acúmulos decrementam até 0).

### 3. Força (`Strength`)
- **Tipo:** Buff passivo (aplicado no herói ou inimigo)
- **Gatilho:** Passivo — sempre ativo enquanto o efeito existir (Decai no fim do turno do dono)
- **Comportamento:** Aumenta o dano de todas as `DamageCard` usadas pela entidade afligida em X pontos. 

---

## Cartas que aplicam efeitos

### `BleedingCard` — cartas de sangramento
- **"golpe lascerante"** — Custo: 3 de fôlego | Aplica **3 de intensidade de Sangramento**
- **"cotovelada cortante"** — Custo: 4 de fôlego | Aplica **4 de intensidade de Sangramento**
- O sangramento age no final do turno de quem aplicou o golpe.

### `HealingCard` — cartas de recuperação
- **"pedir tempo técnico"** — Custo: 4 de fôlego | Aplica **2 de intensidade de Recuperação**
- **"beber suco secreto"** — Custo: 2 de fôlego | Aplica **3 de intensidade de Recuperação**
- A recuperação age no início de cada turno do dono.

---

## Padrão de Design Observer

A classe `GameManager` atua como **Publisher** neste projeto, assumindo a responsabilidade de gerenciar os eventos de combate. Ela mantém uma lista de `Effect` (Subscribers) e os notifica via `notifyEvent()` sempre que o estado dos turnos (orquestrados pela classe `Turns`) avança. 

Os eventos do `GameManager` incluem:

| Evento         | Descrição                        |
|----------------|----------------------------------|
| `HEROSTART`    | Início do turno do herói         |
| `HEROFINISH`   | Fim do turno do herói            |
| `ENEMYSTART`   | Início do turno do inimigo       |
| `ENEMYFINISH`  | Fim do turno do inimigo          |

Cada `Effect` implementa `beNotified(GameManager gameManager)` e decide sozinho se deve agir com base no `gameManager.currentEvent`. As cartas, ao serem jogadas, repassam o efeito gerado para o fluxo do jogo, que os inscreve via `gameManager.subscribe(effect)`. O próprio `GameManager` realiza a coleta de efeitos cuja intensidade chegou a zero para removê-los da lista.

---

## Estrutura de arquivos

```
src/main/java/
├── Core/
│   ├── App.java            — Ponto de entrada
│   ├── GameManager.java    — Gerenciador de eventos (Publisher)
│   ├── Turns.java          — Controlador do fluxo de rodadas e turnos
│   ├── Publisher.java      — Interface para o padrão Observer
│   └── Observer.java       — Interface para o padrão Observer
├── Entities/
│   ├── Entity.java         — Classe abstrata base para herói e inimigos
│   ├── Hero.java           — Herói jogável
│   ├── Enemy.java          — Classe abstrata para inimigos
│   ├── JonJones.java       — Inimigo: aplica Força em si mesmo
│   ├── ConnnorMcGregor.java— Inimigo: rápido e aplica Sangramento
│   └── KennethAllen.java   — Inimigo: aplica Sangramento (paulistinha)
├── Cards/
│   ├── Card.java           — Classe abstrata de carta
│   ├── EffectCard.java     — Classe abstrata para cartas que geram Efeitos
│   ├── DamageCard.java     — Carta de ataque direto (com bônus de Força)
│   ├── ShieldCard.java     — Carta de defesa (concede escudo ao usuário)
│   ├── HealthCard.java     — Carta de cura instantânea de vida
│   ├── BleedingCard.java   — Carta que aplica o efeito Sangramento
│   ├── HealingCard.java    — Carta que aplica o efeito Recuperação
│   └── StrengthCard.java   — Carta que aplica o efeito Força
├── Effects/
│   ├── Effect.java         — Classe abstrata de efeito (Subscriber)
│   ├── Bleeding.java       — Efeito Sangramento
│   ├── Healing.java        — Efeito Recuperação
│   └── Strength.java       — Efeito Força
└── Piles/
    ├── PileOfCards.java    — Pilha genérica de cartas
    ├── PurchasePile.java   — Baralho de compra
    ├── DiscardPile.java    — Pilha de descarte
    └── PlayerHand.java     — Mão do jogador
```