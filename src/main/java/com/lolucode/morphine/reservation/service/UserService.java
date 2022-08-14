package com.lolucode.morphine.reservation.service;

import com.lolucode.morphine.appuser.AppUser;
import com.lolucode.morphine.appuser.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final AppUserRepository appUserRepository;

    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    public AppUser get(final Long id) {
        return appUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    public void update(final Long id, final AppUser appUser) {
        final AppUser existingUser = appUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        appUserRepository.save(appUser);

    }

    public void delete(final Long id) {
        appUserRepository.deleteById(id);

    }


}
