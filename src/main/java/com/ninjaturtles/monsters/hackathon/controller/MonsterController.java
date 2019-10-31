package com.ninjaturtles.monsters.hackathon.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaturtles.monsters.hackathon.model.Monster;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
public class MonsterController {

    private static final String MONSTER_URL = "https://hackathon-wild-hackoween.herokuapp.com/";

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/catalogue/{id}")
    public String catalogue(Model model, @PathVariable Long id) {
        Monster monsterObject = getMonsterFromAPI(id);
        model.addAttribute("monsterInfos", monsterObject);
        return "catalogue";
    }

    @GetMapping("/profile/{id}")
    public String profile(Model model, @PathVariable Long id) {
        Monster monsterObject = getMonsterFromAPI(id);
        model.addAttribute("monsterInfos", monsterObject);
        return "profile";
    }

    @GetMapping("/match/{id}")
    public String match(Model model, @PathVariable Long id) {
        Monster monsterObject = getMonsterFromAPI(id);
        model.addAttribute("monsterInfos", monsterObject);
        return "match";
    }

    @GetMapping("/account/{id}")
    public String account(Model model, @PathVariable Long id) {
        Monster monsterObject = getMonsterFromAPI(id);
        model.addAttribute("monsterInfos", monsterObject);
        return "account";
    }

    @GetMapping("/discussion/{id}")
    public String discussion(Model model, @PathVariable Long id) {
        Monster monsterObject = getMonsterFromAPI(id);
        model.addAttribute("monsterInfos", monsterObject);
        return "discussion";
    }

    @GetMapping("/listmatch/{id}")
    public String listmatch(Model model, @PathVariable Long id) {
        Monster monsterObject = getMonsterFromAPI(id);
        model.addAttribute("monsterInfos", monsterObject);
        return "listmatch";
    }

    private Monster getMonsterFromAPI(Long id) {
        WebClient webClient = WebClient.create(MONSTER_URL);
        Mono<String> call = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/monsters/{id}")
                        .build(id))
                .retrieve()
                .bodyToMono(String.class);

        String response = call.block();

        ObjectMapper objectMapper = new ObjectMapper();
        Monster monsterObject = null;
        try {
            JsonNode root = objectMapper.readTree(response);
            monsterObject = objectMapper.convertValue(root.get("monster"), Monster.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return monsterObject;
    }
}
