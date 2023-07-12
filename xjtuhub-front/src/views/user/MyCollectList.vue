<template>
  <div>
    <h4>我的收藏夹</h4>
    <div class="favorite_box">
      <el-button type="primary" @click="showDia" plain>新建收藏夹</el-button> 
      <div class="favorite" v-for="favorite in favorites" :key="favorite.favoriteId">
          <h2 v-on:click="enterFavorite(favorite.favoriteId, favorite.favoriteName)">{{ favorite.favoriteName }}-</h2>
          <h2>id :{{ favorite.favoriteId }}</h2>
          <div class="favorite_button">
            <el-popconfirm
              v-if="myuserId == localId"
              confirm-button-text="是"
              cancel-button-text="否"
              :icon="InfoFilled"
              icon-color="#626AEF"
              @confirm="deleteFavorite(favorite.favoriteId)"
              @cancel="cancelEvent"
              title="确定删除收藏夹吗？">
              <template #reference>
                <el-button type="danger" plain>删除收藏夹</el-button>
              </template>
            </el-popconfirm>
          </div>
      </div>
    </div>
    <el-dialog
    title="新建收藏夹"
    width="60%"
    :before-close="handleClose"
    v-model="dialogVisible">
        <el-form :model="form" :rules="rules" ref="form" label-width="150px">
        <div class="updateinfo">
            <div class="left">
            <el-form-item label="名称" prop="favName">
                <el-input v-model="favInfo.favName"></el-input>
            </el-form-item>
            <el-form-item label="是否私密" prop="isPrivate">
              <el-select v-model="favInfo.isPrivate" class="m-2" placeholder="Select" size="large">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            </div>
        </div>
        </el-form>
        <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submit">提 交</el-button>
        </span>
    </el-dialog>
  </div>  
</template>

<script setup>
import  userApi from '@/api/user.js';
import { ref, reactive } from 'vue';
import {get, post, setLocalToken, getLocalToken, removeLocalToken, tip} from "@/common";
import axios from "axios";
import { useRoute, useRouter } from 'vue-router';
import { Plus } from '@element-plus/icons-vue'

const router =useRouter();
const route = useRoute();

const favorites = ref([]);
let myuserId = route.query.myuserId;
let localId = route.query.myuserId;
console.log("myuserId", myuserId)

const myget = async () => {
    get('/info/favorites', {
        roleId: localId
    })
    .then((res) => {
        console.log(res.data);
        favorites.value = res.data;
    });
};
myget();

const enterFavorite = (favoriteId, favoriteName) => {
  router.push({
      path: '/user/mycollect', 
      query: {
          favoriteId: favoriteId,
          favoriteName: favoriteName
      }
  });
}

//打开编辑弹窗按钮
var dialogVisible = ref(false);
const showDia = () => {
  dialogVisible.value = true;
  console.log("visible",dialogVisible);
};
const handleClose = () => {
  dialogVisible.value = false;
  console.log("visible",dialogVisible);
}

// const value = ref('')

const options = [
  {
    value: 1,
    label: '是',
  },
  {
    value: 0,
    label: '否',
  },
]

const favInfo = reactive({
  favName: '',
  isPrivate: 0
})
const submit = () => {
  post('/info/newFavorite', 
  {
      roleId: localId,
      favoriteName: favInfo.favName,
      isPrivate: favInfo.isPrivate
  })
  .then((res) => {
    console.log("favInfo.isPrivate", favInfo.isPrivate);
    console.log("res", res.data)
    dialogVisible.value = false;
    location.reload();
  })
}

//删除收藏夹
const deleteFavorite = (favoriteId) => {
  post('/info/deleteFavorite', {
    favoriteId: favoriteId
  })
  .then((res) =>{
    // if(res.data)
    location.reload();
  })
}
const cancelEvent = () => { //取消操作
  console.log('cancel!')
}


</script>

<style>
.el-card {
    border-radius: 0;
}

.el-card:not(:first-child) {
    margin-top: 5px;

}
.myart1{
    line-height: 30px;
}

.favorite_box :hover {
  border-width: 1px;
  border-color: deepskyblue;
}
.favorite {
  padding: 15px 40px 15px 30px;
  height: 70px;
  display: flex;
  align-items: center;
  border: 1px solid #ebebeb;
}
.favorite :hover {
  border-width: 1px;
  border-color: deepskyblue;
}
.favorite_left {
  width: 60px;
  height: 60px;
}
.ffavorite_img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  border: 1px solid #ebebeb;
  vertical-align: top;
}
.favorite_info {
  display: inline-block;
  margin-left: 20px;
  -webkit-box-flex: 1;
  -ms-flex-positive: 1;
  flex-grow: 1;
  overflow: hidden;
}
.favorite_info_top {
  display: inline-block;
  font-size: 10;
  line-height: 14px;
  vertical-align: top;
  cursor: pointer;
}
.favorite_info_top :hover {
  color: deepskyblue;
}
.favorite_info_bottom {
  line-height: 14px;
  color: #999;
  margin-top: 5px;
  cursor: pointer;
}
.favorite_info_bottom :hover {
  color: deepskyblue;
}
.favorite_button {
  margin-left: 0px;
  /* padding-left: 1000; */
}

</style>
    