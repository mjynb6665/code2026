<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <el-input style="width: 200px; margin-right: 10px" v-model="data.goodsId" placeholder="请输入商品ID" :prefix-icon="Search"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button @click="reset">重置</el-button>
    </div>

    <div class="card">
      <el-table :data="data.tableData" stripe>
        <el-table-column label="商品ID" prop="goodsId" width="100"></el-table-column>
        <el-table-column label="用户" width="150">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <img :src="scope.row.userAvatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" style="width: 30px; height: 30px; border-radius: 50%; margin-right: 8px">
              <span>{{ scope.row.userName }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="评分" width="150">
          <template #default="scope">
            <el-rate v-model="scope.row.rating" disabled />
          </template>
        </el-table-column>
        <el-table-column label="评论内容" prop="content" min-width="250" show-overflow-tooltip></el-table-column>
        <el-table-column label="评论时间" prop="time" width="180"></el-table-column>
        <el-table-column label="操作" width="100" align="center">
          <template #default="scope">
            <el-button type="danger" size="small" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import request from "@/utils/request";
import {reactive} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {Search} from "@element-plus/icons-vue";

const data = reactive({
  goodsId: null,
  tableData: [],
})

const load = () => {
  request.get('/comment/selectAll', {
    params: { goodsId: data.goodsId }
  }).then(res => {
    data.tableData = res.data
  })
}
load()

const handleDelete = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复，确定删除吗?', '删除确认', { type: 'warning' }).then(() => {
    request.delete('/comment/delete/' + id).then(res => {
      if (res.code === '200') {
        ElMessage.success('删除成功')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(() => {})
}

const reset = () => {
  data.goodsId = null
  load()
}
</script>