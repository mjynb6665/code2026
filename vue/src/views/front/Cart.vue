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
            <b style="color: red">$ {{scope.row.goodsPrice}} </b>
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
        <div style="margin-top: 20px"><el-button type="danger">立即下单</el-button> </div>
      </div>

    </div>


  </div>


    <el-dialog title="下单信息" width="30%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px" style="padding-right: 30px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.formVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">保 存</el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import request from "@/utils/request";
import {reactive, ref} from "vue";
import {ElMessageBox, ElMessage} from "element-plus";

// 文件上传的接口地址
const uploadUrl = import.meta.env.VITE_BASE_URL + '/files/upload'
const formRef = ref()
const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  total: 0,
  formVisible: false,
  form: {},
  tableData: [],
  selectedRows:[],
  rules:{
    name: [
      {required: true, message: '请输入名称', trigger: 'blur'},
    ]
  }
})

const calTotal = () =>{
  data.total = 0;
  data.selectedRows.forEach(item => {
    data.total = item.goodsPrice  *  item.num;
  })
  if(data.total > 0){
    data.total = data.total.toFixed(2);
  }
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
// 新增
const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}

// 编辑
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

// 新增保存
const add = () => {
  request.post('/cart/add', data.form).then(res => {
    if (res.code === '200') {
      load()
      ElMessage.success('操作成功')
      data.formVisible = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 编辑保存
const update = () => {
  request.put('/cart/update', data.form).then(res => {
    if (res.code === '200') {
      load()
      ElMessage.success('操作成功')
      data.formVisible = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 弹窗保存
const save = () => {
  formRef.value.validate(valid =>{
    if(valid){
      data.form.id ? update() : add()
    }
  })
  // data.form有id就是更新，没有就是新增
}

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


// 重置
const reset = () => {
  data.name = null
  load()
}


</script>