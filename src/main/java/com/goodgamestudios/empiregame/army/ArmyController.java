package com.goodgamestudios.empiregame.army;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/army")
@RestController
@RequiredArgsConstructor
public class ArmyController {

    private final ArmyService armyService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String checkGameStatus() {

        return "Empire Game - Army service is running!";
    }

    @PostMapping
    public ResponseEntity createArmy(@RequestBody ArmyCreateRequest request) {

        return ResponseEntity.ok(armyService.createArmy(request));
    }
}
