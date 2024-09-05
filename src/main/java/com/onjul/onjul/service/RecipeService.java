package com.onjul.onjul.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RecipeService {

    @Value("${api.recipe.key}")
    private String apiKey;

    private final String serviceId = "COOKRCP01";
    private final String baseUrl = "http://openapi.foodsafetykorea.go.kr/api/";
    private final String requestType = "json";
    private final int startIdx = 1;
    private final int endIdx = 10;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public RecipeService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public String getRecipes(String ingredients) {
        String url = baseUrl + apiKey + "/" + serviceId + "/" + requestType
                + "/" + startIdx + "/" + endIdx + "/RCP_PARTS_DTLS=" + ingredients;

        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode recipes = jsonNode.path("COOKRCP01").path("row");

            StringBuilder result = new StringBuilder();
            for (JsonNode recipe : recipes) {
                result.append("메뉴명: ").append(recipe.path("RCP_NM").asText()).append("\n")
                        .append("조리방법: ").append(recipe.path("RCP_WAY2").asText()).append("\n")
                        .append("요리종류: ").append(recipe.path("RCP_PAT2").asText()).append("\n")
                        .append("재료정보: ").append(recipe.path("RCP_PARTS_DTLS").asText()).append("\n")
                        .append("만드는 법: ").append(recipe.path("MANUAL01").asText()).append("\n")
                        .append("------\n");
            }

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while parsing JSON response";
        }
    }
}
