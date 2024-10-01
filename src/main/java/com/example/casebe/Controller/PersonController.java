package com.example.casebe.Controller;

import com.example.casebe.Model.Person;
import com.example.casebe.Model.Product;
import com.example.casebe.Model.Role;
import com.example.casebe.Repository.PersonRepository;
import com.example.casebe.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/getperson")
    public ResponseEntity getAll() {
        return new ResponseEntity(personRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/getperson/{id}")
    public ResponseEntity getPersonById(@PathVariable long id){
        Person person = personRepository.findById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }


    @PostMapping("/registeracc")
    public ResponseEntity register(@RequestBody Person person) {
        if (person.getUsn() == null || person.getUsn().isEmpty() ||
                person.getPass() == null || person.getPass().isEmpty() ||
                person.getPhone() == null || person.getPhone().isEmpty()) {
            return new ResponseEntity<>("Username, password và SDT không được để trống", HttpStatus.BAD_REQUEST);
        }

        Role userRole = roleRepository.findByName("user");
        if (userRole == null) {
            return new ResponseEntity<>("Không tìm thấy role 'user'", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        person.setRole(userRole);
        personRepository.save(person);
        return new ResponseEntity<>("Đăng ký thành công", HttpStatus.CREATED);
    }


    @PostMapping("/loginacc")
    public ResponseEntity login(@RequestBody Person person) {
        Person person1 = personRepository.findByUsnAndPass(person.getUsn(), person.getPass());
        if (person1 == null) {
            return new ResponseEntity<>("Tài khoản hoặc mật khẩu sai", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(person1, HttpStatus.OK);
    }

    @PutMapping("/updateprofile")
    public ResponseEntity<String> updateProfile(@RequestBody Person person) {
        Person existingPerson = personRepository.findById(person.getId());
        if (existingPerson == null) {
            return new ResponseEntity<>("Người dùng không tồn tại", HttpStatus.NOT_FOUND);
        }
        existingPerson.setUsn(person.getUsn());
        existingPerson.setPass(person.getPass());
        existingPerson.setPhone(person.getPhone());
        personRepository.save(existingPerson);
        return new ResponseEntity<>("Cập nhật thành công", HttpStatus.OK);
    }
}
