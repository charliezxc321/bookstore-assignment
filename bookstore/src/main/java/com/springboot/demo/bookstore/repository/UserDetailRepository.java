package com.springboot.demo.bookstore.repository;

import com.springboot.demo.bookstore.entity.Book;
import com.springboot.demo.bookstore.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This class serves as a repository for managing the UserDetail entities
 */
@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long>, JpaSpecificationExecutor<UserDetail> {

//    List<UserDetail> findAll();
    Optional<UserDetail> findByUsername(String username);
}
