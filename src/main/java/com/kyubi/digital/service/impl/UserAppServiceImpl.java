package com.kyubi.digital.service.impl;

import com.kyubi.digital.service.UserAppService;
import com.kyubi.digital.domain.UserApp;
import com.kyubi.digital.repository.UserAppRepository;
import com.kyubi.digital.service.dto.UserAppDTO;
import com.kyubi.digital.service.mapper.UserAppMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserApp}.
 */
@Service
@Transactional
public class UserAppServiceImpl implements UserAppService {

    private final Logger log = LoggerFactory.getLogger(UserAppServiceImpl.class);

    private final UserAppRepository userAppRepository;

    private final UserAppMapper userAppMapper;

    public UserAppServiceImpl(UserAppRepository userAppRepository, UserAppMapper userAppMapper) {
        this.userAppRepository = userAppRepository;
        this.userAppMapper = userAppMapper;
    }

    @Override
    public UserAppDTO save(UserAppDTO userAppDTO) {
        log.debug("Request to save UserApp : {}", userAppDTO);
        UserApp userApp = userAppMapper.toEntity(userAppDTO);
        userApp = userAppRepository.save(userApp);
        return userAppMapper.toDto(userApp);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAppDTO> findAll() {
        log.debug("Request to get all UserApps");
        return userAppRepository.findAll().stream()
            .map(userAppMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserAppDTO> findOne(Long id) {
        log.debug("Request to get UserApp : {}", id);
        return userAppRepository.findById(id)
            .map(userAppMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserApp : {}", id);
        userAppRepository.deleteById(id);
    }
}
