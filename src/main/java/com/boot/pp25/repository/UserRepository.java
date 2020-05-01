package com.boot.pp25.repository;

import com.boot.pp25.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

//    @Modifying
//    @Query("update User u set u.userName = :userName, u.lastName = :lastName, u.password = :password, u.age = :age," +
//            "u.email = :email where u.id = :id")
//    int editUser(@Param("userName") String userName, @Param("lastName") String lastName, @Param("password") String password,
//                 @Param("age") Integer age, @Param("id") Long id);
}
