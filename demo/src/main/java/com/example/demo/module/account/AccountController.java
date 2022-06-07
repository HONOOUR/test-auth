package com.example.demo.module.account;

import com.example.demo.infra.config.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AccountController {

    private final JwtUtils jwtUtils;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginForm) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword());
        SecurityContextHolder.getContext().setAuthentication(token);
        String jwt = jwtUtils.generateJwtToken(token);
        UserAccount userAccount = (UserAccount) token.getPrincipal();
        List<String> roles = userAccount.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        JwtForm jwtForm = new JwtForm();
        jwtForm.setJwt(jwt);
        jwtForm.setUsername(userAccount.getUsername());
        return ResponseEntity.ok(jwtForm);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(SignUpForm signUpForm) {
        if (accountRepository.existsByUsername(signUpForm.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        accountService.processNewAccount(signUpForm);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
