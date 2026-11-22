package com.kinox.kinox.security;

import com.kinox.kinox.repository.AdministradorRepository;
import com.kinox.kinox.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final AdministradorRepository administradorRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return administradorRepository.findByLogin(username)
                .map(admin -> new AuthUserDetails(
                        admin.getId(),
                        admin.getLogin(),
                        admin.getSenha(),
                        Role.ADMIN
                ))
                .or(() -> clienteRepository.findByEmail(username)
                        .map(cliente -> new AuthUserDetails(
                                cliente.getId(),
                                cliente.getEmail(),
                                cliente.getSenha(),
                                Role.CLIENTE
                        )))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário ou senha inválidos."));
    }

}
