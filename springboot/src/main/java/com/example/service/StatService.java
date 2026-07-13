package com.example.service;

import com.example.mapper.StatMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatService {

    @Resource
    private StatMapper statMapper;

    public Map<String, Object> overview() {
        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, Object> sales = statMapper.overview();
        result.putAll(sales);
        result.put("userCount", statMapper.userCount());
        return result;
    }

    public List<Map<String, Object>> dailyTrend(int days) {
        return statMapper.dailyTrend(days);
    }

    public List<Map<String, Object>> monthlyTrend(int months) {
        return statMapper.monthlyTrend(months);
    }

    public List<Map<String, Object>> categorySales() {
        return statMapper.categorySales();
    }

    public List<Map<String, Object>> topGoods(int limit) {
        return statMapper.topGoods(limit);
    }
}
