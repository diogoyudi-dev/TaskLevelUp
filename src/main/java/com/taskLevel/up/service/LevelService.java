package com.taskLevel.up.service;

import org.springframework.stereotype.Service;

@Service
public class LevelService {

    public static final double XP_BASE = 100.00;
    public static final double EXPONENCIAL = 1.50;

    public int xpParaProximoNivel(int level){
        return (int) Math.round(XP_BASE * Math.pow(level, EXPONENCIAL));
    }

    public int calculaLevel(int totalXp){
        int level = 1;
        int sobraXp = totalXp;

        while (sobraXp >= xpParaProximoNivel(level)){
            sobraXp -= xpParaProximoNivel(level);
            level++;
        }
        return level;
    }

    public int xpSobrandoDoNivel(int totalXp){
        int level = 1;
        int sobraXp = totalXp;

        while (sobraXp >= xpParaProximoNivel(level)){
            sobraXp -= xpParaProximoNivel(level);
            level++;
        }
        return sobraXp;
    }

        public int xpFaltandoParaProximoNivel(int totalXp){
            return xpParaProximoNivel(calculaLevel(totalXp)) - xpSobrandoDoNivel(totalXp);
        }



}
