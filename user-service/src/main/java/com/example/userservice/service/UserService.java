package com.example.userservice.service;

import com.example.userservice.po.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Mono<User> getUser(Long uid) {
        return userRepository.findById(uid);
    }

    public Mono<User> getUser(String number) {
        return userRepository.findByNumber(number);
    }
}
