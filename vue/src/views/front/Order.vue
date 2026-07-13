<template>
  <div class="front-container">
    <div class="card" style="padding: 20px">
      <div v-if="data.tableData.length === 0" style="text-align: center; padding: 50px; color: #999">
        暂无订单
      </div>
      <div v-else>
        <div v-for="order in data.tableData" :key="order.id" style="border: 1px solid #ddd; border-radius: 8px; margin-bottom: 15px; overflow: hidden">
          <div style="background-color: #f5f7fa; padding: 10px 20px; display: flex; justify-content: space-between; align-items: center">
            <div style="display: flex; grid-gap: 20px; color: #666; font-size: 14px">
              <span>订单号：{{ order.orderNo }}</span>
              <span>{{ order.time }}</span>
            </div>
            <div>
              <el-tag v-if="order.status === '已发货'" type="warning">{{ order.status }}</el-tag>
              <el-tag v-else-if="order.status === '已完成'" type="success">{{ order.status }}</el-tag>
              <el-tag v-else-if="order.status === '已付款'" type="success">{{ order.status }}</el-tag>
              <el-tag v-else-if="order.status === '已取消'" type="info">{{ order.status }}</el-tag>
              <el-tag v-else>{{ order.status }}</el-tag>
            </div>
          </div>
          <div style="padding: 15px 20px">
            <div v-for="item in order.items" :key="item.id" style="display: flex; align-items: center; padding: 10px 0; border-bottom: 1px solid #f0f0f0">
              <img :src="item.goodsImg" style="width: 60px; height: 60px; object-fit: cover; border-radius: 4px">
              <div style="flex: 1; margin-left: 15px">
                <div style="font-size: 14px; font-weight: 500">{{ item.goodsName }}</div>
              </div>
              <div style="color: #999; font-size: 13px; width: 180px; text-align: center; white-space: nowrap">¥{{ item.goodsPrice }} &times; {{ item.num }}</div>
              <div style="color: red; font-weight: bold; width: 130px; text-align: right">¥{{ (item.goodsPrice * item.num).toFixed(2) }}</div>
            </div>
          </div>
          <div style="background-color: #fafafa; padding: 10px 20px; display: flex; justify-content: flex-end; align-items: center; grid-gap: 15px">
            <span style="font-size: 14px">共 <b>{{ order.items ? order.items.reduce(function(s,i) { return s + i.num }, 0) : 0 }}</b> 件商品</span>
            <span style="font-size: 14px">合计：<b style="color: red; font-size: 18px">{{order.total }}</b>￥</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import request from "@/utils/request";
import {reactive} from "vue";

const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  tableData: [],
})

const load = () => {
  request.get('/order/selectAll', {
    params: {
      userId: data.user.id
    }
  }).then(res => {
    data.tableData = res.data
  })
}
load()
</script>