package com.fedag.CSR.service;

import com.fedag.CSR.model.Support;

import java.util.List;
import java.util.Map;

public interface SupportService {

    List<Support>findAll();

    List<Support>findByEmail(String email);

    Support findById(Long id);

    void deleteById(Long id);

    Map<String, String> create(Support support);
}
