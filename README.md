
-----------------------------------------------------------------------------------------------------------------
# ⚔️ A Luta — Jogo de Combate por Turnos em Java

## Descrição

**A Luta** é um jogo de combate por turnos inspirado em *Slay the Spire*, desenvolvido em Java. O jogador controla o herói **Anderson Silva** em uma batalha contra o inimigo **Jon Jones**.

A cada turno, o jogador dispõe de um recurso chamado **fôlego** (stamina), que é gasto ao usar cartas de ação. As cartas podem causar dano ao inimigo ou conceder escudo ao herói, bloqueando parte do dano recebido. Após o turno do jogador, o inimigo age automaticamente com movimentos aleatórios. O combate continua até que um dos lutadores seja derrotado.

---

## Mecânicas do Jogo

- **Vida (Health):** Cada personagem possui uma quantidade de vida. Ao chegar a zero, o personagem é derrotado.
- **Fôlego (Stamina):** Recurso gasto ao usar cartas. É totalmente restaurado no início de cada turno.
- **Escudo (Shield):** Absorve dano antes que ele reduza a vida. É zerado no início de cada novo turno.
- **Turnos:** O jogador age primeiro, podendo usar quantas cartas quiser enquanto tiver fôlego. Em seguida, o inimigo age automaticamente.

---

## Cartas Disponíveis

| # | Carta             | Tipo    | Efeito         | Custo de Fôlego |
|---|-------------------|---------|----------------|-----------------|
| 1 | Jab               | Ataque  | 8 de dano      | 3               |
| 2 | Direto (Cross)    | Ataque  | 14 de dano     | 5               |
| 3 | Chute na perna    | Ataque  | 18 de dano     | 8               |
| 4 | Chute na cabeça   | Ataque  | 24 de dano     | 12              |
| 5 | Focar             | Escudo  | 10 de bloqueio | 2               |

---

## Estrutura do Projeto

```
├── App.java          # Ponto de entrada; gerencia o loop principal do jogo
├── Hero.java         # Classe do herói controlado pelo jogador
├── Enemy.java        # Classe do inimigo com IA de turno automático
├── DamageCard.java   # Carta de ataque que causa dano ao inimigo
└── ShieldCard.java   # Carta de defesa que concede escudo ao herói
```

---

## Pré-requisitos

- **Java JDK 8 ou superior** instalado
- Terminal / Prompt de Comando

Para verificar se o Java está instalado:

```bash
java -version
```

---

## Compilação

No terminal, dentro da pasta do projeto, compile todos os arquivos `.java`:

```bash
javac *.java
```

---

## Execução

Após compilar, execute o programa com:

```bash
java App
```

O jogo iniciará no terminal e solicitará suas escolhas a cada turno. Digite o número da ação desejada e pressione **Enter**.

---

## Exemplo de Turno

```
----------------------------------
Anderson Silva (vida: 40/40) (reflexos: 0)
VS
Jon Jones (vida: 42/42) (reflexos: 0)

20 de fôlego disponível
1 - Golpear com jab (Dano: 8 | Custo: 3)
2 - Golpear com direto (Dano: 14 | Custo: 5)
3 - Golpear com chute na perna (Dano: 18 | Custo: 8)
4 - Golpear com chute na cabeça (Dano: 24 | Custo: 12)
5 - focar no próximo movimento do adversário (Bloqueio: 10 | Custo: 2)
6 - Encerrar turno
```