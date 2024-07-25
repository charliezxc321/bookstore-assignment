package com.springboot.demo.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.demo.bookstore.components.requests.BookRequest;
import com.springboot.demo.bookstore.components.responses.AuthorResponse;
import com.springboot.demo.bookstore.components.responses.BookResponse;
import com.springboot.demo.bookstore.service.BookstoreService;
import com.springboot.demo.bookstore.utility.BuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@EnableWebMvc
public class BookstoreControllerTest {
    @MockBean
    private BookstoreService bookstoreService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    public void saveBookTest() throws Exception {
        Mockito.when(bookstoreService.saveBook(any())).thenReturn(BuilderUtil.buildBookResponse());

        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                .contentType(MediaType.valueOf("application/json"))
                .accept(MediaType.valueOf("application/json"))
                .content(toJson(BuilderUtil.buildBookRequest())))
                .andExpect(status().isOk())
                .andExpect(content().string(toJson(BuilderUtil.buildBookResponse())));
    }


    @Test
    public void updateBookTest() throws Exception {
        Mockito.when(bookstoreService.updateBook(any())).thenReturn(BuilderUtil.buildBookResponse());

        mockMvc.perform(MockMvcRequestBuilders.put("/books")
                .contentType(MediaType.valueOf("application/json"))
                .accept(MediaType.valueOf("application/json"))
                .content(toJson(BuilderUtil.buildBookRequest())))
                .andExpect(status().isOk())
                .andExpect(content().string(toJson(BuilderUtil.buildBookResponse())));
    }

    @Test
    public void searchBookPositiveTest() throws Exception {
        Mockito.when(bookstoreService.findBooks(anyString(), anyString(), anyString())).thenReturn(BuilderUtil.buildBookResponses());

        mockMvc.perform(MockMvcRequestBuilders.get("/books/search")
                .param("title", "title-test")
                .param("author", "author-test"))
                .andExpect(status().isOk())
                .andExpect(content().string(toJson(BuilderUtil.buildBookResponses())));
    }

    @Test
    public void searchBookNegativeTest() throws Exception {
        Mockito.when(bookstoreService.findBooks(anyString(), anyString(), anyString())).thenReturn(BuilderUtil.buildBookResponses());

        mockMvc.perform(MockMvcRequestBuilders.get("/books/search"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteBookPositiveTest() throws Exception {
        Mockito.doNothing().when(bookstoreService).deleteBook(any());

        mockMvc.perform(MockMvcRequestBuilders.delete("/books?isbn=8989898989898"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBookNegativeTest() throws Exception {
        Mockito.doNothing().when(bookstoreService).deleteBook(any());

        mockMvc.perform(MockMvcRequestBuilders.delete("/books?isb1n=8989898989898"))
                .andExpect(status().is5xxServerError());
    }

    String toJson(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }
}
