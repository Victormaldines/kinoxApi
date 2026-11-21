package com.kinox.kinox.service;

import com.kinox.kinox.repository.FilmeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilmeService {

    private final FilmeRepository filmesRepository;

}
