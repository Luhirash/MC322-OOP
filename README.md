# A Luta — Jogo de Combate por Turnos em Java

-Para fazer o readme(design), formatação da documentação em javadoc e parte das imagens nas interfaces exibidas no terminal, foi utilizada ferramenta de IA generativa(LLM)

## Descrição

**A Luta** é um jogo de combate por turnos inspirado em *Slay the Spire*, desenvolvido em Java.

O jogador controla o herói **Anderson Silva** e avança por uma **árvore de progressão de combates**. A jornada começa contra o oponente inicial (**Kenneth Allen**) e, a cada vitória, o jogador escolhe seu próximo adversário navegando pelos galhos da árvore até chegar aos chefões finais (como **Jon Jones** ou **Connor McGregor**). A cada turno, cartas são compradas de um baralho e o jogador decide quais usar, gastando **fôlego** (stamina). As cartas podem causar dano ao inimigo ou conceder **bloqueio** (escudo) ao herói. Após o turno do jogador, o inimigo age automaticamente com movimentos escolhidos no início do turno. O combate continua até que um dos lutadores seja derrotado.



## Como compilar


A partir da **raiz do repositório**, execute:

```bash
./gradlew build
```

ou

```bash
./gradlew build
```


## Como executar

```bash
java -cp bin Core.App
```

ou

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

O jogador controla **Anderson Silva** em uma jornada contínua de batalhas de cartas. A progressão funciona da seguinte forma:

1. O jogo começa no nó raiz da árvore contra **Kenneth Allen**.
2. Ao vencer, o jogador escolhe entre as próximas ramificações (ex: **Francis Ngannou** ou **Max Holloway**).
3. O jogo continua até o herói ser derrotado ou chegar ao final da árvore (nós folha).

Durante a batalha, a cada turno:
1. O inimigo revela suas intenções (cartas que pretende usar).
2. O jogador compra 3 cartas do baralho e escolhe quais jogar, gastando fôlego.
3. Ao encerrar o turno, o inimigo executa suas ações.

O combate termina quando o herói ou o inimigo chega a 0 de vida. Se o herói vencer, seus efeitos de status são limpos para a próxima luta da árvore.

---

## Efeitos implementados

### 1. Sangramento (`Bleeding`)
- **Tipo:** Debuff (aplicado no inimigo ou no herói)
- **Gatilho:** Final do turno do oponente (ex: `HEROFINISH` se o inimigo estiver sangrando)
- **Comportamento:** A entidade afligida sofre X de dano, onde X é a quantidade atual de acúmulos. Em seguida, perde 1 acúmulo. Quando os acúmulos chegam a zero, o efeito é removido.
- **Exemplo:** 3 acúmulos de Sangramento → 3 de dano no turno 1, 2 no turno 2, 1 no turno 3, depois removido.

### 2. Healing / Recuperação (`Timeout`)
- **Tipo:** Buff (aplicado no herói)
- **Gatilho:** Início do turno do herói (`HEROSTART`)
- **Comportamento:** O herói recupera X pontos de vida, onde X é a quantidade de acúmulos. Em seguida, perde 1 acúmulo. Quando os acúmulos chegam a zero, o efeito é removido.
- **Exemplo:** 2 acúmulos de Recuperação → cura 2 de vida no turno 1, cura 2 no turno 2 (acúmulos decrementam até 0).

### 3. Força (`Strength`)
- **Tipo:** Buff passivo (aplicado no herói ou inimigo)
- **Gatilho:** Passivo — sempre ativo enquanto o efeito existir (Decai no fim do turno do dono)
- **Comportamento:** Aumenta o dano de todas as `DamageCard` usadas pela entidade afligida em X pontos. 

---

## Sistemas de Progressão

Além das batalhas, o jogador encontra dois eventos especiais na árvore de progressão que utilizam padrões de design distintos.

### Loja do Octógono — Padrão Strategy

A **Loja** (`Shop extends Event`) permite ao herói gastar moedas ganhas nas batalhas para comprar **relíquias** — itens passivos que concedem bônus permanentes durante toda a jornada.

**Como funciona no jogo:** ao entrar na loja, o jogador vê o catálogo com preços, escolhe um item e tem o valor debitado automaticamente. Relíquias já compradas são sinalizadas e não podem ser adquiridas novamente.

**Padrão Strategy:** cada relíquia implementa a interface `RelicStrategy`, que define os hooks `onBattleStart(Hero)` e `onTurnStart(Hero)`. A loja apenas exibe `getName()`, `getDescription()` e `getCost()` — sem saber o que cada relíquia faz internamente. O `Hero` mantém uma `List<RelicStrategy>` e aciona os hooks nos momentos certos (`triggerBattleStartRelics()` / `triggerTurnStartRelics()`). Adicionar uma nova relíquia é só criar uma nova classe e registrá-la na loja, sem alterar nada mais.

Relíquias implementadas:
- **Protetor Bucal de Campeão** — concede +5 de escudo no início de cada batalha.
- **Bandagens do Spider** — recupera +1 de vida no início de cada turno.

---

### Camp de Treinamento — Padrão Command

O **Camp de Treinamento** (`TrainingCamp extends Event`) representa uma pausa na Academia Chute Boxe. O herói escolhe **uma única ação** por visita para se preparar para o próximo combate.

**Como funciona no jogo:** o evento exibe as ações disponíveis; o jogador escolhe uma e ela é executada imediatamente, encerrando a visita.

**Padrão Command:** as ações implementam a interface `CampActionCommand` com o método `execute(Hero)`. O `TrainingCamp` atua como **Invoker** — mantém uma `List<CampActionCommand>` e chama `execute()` sem saber o que cada comando faz. O `Hero` é o **Receiver**, afetado pela ação concreta. Para adicionar novas ações ao Camp, basta criar uma nova classe e registrá-la, sem modificar o `TrainingCamp`.

Ações implementadas:
- **Banheira de Gelo** (`IceBathCommand`) — recupera 30% da vida máxima do herói.
- **Treino de Sparring** (`SparringCommand`) — o jogador escolhe uma carta do baralho para aplicar *upgrade* permanente.

---

## Padrão de Design Observer

O jogo isola a lógica de eventos na classe `GameManager`, que atua como o **Publisher** (implementando a interface genérica `Publisher`). O `GameManager` mantém uma lista de `Effect` (que agem como **Observers** via interface `Observer`) e os notifica chamando `notifyEvent()` sempre que a classe `Turns` sinaliza que um evento de combate ocorreu:

| Evento         | Descrição                        |
|----------------|----------------------------------|
| `HEROSTART`    | Início do turno do herói         |
| `HEROFINISH`   | Fim do turno do herói            |
| `ENEMYSTART`   | Início do turno do inimigo       |
| `ENEMYFINISH`  | Fim do turno do inimigo          |

Cada `Effect` implementa `beNotified(GameManager gameManager)` e decide sozinho se deve agir com base no `gameManager.currentEvent`. Efeitos se inscrevem via `gameManager.subscribe(effect)` e são removidos via `gameManager.unsubscribe(effect)` quando seus acúmulos chegam a zero ou quando a batalha acaba via `gameManager.unsubscribeAll()`.

---

## Estrutura de arquivos

```
src/
├── Camp/
│   ├── CampActionCommand.java  — Interface Command para ações do Camp
│   ├── IceBathCommand.java     — Recupera 30% da vida máxima
│   └── SparringCommand.java    — Aplica upgrade em uma carta do baralho
├── Relics/
│   ├── RelicStrategy.java      — Interface Strategy para relíquias passivas
│   ├── MouthGuardStrategy.java — +5 escudo no início de cada batalha
│   └── HandWrapStrategy.java   — +1 vida no início de cada turno
├── Core/
│   ├── App.java            — Ponto de entrada e setup inicial do jogo
│   ├── Battle.java         — Controla o fluxo de uma batalha isolada
│   ├── EnemyNode.java      — Estrutura de Árvore Binária para progressão
│   ├── GameManager.java    — Gerenciador central (Publisher do Observer)
│   ├── Observer.java       — Interface para entidades que escutam eventos
│   ├── Publisher.java      — Interface de emissão de eventos
│   └── Turns.java          — Gerenciador de turnos de uma batalha específica
├── Events/
│   ├── Event.java          — Classe abstrata base para todos os eventos
│   ├── Battle.java         — Evento de batalha contra um inimigo
│   ├── Choice.java         — Evento de escolha com três possibilidades
│   ├── Shop.java           — Loja de relíquias (Invoker do Strategy)
│   ├── TrainingCamp.java   — Camp de treinamento (Invoker do Command)
│   ├── EventNode.java      — Nó da árvore de progressão
│   └── EventTree.java      — Árvore completa de eventos do jogo
├── Entities/
│   ├── Entity.java         — Classe abstrata base para herói e inimigos
│   ├── Hero.java           — Herói jogável
│   ├── Enemy.java          — Classe abstrata para inimigos
│   └── (Inimigos).java     — Classes dos lutadores (JonJones, KennethAllen, etc)
├── Cards/
│   ├── Card.java           — Classe abstrata de carta
│   ├── DamageCard.java     — Carta de ataque (com bônus de Força)
│   ├── ShieldCard.java     — Carta de defesa
│   ├── bleedingCard.java   — Carta que aplica Sangramento
│   └── TimeoutCard.java    — Carta que aplica Recuperação
├── Effects/
│   ├── Effect.java         — Classe abstrata de efeito (Observer)
│   ├── Bleeding.java       — Efeito Sangramento
│   ├── Strength.java       — Efeito Força
│   └── Timeout.java        — Efeito Recuperação
└── Piles/
    ├── PileOfCards.java    — Pilha genérica de cartas
    ├── PurchasePile.java   — Baralho de compra
    ├── DiscardPile.java    — Pilha de descarte
    └── PlayerHand.java     — Mão do jogador
```