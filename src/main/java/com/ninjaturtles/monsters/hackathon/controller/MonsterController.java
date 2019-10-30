package com.ninjaturtles.monsters.hackathon.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaturtles.monsters.hackathon.model.Monster;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
public class MonsterController {

    private static final String MONSTER_URL = "https://hackathon-wild-hackoween.herokuapp.com/";

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/monster")
        public String monster (Model model, @RequestParam Long id) {

            WebClient webClient = WebClient.create(MONSTER_URL);
            Mono<String> call = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/monster/{id}")
                            .build(id))
                    .retrieve()
                    .bodyToMono(String.class);

            String response = call.block();

            ObjectMapper objectMapper = new ObjectMapper();
            Monster monsterObject = null;
            try {
                monsterObject = objectMapper.readValue(response, Monster.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            model.addAttribute("monsterInfos", monsterObject);

            return "monster";
        }
}
