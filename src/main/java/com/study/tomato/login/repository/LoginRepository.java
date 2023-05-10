package com.study.tomato.login.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LoginRepository {
    List<Map<String, Object>> sessionLogin(Map<String, Object> param);
}
