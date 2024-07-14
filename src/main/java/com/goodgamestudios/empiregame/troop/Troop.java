package com.goodgamestudios.empiregame.troop;

import lombok.Data;

@Data
public class Troop {

    private TroopType troopType;

    private Integer menStrong;

    //We can also use Soldier class as list and we can get length of soldiers ad menStrong of this troop.
    //private List<Soldier> soldiers;
}
