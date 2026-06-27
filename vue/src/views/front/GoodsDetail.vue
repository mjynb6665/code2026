
<template>

  <div class="front-container" style="width:  50%">
    <!--第一行-->
    <div class="card" style="padding: 20px; display: flex; grid-gap: 20px; margin-bottom: 10px">
      <img :src="data.goods.img" alt="" style="width: 300px; height: 300px">
      <div style="flex: 1">
        <div style="display: flex; align-items: flex-start; grid-gap: 20px; margin-bottom: 10px">
          <div style="font-size: 22px; font-weight: bold; line-height: 30px">
            <el-tag style="margin-right: 5px; float: left; background-color: red; color: white" type="danger" v-if="data.goods.recommend === '是'">推荐</el-tag>
            {{ data.goods.name }}</div>
          <div style="width: 20%; cursor: pointer; color: #666">
            <el-icon style="position: relative; top: 3px" size="18"><Star /></el-icon>收藏
          </div>
        </div>
          <!--第二行-->
        <div style="margin-bottom:20px; margin-top:20px ">
          <span style="color: red; font-size: 18px">￥</span><b style="color: red; font-size: 25px;">{{data.goods.price}}</b>
          <span style="color: #666; margin-left: 20px">累计销量{{data.goods.saleCount}}</span>
          <span style="color: #666; margin-left: 20px">剩余{{data.goods.store}}件</span>
        </div>
        <div style="margin-bottom: 20px ;padding: 10px; background-color: #e8e4e4; border-radius: 5px; line-height: 25px; text-align: justify">{{data.goods.description}}</div>
        <div style="margin-bottom: 20px">
          <el-input-number :min ="1" v-model="data.num" style="width: 150px; height: 40px"></el-input-number>
          <el-button type="danger" style="height: 40px; margin-left: 20px">加入购物车</el-button>
          <el-button type="danger" style="height: 40px; margin-left: 20px">立即购买</el-button>
        </div>
          <div style="mairgin-top: 20px; color: #666">校园小卖部售卖的商品，由小卖部客服人员会及时处理，请放心购买</div>
      </div>
    </div>
    <div class="card" style="padding: 20px; margin-bottom: 50px">
      <div style="font-size: 20px; padding-bottom: 10px; border-bottom: 1px solid #ddd">
        <span @click="changeTab('商品详情')" :class="{'current-active': data.current === '商品详情'}" style="cursor: pointer">商品详情</span>
        <span @click="changeTab('商品评论')" :class="{'current-active': data.current === '商品评论'}" style="margin-left: 20px; cursor: pointer">商品评论</span>
      </div>
      <div style="padding: 10px" v-html="data.goods.content" v-if="data.current === '商品详情'"></div>
      <div v-if="data.current === '商品评论'" style="min-height: 500px">
        <div v-if="data.commentList.length === 0" style="padding : 50px; text-align: center">暂无评论</div>
        <div v-if="data.commentList.length > 0" style="padding : 20px; text-align: center">
          <!--          评论-->
        </div>


      </div>

    </div>
  </div>


</template>



<script setup>
import {reactive} from "vue";
import router from "../../router";
import request from "../../utils/request";
const data = reactive({
  goods: {},
  num:1,
  current: '商品详情',
  id: router.currentRoute.value.query.id,

  commentList: []
})

const load = () =>{
  request.get('/goods/selectById/' + data.id).then(res => {
    data.goods = res.data
  })
}
load();

const changeTab = (current) => {
  data.current = current
}
</script>



<style scoped>
.current-active {
  color: red;
  border-bottom: 2px solid red;
  padding-bottom: 10px
}
</style>