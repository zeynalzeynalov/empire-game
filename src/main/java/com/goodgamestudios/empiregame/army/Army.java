package com.goodgamestudios.empiregame.army;

import com.goodgamestudios.empiregame.troop.Troop;
import lombok.Data;

import java.util.List;

@Data
public class Army {

    private String name;

    private List<Troop> troops;
}
