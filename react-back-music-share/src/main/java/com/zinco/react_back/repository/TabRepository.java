package com.zinco.react_back.repository;

import com.zinco.react_back.entity.Tab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TabRepository extends JpaRepository<Tab, Integer> {
    Tab findByTabPageAndTabDetail(String musicReco, Integer integer);
}
