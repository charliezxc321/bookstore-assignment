package com.springboot.demo.bookstore.service;

import com.springboot.demo.bookstore.exception.BookstoreException;
import com.springboot.demo.bookstore.repository.UserDetailRepository;
import com.springboot.demo.bookstore.utility.BuilderUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserDetailServiceImplTest {

    @MockBean
    private UserDetailRepository userDetailRepository;

    @Autowired
    private UserDetailsService userDetailsService;


    @Test
    public void loadUserByUsernamePositiveTest() {
        Mockito.when(userDetailRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(BuilderUtil.buildUserDetail()));

        UserDetails userDetails = userDetailsService.loadUserByUsername("test-username");

        Assertions.assertEquals("test-username", userDetails.getUsername());
    }

    @Test
    public void loadUserByUsernameNegativeTest() {
        Mockito.when(userDetailRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        BookstoreException bookstoreException = Assertions.assertThrows(BookstoreException.class, () ->
                userDetailsService.loadUserByUsername ("test-username"));
        HttpStatus status = bookstoreException.getStatus();
        String message = bookstoreException.getMessage();

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, status);
        Assertions.assertEquals("User is not found", message);
    }

}
