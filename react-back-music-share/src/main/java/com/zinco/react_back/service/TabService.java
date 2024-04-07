package com.zinco.react_back.service;

import com.zinco.react_back.entity.Music;
import com.zinco.react_back.entity.Tab;
import com.zinco.react_back.repository.TabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;

@Service
public class TabService {

    @Autowired
    private TabRepository tabRepository;
    @Autowired
    private EntityManager entityManager;

    public Tab getTab(int tabId) {
        return entityManager.find(Tab.class, tabId);
    }

    public Tab getTab(String uri, String key) {
        if (uri == null || key == null) {
            return null;
        }
        uri = uri.substring(1);
        return tabRepository.findByTabPageAndTabDetail(uri, Integer.parseInt(key));
    }
}
