package com.springboot.demo.bookstore.service;

import com.springboot.demo.bookstore.entity.UserDetail;
import com.springboot.demo.bookstore.exception.BookstoreException;
import com.springboot.demo.bookstore.exception.BookstoreExceptionConstants;
import com.springboot.demo.bookstore.repository.UserDetailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The class provides the implementation for the UserDetailsService interface.
 * It will be used by the SecurityConfiguration class
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService{

    @Autowired
    private UserDetailRepository userDetailRepository;

    /**
     * This method fetches the user from the database by the username input
     * @param username - The username of the user
     * @return a UserDetails object
     */
    @Override
    public UserDetails loadUserByUsername(String username) {

        log.info("Fetching the user");
        Optional<UserDetail> userDetailOptional = userDetailRepository.findByUsername(username);
        if(userDetailOptional.isPresent()){
            var userDetail = userDetailOptional.get();
            log.info("Returning the fetched user");
            return User.builder()
                    .username(userDetail.getUsername())
                    .password(userDetail.getPassword())
                    .roles(convertToList(userDetail.getRole()))
                    .build();
        }
        else
            throw new BookstoreException(HttpStatus.UNAUTHORIZED, BookstoreExceptionConstants.USER_NOT_FOUND.getDescription());
    }

    private String[] convertToList(String roles) {
        if(roles == null)
            return new String[]{"USER"};
        else
            return roles.split(",");
    }
}
