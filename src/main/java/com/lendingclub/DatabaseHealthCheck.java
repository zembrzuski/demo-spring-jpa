package com.lendingclub;

import com.lendingclub.repository.UserRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthCheck implements HealthIndicator {

    private final UserRepository userRepository;

    public DatabaseHealthCheck(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Health health() {
        try {
            userRepository.count();
            return new Health.Builder().up().build();
        } catch (RuntimeException e) {
            return new Health.Builder().down().build();
        }
    }

}
