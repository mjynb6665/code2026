<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <el-input style="width: 200px; margin-right: 10px" v-model="data.orderNo" placeholder="请输入订单号查询" :prefix-icon="Search"></el-input>
      <el-select v-model="data.status" placeholder="请选择状态" style="width: 150px; margin-right: 10px" clearable>
        <el-option label="已付款" value="已付款"></el-option>
        <el-option label="已发货" value="已发货"></el-option>
        <el-option label="已完成" value="已完成"></el-option>
        <el-option label="已取消" value="已取消"></el-option>
      </el-select>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button @click="reset">重置</el-button>
    </div>

    <div class="card">
      <el-table :data="data.tableData" stripe>
        <el-table-column label="订单编号" prop="orderNo" width="180"></el-table-column>
        <el-table-column label="用户" prop="userName"></el-table-column>
        <el-table-column label="总金额" width="120">
          <template #default="scope">
            <b style="color: red">￥{{ scope.row.total }}</b>
          </template>
        </el-table-column>
        <el-table-column label="订单状态" width="120">
          <template #default="scope">
            <el-select v-model="scope.row.status" @change="handleStatusChange(scope.row)" style="width: 100px">
              <el-option label="已付款" value="已付款"></el-option>
              <el-option label="已发货" value="已发货"></el-option>
              <el-option label="已完成" value="已完成"></el-option>
              <el-option label="已取消" value="已取消"></el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="下单时间" prop="time" width="180"></el-table-column>
        <el-table-column label="商品明细" width="320">
          <template #default="scope">
            <div style="display: flex; flex-wrap: wrap; grid-gap: 5px">
              <el-tag v-for="item in scope.row.items" :key="item.id" type="info" style="margin: 2px">
                {{ item.goodsName }} x{{ item.num }}
              </el-tag>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import request from "@/utils/request";
import {reactive} from "vue";
import {ElMessage} from "element-plus";
import {Search} from "@element-plus/icons-vue";

const data = reactive({
  orderNo: null,
  status: null,
  tableData: [],
})

const load = () => {
  request.get('/order/selectAll', {
    params: {
      orderNo: data.orderNo,
      status: data.status
    }
  }).then(res => {
    data.tableData = res.data
  })
}
load()

const handleStatusChange = (row) => {
  request.put('/order/updateStatus', {
    id: row.id,
    status: row.status
  }).then(res => {
    if (res.code === '200') {
      ElMessage.success('状态更新成功')
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const reset = () => {
  data.orderNo = null
  data.status = null
  load()
}
</script>