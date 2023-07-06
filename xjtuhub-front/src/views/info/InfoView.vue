<template>
  <div class="content">
    <div class="leftinfo">
      <div class="topuser">
        <div class="avtor">
          <div class="imgbox">

          </div>
          <el-button type="primary">关注</el-button>
          <el-button>编辑</el-button>

        </div>
        <div class="userInfo">
          <!-- <editor :value="emailForm.msg" @updateValue="getMsg"></editor> -->
        </div>
      </div>
      <div class="bottomContnet">
        <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
          <el-tab-pane label="文章" name="first">
        
          </el-tab-pane>
          <el-tab-pane label="问题" name="second">问题</el-tab-pane>
          <el-tab-pane label="收藏夹" name="third">收藏夹</el-tab-pane>
          <el-tab-pane label="草稿箱" name="fourth">草稿箱</el-tab-pane>
          <el-tab-pane label="历史足迹" name="five">历史足迹</el-tab-pane>

        </el-tabs>
      </div>
    </div>
    <div class="rightEdit">
      <div class="datastatis" style="background: #3b4554;">数据统计</div>
      <div class="datastatis" style="background:#54b3a4 ;">关注列表</div>
      <div class="datastatis" style="background: #4181d5;">粉丝列表</div>
    </div>
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import editor from '../../components/editor.vue';
import axios from 'axios'
import { useRouter } from 'vue-router'

import { Plus } from '@element-plus/icons-vue'

const router = useRouter()
//富文本相关的内容
const emailForm = reactive({
  test_msg: ''
})
const getMsg = (val) => {
  console.log("获取到的value",val );
  emailForm.msg = val
}

//tab模块的代码
const activeName = ref('first')
const handleClick = (tab, event) => {
  // console.log(tab, event)
}

// 图片回显路径
const imageUrl = ref('')
// 添加文件时的回显
const handleAvatarChange = (uploadFile) => {
  const reader = new FileReader()
  // 从上传组件中获取数据
  const image = uploadFile.raw
  reader.readAsDataURL(image)
  // 读取文件的回调函数
  reader.onload = () => {
    // 将转化的url赋值给文件
    imageUrl.value = reader.result
  }
}
// 上传之前做简单验证
const beforeAvatarUpload = (rawFile) => {
  if (!rawFile.type.startsWith('image/')) {
    tip.error('只能上传图片格式!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 5) {
    tip.error('上传文件不能大于5MB!')
    return false
  }
  return true
}
// 上传图片
const handleAvatarUpload = (uploadFile) => {
  const formData = new FormData()
  formData.append('file', uploadFile.file)
  axios.post('/product/img-upload', formData).then((res) => {
    // 图片在服务器的名称
    //commodityAdd.imgs = res.data.fileName;
  })
}
</script>
<style>
.content {
  display: flex;
  justify-content: space-between;
  padding: 0 10px;
}
.topuser{
  display: flex;
  padding: 10px;
  justify-content: space-between;

}
.bottomContnet{
  padding: 10px;

}
.imgbox{
  width: 120px;
  height: 120px;
  margin-bottom: 20px;
  border: 1px solid red;
}
.imgbox img{
  width: 100%;
  height: 100%;
}
.userInfo{
  width: 80%;
  height: 120px;  
  border: 1px solid black;
}

.leftinfo {
  width: 60%;
  border: 1px solid red;
}
.rightEdit {
  width: 30%;
  /* border: 1px solid red; */
}
.datastatis{
  width: 100%;
  height: 120px;
  margin-bottom: 10px;
  border-radius: 2px;
  font-size:25px;
  color: white;
  text-align: center;
  line-height: 120px;
}

#backg {
  background: url('@/assets/A.png');
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
}
</style>
