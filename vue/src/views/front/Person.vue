

<template>
  <div class="front-container">
    <div class="card">
      <div style="font-weight: bold; font-size: 30px; text-align: center; margin-bottom: 30px;">个人信息</div>
      <el-form ref="formRef" :model="data.user" :rules="data.rules" label-width="120px" style="padding-top: 30px">
        <el-form-item label="账号" prop="username">
          <el-input :disabled="data.user.id !== undefined" v-model="data.user.username" placeholder="请输入账号"  autocomplete="off" />
        </el-form-item>

        <el-form-item label="姓名" prop="name">
          <el-input v-model="data.user.name" placeholder="请输入姓名"></el-input>
        </el-form-item>

        <el-form-item label="余额" prop="account">
          <div style="color: red">余额{{data.user.account}}</div>
        </el-form-item>

        <el-form-item label="头像" prop="avatar">
          <el-upload
              class="avatar-uploader"
              :action= "baseUrl + '/files/upload'"
              :show-file-list= "false"
              :on-success="handleFileUpload">
            <img v-if="data.user.avatar" :src="data.user.avatar" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <div style="text-align: center;">
        <el-button type="primary" @click="update" size="medium">保存</el-button>
        </div>
      </el-form>
    </div>
  </div>

</template>


<script setup>

import {ref} from "vue";
import request from "../../utils/request";
import {ElMessage, ElMessageBox, ElUpload} from "element-plus";

const baseUrl = import.meta.env.VITE_BASE_URL;

const emit = defineEmits(['updateUser']);

  const formRef = ref();
  const data = ref({
    user: JSON.parse(localStorage.getItem('system-user') || '{}'),
    rules: {
      username: [
        {required: true, message: '请输入账号', trigger: 'blur'}
      ],
    },
  })




  const loadUser= () => {
    request.get('/user/selectById/' + data.value.user.id).then( res => {
      data.value.user = res.data;
      //存储最新的用户信息
      localStorage.setItem('system-user', JSON.stringify(res.data));
      emit('updateUser')//?
    });
  }

  const handleFileUpload = (response) => {
  console.log(response);
  data.value.user.avatar = response.data;
  }

  loadUser();

  const update = () => {
    request.put('/user/update', data.value.user).then(res => {
      if (res.code === '200') {
        ElMessage.success('更新成功')
        loadUser()
        emit('updateUser');
      } else {
        ElMessage.error(res.msg)
      }
    })
  }
const updateUser = () => {
  data.value.user = JSON.parse(localStorage.getItem('system-user') || '{}')

}
</script>


<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>