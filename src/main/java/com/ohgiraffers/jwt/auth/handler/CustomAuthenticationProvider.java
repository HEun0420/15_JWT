package com.ohgiraffers.jwt.auth.handler;

import com.ohgiraffers.jwt.auth.service.CustomUserDetailService;
import com.ohgiraffers.jwt.auth.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailService detailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 로그인 요청 정보를 가지고 있는 token
        UsernamePasswordAuthenticationToken loginToken = (UsernamePasswordAuthenticationToken) authentication;

        // 사용자가 입력한 ID
        String memberId = loginToken.getName();
        // 사용자가 입력한 password
        String password = (String) loginToken.getCredentials();

        // 사용자가 입력한 ID로 찾아온 CustomUserDetail
        // CustomUserDetailService의 loudUserByUsername 메소드로 찾아올 수 있다.
        CustomUserDetails member = (CustomUserDetails) detailsService.loadUserByUsername(memberId);

        // passwordEncoder의 matches 메소드로
        // 사용자가 입력한 password와 db에서 찾아온 password가 일치하는지 확인한다.(복호화진행)
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new BadCredentialsException(password + "는 비밀번호가 아닙니다.");
        }
        return new UsernamePasswordAuthenticationToken(member, password, member.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
