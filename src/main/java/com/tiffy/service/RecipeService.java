package com.tiffy.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiffy.dto.RecipeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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

    public List<RecipeDto> getRecipes(String ingredients) {
        String url = baseUrl + apiKey + "/" + serviceId + "/" + requestType
                + "/" + startIdx + "/" + endIdx + "/RCP_PARTS_DTLS=" + ingredients;

        String response = restTemplate.getForObject(url, String.class);
        List<RecipeDto> recipeList = new ArrayList<>();
        String manualNumber[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode recipes = jsonNode.path("COOKRCP01").path("row");
            for (JsonNode recipeNode : recipes) {
                RecipeDto recipe = new RecipeDto();
                recipe.setRcpNm(recipeNode.path("RCP_NM").asText());
                recipe.setRcpWay2(recipeNode.path("RCP_WAY2").asText());
                recipe.setRcpPat2(recipeNode.path("RCP_PAT2").asText());
                recipe.setAttFileNoMain(recipeNode.path("ATT_FILE_NO_MAIN").asText());
                recipe.setAttFileNoMk(recipeNode.path("ATT_FILE_NO_MK").asText());
                recipe.setRcpPartsDtls(recipeNode.path("RCP_PARTS_DTLS").asText());
                recipe.setManual01(recipeNode.path("MANUAL01").asText());
                recipe.setManualImg01(recipeNode.path("MANUAL_IMG01").asText());
                recipe.setManual02(recipeNode.path("MANUAL02").asText());
                recipe.setManualImg02(recipeNode.path("MANUAL_IMG02").asText());
                recipe.setManual03(recipeNode.path("MANUAL03").asText());
                recipe.setManualImg03(recipeNode.path("MANUAL_IMG03").asText());
                recipe.setManual04(recipeNode.path("MANUAL04").asText());
                recipe.setManualImg04(recipeNode.path("MANUAL_IMG04").asText());
                recipe.setManual05(recipeNode.path("MANUAL05").asText());
                recipe.setManualImg05(recipeNode.path("MANUAL_IMG05").asText());
                recipe.setManual06(recipeNode.path("MANUAL06").asText());
                recipe.setManualImg06(recipeNode.path("MANUAL_IMG06").asText());
                recipe.setManual07(recipeNode.path("MANUAL07").asText());
                recipe.setManualImg07(recipeNode.path("MANUAL_IMG07").asText());
                recipe.setManual08(recipeNode.path("MANUAL08").asText());
                recipe.setManualImg08(recipeNode.path("MANUAL_IMG08").asText());
                recipe.setManual09(recipeNode.path("MANUAL09").asText());
                recipe.setManualImg09(recipeNode.path("MANUAL_IMG09").asText());
                recipe.setManual10(recipeNode.path("MANUAL10").asText());
                recipe.setManualImg10(recipeNode.path("MANUAL_IMG10").asText());
                recipe.setManual11(recipeNode.path("MANUAL11").asText());
                recipe.setManualImg11(recipeNode.path("MANUAL_IMG11").asText());
                recipe.setManual12(recipeNode.path("MANUAL12").asText());
                recipe.setManualImg12(recipeNode.path("MANUAL_IMG12").asText());
                recipe.setManual13(recipeNode.path("MANUAL13").asText());
                recipe.setManualImg13(recipeNode.path("MANUAL_IMG13").asText());
                recipe.setManual14(recipeNode.path("MANUAL14").asText());
                recipe.setManualImg14(recipeNode.path("MANUAL_IMG14").asText());
                recipe.setManual15(recipeNode.path("MANUAL15").asText());
                recipe.setManualImg15(recipeNode.path("MANUAL_IMG15").asText());
                recipe.setManual16(recipeNode.path("MANUAL16").asText());
                recipe.setManualImg16(recipeNode.path("MANUAL_IMG16").asText());
                recipe.setManual17(recipeNode.path("MANUAL17").asText());
                recipe.setManualImg17(recipeNode.path("MANUAL_IMG17").asText());
                recipe.setManual18(recipeNode.path("MANUAL18").asText());
                recipe.setManualImg18(recipeNode.path("MANUAL_IMG18").asText());
                recipe.setManual19(recipeNode.path("MANUAL19").asText());
                recipe.setManualImg19(recipeNode.path("MANUAL_IMG19").asText());
                recipe.setManual20(recipeNode.path("MANUAL20").asText());
                recipe.setManualImg20(recipeNode.path("MANUAL_IMG20").asText());

                recipeList.add(recipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipeList;
    }
}
