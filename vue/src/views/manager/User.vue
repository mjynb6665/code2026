

<template>
  <div class="card" style="margin-bottom: 5px; display: flex; align-items: center">
    <el-input style="width: 250px" v-model="data.name" class="responsive-input" placeholder="输入名称查询" :prefix-icon="Search"/>
    <el-button @click="load" style="margin: 0 5px" type="primary">查询</el-button>
    <el-button @click="reset" style="margin: 0 5px" type="info">重置</el-button>
  </div>

  <div class="card">
    <div>
      <el-button type="info" @click="handleAdd">新增</el-button>
      <el-table :data="data.tableData" stripe style="width: 100%">
        <el-table-column prop="username" label="账号" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="avatar" label="头像">
          <template #default="scope">
            <el-image v-if="scope.row.avatar"
                    :src="scope.row.avatar"
                    :preview-src-list="[scope.row.avatar]"
                      preview-teleported 
                    style="width: 50px; height: 50px; border-radius: 50% ;display: block ">
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="role" label="角色" />


        <el-table-column prop="account" label="账户余额" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
          <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>

          <el-button type="danger" @click="del(scope.row.id)">删除</el-button>

          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>



  <div class="card">
    <el-pagination v-model:current-page="data.pageNum"
                   v-model:page-size = "data.pageSize"
                   @current-change="load" background layout="total ,prev, pager, next" :total=data.total el-pagination/>
  </div>

  <el-dialog v-model="data.formVisible" title="用户信息" width="30%" destroy-on-close>
    <el-form ref="formRef" :model="data.form" :rules="data.rules" label-width="120px" style="padding-top: 30px">
      <el-form-item label="账号" prop="username">
        <el-input :disabled="data.form.id !== undefined" v-model="data.form.username" placeholder="请输入账号"  autocomplete="off" />
      </el-form-item>

      <el-form-item label="姓名" prop="name">
        <el-input v-model="data.form.name" placeholder="请输入姓名">
        </el-input>
      </el-form-item>

      <el-form-item label="头像" prop="avatar">
        <el-upload
            class="upload-demo"
            :action="baseUrl + '/files/upload'"
            list-type="picture"
            :on-success="handleFileUpload"
        >
          <el-button size="small" type="primary">点击上传</el-button>
          <template #tip>
            <div class="el-upload__tip">只能上传 jpg/png 文件，且不超过 500kb</div>
          </template>
        </el-upload>

      </el-form-item>

    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="data.formVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </div>
    </template>


  </el-dialog>


</template>

<script setup>
import {reactive} from "vue";
import {ref} from "vue";
import {Search} from "@element-plus/icons-vue";
import request from "../../utils/request";
import {ElMessage, ElMessageBox} from "element-plus";

const formRef = ref();


const baseUrl = import.meta.env.VITE_BASE_URL;

const data = ref({
  name: null,
  tableData:[],
  total: 0,
  pageNum: 1,
  pageSize: 10,
  formVisible:false,
  form:{},
  rules:{
    username: [
      {required: true, message: '请输入账号', trigger: 'blur'}
    ],
    name: [  // ← 补上这个完整数组
      {required: true, message: '请输入姓名', trigger: 'blur'}
    ],
  }
})

const handleFileUpload = (response) => {
  console.log(response);
  data.value.form.avatar = response.data
}


const load = () => {
  request.get('user/selectPage' , {
    params:{
      pageNum: data.value.pageNum,
      pageSize: data.value.pageSize,
      name: data.value.name
    }
  }).then(res => {
    if (res.code === '200'){
      data.value.tableData = res.data?.list
      data.value.total = res.data?.total
    }else{
      ElMessage.error(res.msg)
    }
  })
}
load();
const reset = () => {
  data.value.name = null
  load()
}
const del = (id) => {
  ElMessageBox.confirm('确定删除吗？', '删除确认', {type: 'warning'}).then(() => {
    request.delete('user/delete/' + id).then(res => {
      if (res.code === '200'){
        ElMessage.success('删除成功')
        load()
      }else{
        ElMessage.error(res.msg)
      }
    })
  }).catch(() => {
  })
}

const handleEdit = (row) => {
  data.value.form = JSON.parse(JSON.stringify(row))
  data.value.formVisible = true


}
const add = () => {
  request.post('/user/add', data.value.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      data.formVisible = false
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const update = () => {
  request.put('/user/update', data.value.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      data.value.formVisible = false
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const save = () => {
  formRef.value.validate((valid) => {
    if (valid) {  // 表示表单校验通过
      data.value.form.id ? update() : add()
    }
  })
}


const handleAdd = () => {
  data.value.form = {}
  data.value.formVisible = true
}



</script>
<style >

</style>