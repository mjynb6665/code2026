package com.example.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StatMapper {

    List<Map<String, Object>> dailyTrend(@Param("days") int days);

    List<Map<String, Object>> monthlyTrend(@Param("months") int months);

    List<Map<String, Object>> categorySales();

    List<Map<String, Object>> topGoods(@Param("limit") int limit);

    Map<String, Object> overview();

    Long userCount();
}
