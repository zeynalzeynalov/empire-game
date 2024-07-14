package com.goodgamestudios.empiregame.army;

import com.goodgamestudios.empiregame.troop.Troop;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ArmyGeneratedTroopMenStrengthTest {

    @Test
    void shouldGenerateNonRepeatArmyTroopStrengths() {

        ArmyService armyService = new ArmyService(new ArmyRepository());
        armyService.init(10);

        Map<String, Integer> armyStringMap = new HashMap<>();

        ArmyCreateRequest request = new ArmyCreateRequest();
        request.setArmyMenStrong(10);

        int callLimit = 9;

        for(int i=0; i<callLimit; i++) {
            Army army = armyService.createArmy(request);

            String armyString = "[";

            for (Troop troop : army.getTroops()) {
                armyString += String.format("{%s-%d}", troop.getTroopType(), troop.getMenStrong());
            }

            armyString += "]";

            armyStringMap.put(armyString, armyStringMap.getOrDefault(armyString,0) + 1);
        }

        System.out.println(armyStringMap);

        for(Map.Entry<String, Integer> e : armyStringMap.entrySet())
            if(e.getValue() > 1)
                System.out.println(e.getKey());

        assertThat(armyStringMap.size()).isEqualTo(9);
    }
}
