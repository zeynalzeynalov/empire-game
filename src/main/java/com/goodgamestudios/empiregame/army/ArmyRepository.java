package com.goodgamestudios.empiregame.army;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArmyRepository {

    List<Army> armies = new ArrayList<>();

    public void saveArmy(Army army) {

        armies.add(army);
        System.out.println("Army is saved to repository!");
    }
}
