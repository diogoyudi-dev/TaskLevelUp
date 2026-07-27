package com.taskLevel.up.models;

public enum Dificuldade {
    FACIL(10),
    MEDIO(25),
    DIFICIL(50),
    EPICO(100);

    private final int xpBase;

    Dificuldade(int xpBase) {
        this.xpBase = xpBase;
    }

    public int getXpBase() {
        return xpBase;
    }
}
