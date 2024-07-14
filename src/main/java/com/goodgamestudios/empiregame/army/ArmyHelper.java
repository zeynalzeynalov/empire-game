package com.goodgamestudios.empiregame.army;

import com.goodgamestudios.empiregame.troop.TroopType;

public class ArmyHelper {

    public static void printArmyStatus(int[][] armyRandomMemory) {
        for (TroopType troopType : TroopType.values()) {
            int troopTypeIndex = troopType.ordinal();

            for(int i=0; i<armyRandomMemory[troopTypeIndex].length; i++) {
                System.out.print(" " + armyRandomMemory[troopTypeIndex][i]);
            }
            System.out.println();
        }
    }
}
