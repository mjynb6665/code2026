<template>
  <div>
    <!-- KPI -->
    <el-row :gutter="16" style="margin-bottom: 20px">
      <el-col :span="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-label">总销售额</div>
          <div class="stat-value">¥{{ fmtNum(overview.totalSales) }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-label">总订单数</div>
          <div class="stat-value">{{ fmtNum(overview.totalOrders) }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-label">客单价</div>
          <div class="stat-value">¥{{ fmtNum(overview.avgOrder) }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-label">用户数</div>
          <div class="stat-value">{{ fmtNum(overview.userCount) }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16" style="margin-bottom: 16px">
      <!-- 销售趋势 -->
      <el-col :span="16">
        <el-card shadow="never">
          <template #header>
            <div class="chart-header">
              <span>销售趋势</span>
              <el-radio-group v-model="trendType" size="small" @change="loadTrend">
                <el-radio-button value="day">近7天</el-radio-button>
                <el-radio-button value="month">近12月</el-radio-button>
              </el-radio-group>
            </div>
          </template>
          <div ref="trendChart" style="height: 320px"></div>
        </el-card>
      </el-col>

      <!-- 分类占比 -->
      <el-col :span="8">
        <el-card shadow="never">
          <template #header><span>分类销售分布</span></template>
          <div ref="categoryChart" style="height: 320px"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 商品排行 -->
    <el-card shadow="never">
      <template #header><span>商品销量 TOP 10</span></template>
      <el-table :data="topGoods" style="width: 100%" stripe>
        <el-table-column type="index" label="#" width="60" />
        <el-table-column label="商品" width="240">
          <template v-slot="{ row }">
            <div style="display: flex; align-items: center; gap: 8px">
              <img :src="row.goodsImg" style="width: 36px; height: 36px; border-radius: 4px; object-fit: cover" />
              <span style="font-size: 13px">{{ row.goodsName }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="saleCount" label="销量" width="120" align="center" sortable />
        <el-table-column label="销售额" width="150" align="right" sortable sort-by="totalSales">
          <template v-slot="{ row }">
            <span style="color: red; font-weight: bold">¥{{ fmtNum(row.totalSales) }}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, nextTick } from "vue";
import request from "@/utils/request";
import * as echarts from "echarts";

const overview = reactive({ totalSales: 0, totalOrders: 0, avgOrder: 0, userCount: 0 });
const trendType = ref("day");
const topGoods = ref([]);

const trendChart = ref(null);
const categoryChart = ref(null);
let trendInstance = null;
let categoryInstance = null;

const fmtNum = (v) => {
  if (v == null) return "0";
  const n = Number(v);
  if (isNaN(n)) return v;
  return n.toLocaleString("zh-CN", { minimumFractionDigits: 0, maximumFractionDigits: 2 });
};

const loadOverview = async () => {
  const res = await request.get("/stat/overview");
  Object.assign(overview, res.data);
};

const loadTrend = async () => {
  const count = trendType.value === "day" ? 7 : 12;
  const res = await request.get("/stat/trend", { params: { type: trendType.value, count } });
  const list = res.data || [];
  const xData = list.map((i) => i.dateKey);
  const salesData = list.map((i) => Number(i.sales));
  const orderData = list.map((i) => Number(i.orderCount));

  const option = {
    tooltip: { trigger: "axis" },
    legend: { data: ["销售额", "订单数"] },
    grid: { left: 60, right: 30, top: 30, bottom: 30 },
    xAxis: { type: "category", data: xData, axisLabel: { rotate: xData.length > 7 ? 45 : 0 } },
    yAxis: [
      { type: "value", name: "销售额", axisLabel: { formatter: (v) => "¥" + (v / 10000).toFixed(1) + "w" } },
      { type: "value", name: "订单数" },
    ],
    series: [
      { name: "销售额", type: "line", data: salesData, smooth: true, symbol: "circle", symbolSize: 6, lineStyle: { width: 2.5 }, itemStyle: { color: "#fd6a6a" } },
      { name: "订单数", type: "bar", yAxisIndex: 1, data: orderData, barWidth: 18, itemStyle: { color: "#96c3eb" } },
    ],
  };

  if (!trendInstance) {
    await nextTick();
    trendInstance = echarts.init(trendChart.value);
  }
  trendInstance.setOption(option, true);
};

const loadCategory = async () => {
  const res = await request.get("/stat/category");
  const list = res.data || [];
  const pieData = list.map((i) => ({ name: i.name, value: Number(i.sales) }));

  const option = {
    tooltip: { trigger: "item", formatter: "{b}: ¥{c}" },
    legend: { orient: "vertical", right: 10, top: 20, textStyle: { fontSize: 12 } },
    series: [
      {
        type: "pie",
        radius: ["45%", "75%"],
        center: ["40%", "52%"],
        data: pieData,
        label: { show: false },
        emphasis: { label: { show: true, fontSize: 14 } },
        itemStyle: { borderRadius: 4, borderColor: "#fff", borderWidth: 2 },
      },
    ],
  };

  if (!categoryInstance) {
    await nextTick();
    categoryInstance = echarts.init(categoryChart.value);
  }
  categoryInstance.setOption(option, true);
};

const loadTopGoods = async () => {
  const res = await request.get("/stat/topGoods", { params: { limit: 10 } });
  topGoods.value = res.data || [];
};

onMounted(async () => {
  await loadOverview();
  await Promise.all([loadTrend(), loadCategory(), loadTopGoods()]);
});
</script>

<style scoped>
.stat-card .stat-label {
  font-size: 13px;
  color: #999;
  margin-bottom: 6px;
}
.stat-card .stat-value {
  font-size: 24px;
  font-weight: 700;
  color: #333;
}
.chart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
