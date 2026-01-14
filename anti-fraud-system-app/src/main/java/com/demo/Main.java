package com.demo;

import com.demo.repo.UserAccountRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Component
    class Runner implements ApplicationRunner {

        private final UserAccountRepository repository;

        // constructor injection for TreadmillRepository
        Runner(UserAccountRepository treadmillRepository) {
            this.repository = treadmillRepository;
        }

        private void doWeHaveSomethingInDb() {
            long count = repository.count();
            if (count > 0) {
                System.out.printf("Db has %d account(s)%n", count);
            } else {
                System.out.println("Db is empty");
            }
        }

        @Override
        public void run(ApplicationArguments args) {
            // here's you can print anything to the console
            doWeHaveSomethingInDb();

            // repository.save(new UserAccount("user1", "password"));

            doWeHaveSomethingInDb();
        }

    }
}