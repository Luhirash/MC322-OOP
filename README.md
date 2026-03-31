# A Luta — Jogo de Combate por Turnos em Java

## Descrição

**A Luta** é um jogo de combate por turnos inspirado em *Slay the Spire*, desenvolvido em Java.

O jogador controla o herói **Anderson Silva** e escolhe um inimigo para enfrentar: **Jon Jones** ou **Connor McGregor**. A cada turno, cartas são compradas de um baralho e o jogador decide quais usar, gastando **fôlego** (stamina). As cartas podem causar dano ao inimigo ou conceder **bloqueio** (escudo) ao herói. Após o turno do jogador, o inimigo age automaticamente com movimentos escolhidos no início do turno. O combate continua até que um dos lutadores seja derrotado.



## Como compilar

A partir da **raiz do repositório**, execute:

```bash
javac -d bin $(find src -name "*.java")
```

## Como executar

```bash
java -cp bin App
```

---

## Funcionamento do jogo

O jogador controla **Anderson Silva** em uma batalha de cartas contra um dos inimigos disponíveis:

- **Jon Jones** — lutador resistente que aplica Força em si mesmo
- **Connor McGregor** — lutador rápido que envenena o herói

A cada turno:
1. O inimigo revela suas intenções (cartas que pretende usar)
2. O jogador compra 3 cartas do baralho e escolhe quais jogar, gastando fôlego
3. Ao encerrar o turno, o inimigo executa suas ações

O combate termina quando o herói ou o inimigo chega a 0 de vida.

---

## Efeitos implementados

### 1. Sangramento (`Bleeding`)
- **Tipo:** Debuff (aplicado no inimigo ou no herói)
- **Gatilho:** Final do turno do herói (`HEROFINISH`)
- **Comportamento:** A entidade afligida sofre X de dano, onde X é a quantidade atual de acúmulos. Em seguida, perde 1 acúmulo. Quando os acúmulos chegam a zero, o efeito é removido.
- **Exemplo:** 3 acúmulos de Sangramento → 3 de dano no turno 1, 2 no turno 2, 1 no turno 3, depois removido.

### 2. Timeout / Recuperação (`Timeout`)
- **Tipo:** Buff (aplicado no herói)
- **Gatilho:** Início do turno do herói (`HEROSTART`)
- **Comportamento:** O herói recupera X pontos de vida, onde X é a quantidade de acúmulos. Em seguida, perde 1 acúmulo. Quando os acúmulos chegam a zero, o efeito é removido.
- **Exemplo:** 2 acúmulos de Recuperação → cura 2 de vida no turno 1, cura 2 no turno 2 (acúmulos decrementam até 0).

### 3. Força (`Strength`)
- **Tipo:** Buff passivo (aplicado no inimigo)
- **Gatilho:** Passivo — sempre ativo enquanto o efeito existir
- **Comportamento:** Aumenta o dano de todas as `DamageCard` usadas pela entidade afligida em X pontos. Jon Jones aplica 2 acúmulos de Força em si mesmo a cada turno.

---

## Cartas que aplicam efeitos

### `bleedingCard` — "golpe lascerante"
- Custo: 3 de fôlego
- Aplica **3 acúmulos de Sangramento** no inimigo
- O sangramento age no final de cada turno do herói

### `TimeoutCard` — "segundo fôlego"
- Custo: 4 de fôlego
- Aplica **2 acúmulos de Recuperação** no próprio herói
- A cura age no início de cada turno do herói

---

## Padrão de Design Observer

A classe `Turns` age como **Publisher**. Mantém uma lista de `Effect` (Subscribers) e os notifica via `notifyEvent()` sempre que um evento de combate ocorre:

| Evento         | Descrição                        |
|----------------|----------------------------------|
| `HEROSTART`    | Início do turno do herói         |
| `HEROFINISH`   | Fim do turno do herói            |
| `ENEMYSTART`   | Início do turno do inimigo       |
| `ENEMYFINISH`  | Fim do turno do inimigo          |

Cada `Effect` implementa `beNotified(Turns turn)` e decide sozinho se deve agir com base no `turn.currentEvent`. Efeitos se inscrevem via `turns.subscribe(effect)` e são removidos via `turns.unsubscribe(effect)` quando seus acúmulos chegam a zero.

---

## Estrutura de arquivos

```
src/
├── App.java            — Ponto de entrada
├── Entity.java         — Classe abstrata base para herói e inimigos
├── Hero.java           — Herói jogável
├── Enemy.java          — Classe abstrata para inimigos
├── JonJones.java       — Inimigo: aplica Força em si mesmo
├── ConnnorMcGregor.java— Inimigo: aplica Veneno no herói
├── Card.java           — Classe abstrata de carta
├── DamageCard.java     — Carta de ataque (com bônus de Força)
├── ShieldCard.java     — Carta de defesa
├── bleedingCard.java   — Carta que aplica Sangramento (NOVA)
├── TimeoutCard.java    — Carta que aplica Recuperação (NOVA)
├── Effect.java         — Classe abstrata de efeito (Subscriber)
├── Bleeding.java       — Efeito Sangramento (NOVO)
├── Strength.java       — Efeito Força (NOVO)
├── Timeout.java        — Efeito Recuperação
├── Turns.java          — Gerenciador de turnos (Publisher)
├── PileOfCards.java    — Pilha genérica de cartas
├── PurchasePile.java   — Baralho de compra
├── DiscardPile.java    — Pilha de descarte
└── PlayerHand.java     — Mão do jogador
```