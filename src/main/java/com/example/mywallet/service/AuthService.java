package com.example.mywallet.service;


import com.example.mywallet.dto.request.UserRegisterDto;
import com.example.mywallet.dto.request.UsernamePasswordRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.dto.response.UserResponseDto;
import com.example.mywallet.entity.BalanceEntity;
import com.example.mywallet.entity.CurrencyEntity;
import com.example.mywallet.entity.UserEntity;
import com.example.mywallet.entity.enums.RoleEnum;
import com.example.mywallet.exception.RecordNotFound;
import com.example.mywallet.repository.BalanceRepository;
import com.example.mywallet.repository.CurrencyRepository;
import com.example.mywallet.repository.UserRepository;
import com.example.mywallet.utils.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final BalanceRepository balanceRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public ApiResponse register(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getRetryPassword())) {
            return new ApiResponse(
                    HttpStatus.CONFLICT.value(),
                    "Retry password incorrect",
                    null
            );
        }
        BalanceEntity balanceEntity = new BalanceEntity();
        if (userRegisterDto.getCurrencyId() > 0) {
            Optional<CurrencyEntity> byId = currencyRepository.findById(userRegisterDto.getCurrencyId());
            balanceEntity.setCurrency(byId.orElseThrow(() ->
                    new RecordNotFound("Currency not found")));
        }
        BalanceEntity myBalance = balanceRepository.save(balanceEntity);
        UserEntity build = UserEntity.builder()
                .name(userRegisterDto.getName())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .perRoleEnumList(List.of(RoleEnum.USER))
                .username(userRegisterDto.getUsername())
                .myBalance(myBalance)
                .build();
        UserEntity save = userRepository.save(build);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                UserResponseDto.from(save)
        );
    }

    public ApiResponse login(UsernamePasswordRequestDto usernamePasswordRequestDto) throws UsernameNotFoundException, JsonProcessingException {
        UserEntity userEntity = userRepository.findByUsername(usernamePasswordRequestDto.getUsername()).orElseThrow(() ->
                new RecordNotFound("username or password is incorrect"));
        if (!passwordEncoder.matches(usernamePasswordRequestDto.getPassword(), userEntity.getPassword())) {
            throw new RecordNotFound("username or password is incorrect");
        }
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Map.of(
                        "access_token", jwtUtils.generateAccessToken(userEntity),
                        "refresh_token", jwtUtils.generateRefreshToken(userEntity)
                )
        );
    }

    public ApiResponse getAccessToken(String refreshToken) throws JsonProcessingException {
        Claims claims = jwtUtils.isValidRefreshToken(refreshToken);
        if (claims != null) {
            String username = claims.getSubject();
            UserEntity userEntity = userRepository.findByUsername(username).orElse(null);
            if (userEntity != null) {
                return new ApiResponse(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        Map.of(
                                "access_token", jwtUtils.generateAccessToken(userEntity)
                        )
                );
            }
        }
        return null;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        final UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
//                new RecordNotFound("User not found"));
//        boolean accountNonExpired = user.isAccountNonExpired();
//        if (accountNonExpired) {
//            return User.withUsername(user.getUsername())
//                    .password(user.getPassword())
//                    .disabled(user.isEnabled())
//                    .authorities(user.getAuthorities()).build();
//        }
//        return null;
//    }
}
