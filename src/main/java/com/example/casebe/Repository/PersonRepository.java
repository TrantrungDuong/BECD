package com.example.casebe.Repository;

import com.example.casebe.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByUsnAndPass(String usn, String pass);
    Person findById(long id);

}
