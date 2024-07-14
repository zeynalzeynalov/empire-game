package com.goodgamestudios.empiregame.army;

import com.goodgamestudios.empiregame.troop.TroopType;
import com.goodgamestudios.empiregame.troop.Troop;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class ArmyService {

    private final ArmyRepository armyRepository;

    // We create array of length maxCallLimit for each troop type
    private int[][] armyRandomMemory;

    // We store current index of Maximum array index for each troop type
    private int[] soldierTypeLastCalculatedArrayIndex;

    // Read repeated call limit from application.properties file.
    @Value( "${empiregame.maxcalllimit}" )
    private int empiregame_max_call_limit;

    private int currentArmyId;

    public ArmyService(ArmyRepository armyRepository){

        this.armyRepository = armyRepository;
    }

    public void init(@Value("${empiregame.maxcalllimit}") int empiregame_max_call_limit) {
        System.out.println("Started ArmyService()");

        this.empiregame_max_call_limit = empiregame_max_call_limit;

        System.out.println(String.format("empiregame_max_call_limit = %d", empiregame_max_call_limit));

        currentArmyId = 0;

        armyRandomMemory = new int[TroopType.values().length][empiregame_max_call_limit];
        soldierTypeLastCalculatedArrayIndex = new int[TroopType.values().length];

        for (TroopType troopType : TroopType.values()) {
            soldierTypeLastCalculatedArrayIndex[troopType.ordinal()] = empiregame_max_call_limit-1;
        }

        for(int i=0; i<empiregame_max_call_limit; i++) {
            for (TroopType troopType : TroopType.values()) {

                // Fill arrays from 1 to limit
                armyRandomMemory[troopType.ordinal()][i]=i;
            }
        }

        ArmyHelper.printArmyStatus(armyRandomMemory);
    }

    public Army createArmy(ArmyCreateRequest request) {

        //Generate new Army
        Army newArmy = new Army();
        newArmy.setName(String.format("Army %d", ++currentArmyId));
        newArmy.setTroops(generateTroopList(request.getArmyMenStrong()));

        armyRepository.saveArmy(newArmy);

        return newArmy;
    }

    // ------------- Creation of random men strong troop list -------------
    private List<Troop> generateTroopList(int requestedTotalArmyMenStrong) {

        System.out.println(String.format("Received new Army create request: %s", requestedTotalArmyMenStrong));

        System.out.println("soldierTypeLastCalculatedArrayIndex=" + soldierTypeLastCalculatedArrayIndex);

        int TroopTypeCount = TroopType.values().length;

        Random random = new Random();

        List<Troop> troopList = new ArrayList<>();

        List<Integer> originalRandoms = new ArrayList<>();

        for (TroopType troopType : TroopType.values()) {

            int soldierTypeIndex = troopType.ordinal();

            int max = soldierTypeLastCalculatedArrayIndex[soldierTypeIndex];
            System.out.println("max:" + max);
            // Get random array index from 1 to limit for this troop type
            int randIndex = random.nextInt(max);

            System.out.println(String.format("Troop type: %s troopTypeIndex: %d RandomArrayIndex: %d", troopType, soldierTypeIndex, randIndex));

            int temp = armyRandomMemory[soldierTypeIndex][randIndex];
            armyRandomMemory[soldierTypeIndex][randIndex] = armyRandomMemory[soldierTypeIndex][max];
            armyRandomMemory[soldierTypeIndex][max] = temp;

            originalRandoms.add(temp);

            soldierTypeLastCalculatedArrayIndex[soldierTypeIndex]--;
        }

        double sumOfOriginalRandoms = originalRandoms.stream().mapToInt(Integer::intValue).sum();
        double factor = ((double)requestedTotalArmyMenStrong) / sumOfOriginalRandoms;

        System.out.println(String.format("\nsumOfOriginalRandoms=%.5f factor=%.5f", sumOfOriginalRandoms, factor));

        List<Integer> convertedRandoms = new ArrayList<>();
        int sumOfConvertedRandoms = 0;
        for(int i = 0; i < originalRandoms.size() - 1; i++) {

            int originalRand = originalRandoms.get(i);
            int tempConverted = ((int)(originalRand * factor));

            // Prevent 0 troop men strength
            if(tempConverted == 0)
                tempConverted++;

            int convertedRand = tempConverted;

            sumOfConvertedRandoms += convertedRand;
            convertedRandoms.add(convertedRand);
        }

        int lastConvertedRand = requestedTotalArmyMenStrong - sumOfConvertedRandoms;
        convertedRandoms.add(lastConvertedRand);

        System.out.println("originalRandoms: " + originalRandoms);
        System.out.println("convertedRandoms: " + convertedRandoms);

        for (TroopType troopType : TroopType.values()) {

            Troop troop = new Troop();
            troop.setTroopType(troopType);
            troop.setMenStrong(convertedRandoms.get(troopType.ordinal()));

            troopList.add(troop);
        }

        System.out.println();

        ArmyHelper.printArmyStatus(armyRandomMemory);

        return troopList;
    }
}
