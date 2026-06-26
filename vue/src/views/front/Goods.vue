<template>

  <div class="front-container">

    <div style="display: flex; grid-gap: 20px; margin-bottom: 20px">
      <div style="display: flex; flex: 1 ;grid-gap: 20px; align-items: center">

        <div :class="{'active': null === data.categoryId}"
             @click="loadByCategory(null)"
             style="cursor: pointer; padding: 10px">
              全部
        </div>

        <div :class="{'active': item.id === data.categoryId}"
             @click="loadByCategory(item.id)"
             v-for="item in data.categoryList"
             :key = "item.id"
             style="cursor: pointer; padding: 10px">
          {{item.name}}
        </div>
      </div>
      <div>
        <el-input clearable @click="load" v-model="data.name" placeholder="请输入商品名称" style="height: 40px; width: 300px"></el-input>
        <el-button type="primary" @click="load" style="height: 40px; margin-left: 5px">搜索</el-button>
      </div>
    </div>


    <div v-if="data.total > 0">
    <el-row :gutter="20">
      <el-col :span="6" v-for="item in data.tableData" :key = "item.id">
        <div @click="router.push('/front/goodsDetail?id=' + item.id)" class="card goods-item" style="width: 100%; border-radius: 5px ; height: 350px; margin-bottom: 20px">
          <img  :src="item.img" alt="" style="width: 100%; height: 260px; border-radius: 5px 5px 0 0" >
          <div style="padding: 5px">
            <div class="line1" style="font-size: 18px; margin-bottom: 5px">{{item.name}}</div>
            <div>
              <span style="color: red">￥</span><b style="color: red; font-weight: bold; font-size: 20px">{{item.price}}</b>
              <span style="margin-left: 10px; color: #666">销量：{{item.saleCount}}件</span>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
    <div v-else style="padding: 50px; text-align: center;color: #666">暂无数据</div>



    <div style="margin-top: 20px">
      <el-pagination style="background-color: white; width: fit-content; padding: 5px 10px; border-radius: 5px"
                     @current-change="load"
                     v-model:page-size="data.pageSize"
                     v-model:current-page="data.pageNum"
                     :total="data.total"/>
    </div>

  </div>

</template>
<script setup>
import {reactive} from "vue";
import router from "../../router";
import request from "../../utils/request";
const data = reactive({
    name: router.currentRoute.value.query.name,
    tableData:[],
    pageNum: 1,
    pageSize: 8,
    total: 0,
    categoryId: null,
    categoryList:[],
  }
)

const load = () => {
  request.get('/goods/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
      categoryId: data.categoryId
    }
  }).then(res => {
    data.tableData = res.data?.list
    data.total = res.data?.total
  })
}
load()

const loadCategory = () => {
  request.get('/category/selectAll').then(res => {
   data.categoryList = res.data
  })
}
loadCategory()

const clearPathParam = () => {
  let url = location.href
  url = url.replace(/(\?|#)[^'"]*/, '');           //去除参数
  window.history.pushState({},0, url);
}
clearPathParam()

const loadByCategory = (categoryId) => {
  data.categoryId = categoryId
  load()
}

</script>


<style scoped>
.active {
  font-weight: bold;
  color: #0c9c7a;
  border-bottom: 2px solid #0c9c7a;
}
.goods-item {
  cursor: pointer;
  transition: all 0.5s;
}
.goods-item:hover {
  transform: scale(1.05);
}
</style>


