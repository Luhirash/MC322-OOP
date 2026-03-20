# A Luta — Jogo de Combate por Turnos em Java

## Descrição

**A Luta** é um jogo de combate por turnos inspirado em *Slay the Spire*, desenvolvido em Java.

O jogador controla o herói **Anderson Silva** e escolhe um inimigo para enfrentar: **Jon Jones** ou **Connor McGregor**. A cada turno, cartas são compradas de um baralho e o jogador decide quais usar, gastando **fôlego** (stamina). As cartas podem causar dano ao inimigo ou conceder **reflexo** (escudo) ao herói. Após o turno do jogador, o inimigo age automaticamente com movimentos escolhidos no início do turno. O combate continua até que um dos lutadores seja derrotado.


## Hierarquia de Classes

O projeto foi refatorado utilizando **herança** e **classes abstratas** para eliminar duplicação de código:

```
Entity  (abstrata)
├── Hero
└── Enemy  (abstrata)
    ├── JonJones
    └── ConnnorMcGregor

Card  (abstrata)
├── DamageCard
└── ShieldCard

PileOfCards
├── PurchasePile
└── DiscardPile

PlayerHand
Turns
App
```

### `Entity` (abstrata)
Representa qualquer personagem da batalha. Centraliza os atributos `name`, `health`, `shield`, `stamina` e métodos como `receiveDamage`, `gainShield`, `isAlive`, `newTurn` e `printStats`, evitando repetição entre herói e inimigos.

### `Hero`
Herda de `Entity`. Representa o personagem controlado pelo jogador (Anderson Silva).

### `Enemy` (abstrata)
Herda de `Entity`. Contém a lógica de escolha aleatória de cartas (`chooseCards`) e declara os métodos abstratos `printIntentions` e `getHits`, que cada inimigo concreto deve implementar.

### `JonJones` e `ConnnorMcGregor`
Inimigos concretos que herdam de `Enemy`. Cada um possui seu próprio conjunto de cartas e implementa `printIntentions` com uma mensagem personalizada — parte do **desafio extra**.

### `Card` (abstrata)
Representa o conceito geral de uma carta jogável. Centraliza `name`, `description`, `staminaCost` e o método `tryCard`, que verifica se o herói tem fôlego suficiente antes de executar o efeito.

### `DamageCard`
Herda de `Card`. Causa dano ao inimigo ao ser usada.

### `ShieldCard`
Herda de `Card`. Concede reflexo (escudo) ao herói ao ser usada.

---

## Sistema de Baralho

O jogador possui um baralho gerenciado por três estruturas:

| Estrutura | Classe | Descrição |
|---|---|---|
| Pilha de compra | `PurchasePile` | Cartas disponíveis para compra a cada turno |
| Mão do jogador | `PlayerHand` | Cartas disponíveis para uso no turno atual (máximo 3) |
| Pilha de descarte | `DiscardPile` | Cartas usadas ou não jogadas ao fim do turno |

**Fluxo a cada turno:**
1. O herói recupera todo o seu fôlego e o escudo é zerado;
2. O jogador compra cartas da pilha de compra até encher a mão (3 cartas);
3. O jogador escolhe quais cartas usar ou encerra o turno manualmente;
4. Cartas usadas vão para a pilha de descarte imediatamente;
5. Cartas restantes na mão ao fim do turno também vão para o descarte;
6. O inimigo executa as ações que anunciou no início do turno.

Quando a pilha de compra se esgota, as cartas da pilha de descarte são embaralhadas e repostas como nova pilha de compra.

---

## Desafio Extra — Anúncio de Intenções

Implementado conforme o item 3.5 da tarefa. No início de cada turno, antes do jogador agir, o inimigo **anuncia quais cartas pretende usar** naquele turno. O método `printIntentions` é declarado como abstrato em `Enemy` e cada inimigo concreto o implementa com sua própria mensagem:

- **Jon Jones:** *"Jon Jones tem muita energia! Por isso pretende usar:"*
- **Connor McGregor:** *"Connor McGregor é leve e rápido, então vai usar:"*

Isso permite ao jogador montar sua estratégia antes de agir, priorizando ataque ou defesa de acordo com a ameaça do inimigo.

---

## Cartas Disponíveis

| Carta | Tipo | Efeito | Custo de Fôlego |
|---|---|---|---|
| Jab | Ataque | 5 de dano | 3 |
| Direto | Ataque | 8 de dano | 5 |
| Chute na perna | Ataque | 10 de dano | 6 |
| Chute na cabeça | Ataque | 12 de dano | 7 |
| Soco cruzado | Ataque | 9 de dano | 5 |
| Uppercut | Ataque | 11 de dano | 6 |
| Focar | Escudo | 5 de reflexo | 2 |
| Desviar | Escudo | 8 de reflexo | 3 |
| Andar para trás | Escudo | 2 de reflexo | 1 |
| Agachar | Escudo | 4 de reflexo | 2 |

---

## Inimigos

| Inimigo | Vida | Fôlego | Cartas |
|---|---|---|---|
| Jon Jones | 42 | 11 | Chute na perna, Soco cruzado, Desviar |
| Connor McGregor | 30 | 14 | Chute na cabeça, Direto, Andar para trás |

---

## Estrutura do Projeto

```
src/
├── App.java              # Ponto de entrada; gerencia o loop principal do jogo
├── Entity.java           # Classe abstrata base para herói e inimigos
├── Hero.java             # Herói controlado pelo jogador
├── Enemy.java            # Classe abstrata para inimigos; lógica de turno automático
├── JonJones.java         # Inimigo concreto com cartas e intenções próprias
├── ConnnorMcGregor.java  # Inimigo concreto com cartas e intenções próprias
├── Card.java             # Classe abstrata base para todas as cartas
├── DamageCard.java       # Carta de ataque que causa dano ao inimigo
├── ShieldCard.java       # Carta de defesa que concede reflexo ao herói
├── PileOfCards.java      # Estrutura base de pilha de cartas (LinkedList)
├── PurchasePile.java     # Pilha de compra; reabastece a partir do descarte
├── DiscardPile.java      # Pilha de descarte
├── PlayerHand.java       # Mão do jogador; gerencia compra e devolução de cartas
└── Turns.java            # Gerencia os turnos do herói e do inimigo
```

---

## Compilação

No terminal, a partir da raiz do repositório:

```bash
javac -d bin $(find src -name "*.java")
```

---

## Execução

```bash
java -cp bin App
```

O jogo iniciará no terminal. Escolha o inimigo e, a cada turno, digite o número da carta que deseja usar ou o número para encerrar o turno. Pressione **Enter** após cada escolha.