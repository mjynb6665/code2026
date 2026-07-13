<template>
  <div class="front-container">
  <div class="card" style="padding: 20px">
    <div>
      <el-table :data="data.tableData" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection"  width="55" />

        <el-table-column label="商品名称" prop="goodsName"></el-table-column>
        <el-table-column label="商品图片" >
          <template #default="scope">
            <img style="width: 50px; height: 50px; display: block" :src="scope.row.goodsImg">
          </template>
        </el-table-column>
        <el-table-column label="商品单价" >
          <template #default="scope">
            <b style="color: red">￥ {{scope.row.goodsPrice}} </b>
          </template>
        </el-table-column>
        <el-table-column label="商品数量" >
          <template #default="scope">
            <el-input-number @change="calTotal" v-model="scope.row.num" :min="1" label="label" style="width: 150px"></el-input-number>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center" width="160">
          <template #default="scope">
            <el-button type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="text-align: right; margin-top: 20px;font-size: 20px">总价格: <b style="color: red; display: inline-block; min-width: 60px; text-align: left">{{data.total}} 元</b>
        <div style="margin-top: 20px"><el-button type="danger" @click="handleCheckout">立即下单</el-button> </div>
      </div>

    </div>


  </div>

  </div>
</template>

<script setup>
import request from "@/utils/request";
import {reactive, ref} from "vue";
import {ElMessageBox, ElMessage} from "element-plus";

const formRef = ref()
const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  total: 0,
  form: {},
  tableData: [],
  selectedRows:[],
})

// 计算选中商品的总价
const calTotal = () =>{
  data.total = 0;
  let sum = 0;
  data.selectedRows.forEach(item => {
    sum += item.goodsPrice * item.num;
  })
  data.total = sum.toFixed(2);
}

// 分页查询
const load = () => {
  request.get('/cart/selectAll', {
    params: {
      userId: data.user.id
    }
  }).then(res => {
    data.tableData = res.data
  })
}
load()

// 删除
const handleDelete = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/cart/delete/' + id).then(res => {
      if (res.code === '200') {
        load()
        ElMessage.success('操作成功')
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {})
}

const handleSelectionChange = (rows) => {
  data.selectedRows = rows
  calTotal()
}

// 立即下单
const handleCheckout = () => {
  if (data.selectedRows.length === 0) {
    ElMessage.warning('请先选择要购买的商品')
    return
  }
  const cartIds = data.selectedRows.map(row => row.id)
  request.post('/order/createFromCart', {
    userId: data.user.id,
    cartIds: cartIds
  }).then(res => {
    if (res.code === '200') {
      ElMessage.success('下单成功')
      load()
      data.selectedRows = []
      data.total = 0
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 重置
const reset = () => {
  data.name = null
  load()
}

</script>