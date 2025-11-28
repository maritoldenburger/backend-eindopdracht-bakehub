package nl.maritoldenburger.bakehub.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class RecipeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldGetAllRecipes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipes"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").exists())
                .andReturn();
    }

    @Test
    void shouldGetRecipeById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipes/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.servings").exists())
                .andReturn();
    }

    @Test
    void shouldPostRecipe() throws Exception {
        String requestJson = """
                {
                    "name": "Novi Bananenbrood",
                    "imageUrl": "bananenbrood.jpg",
                    "description": "Heerlijk bananenbrood",
                    "instructions": "1. Prak de bananen. 2. Meng alle ingrediënten.",
                    "servings": 12,
                    "preparationTime": 60,
                    "ingredients": [
                        {
                            "name": "bloem",
                            "quantity": 250.0,
                            "unit": "GRAM"
                        }
                    ]
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/recipes/category/1")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Novi Bananenbrood"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.servings").value(12))
                .andReturn();
    }

    @Test
    void shouldUpdateRecipe() throws Exception {
        String requestJson = """
                {
                    "name": "Aangepaste Speculoos cheesecake",
                    "imageUrl": "cheesecake.jpg",
                    "description": "Nu ook vegan",
                    "instructions": "Nieuwe instructies",
                    "servings": 8,
                    "preparationTime": 45,
                    "ingredients": []
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.put("/recipes/1")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Aangepaste Speculoos cheesecake"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.servings").value(8))
                .andReturn();
    }

    @Test
    void shouldDeleteRecipe() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/recipes/6"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();
    }
}