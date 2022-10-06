package com.pawasa;

import com.pawasa.model.User;
import com.pawasa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class PawasaApplication {
    public static void main(String[] args) {
        SpringApplication.run(PawasaApplication.class, args);
    }

}
