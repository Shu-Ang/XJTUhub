<template>
  <div>
    <h4>{{ localId }}的收藏夹</h4>
    <div class="favorite_box">
      <el-button type="primary" @click="showDia" plain><el-icon><CirclePlus /></el-icon>新建收藏夹</el-button> 
      <div class="favorite" v-for="favorite in favorites" :key="favorite.favoriteId">
          <div class="favorite_info">
            <h3 v-on:click="enterFavorite(favorite.favoriteId)" style="cursor: pointer"
            ><el-icon><StarFilled /></el-icon> {{ favorite.favoriteName }}</h3>
          </div>
          <div class="favorite_button">
            <el-popconfirm
              v-if="userId == localId"
              confirm-button-text="是"
              cancel-button-text="否"
              :icon="InfoFilled"
              icon-color="#626AEF"
              @confirm="deleteFavorite(favorite.favoriteId)"
              @cancel="cancelEvent"
              title="确定删除收藏夹吗？">
              <template #reference>
                <el-button type="danger" plain><el-icon><Delete /></el-icon>删除收藏夹</el-button>
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
      <!-- 分页 -->
    <div class="demo-pagination-block" style="margin-left: 140px;">
      <div class="demonstration"></div>
      <el-pagination v-model:current-page="params.pageNum" v-model:page-size="params.pageSize"
        :page-sizes="[5, 10, 15, 20]" :small="small" :disabled="disabled" :background="background"
        layout="sizes, prev, pager, next" :total="params.totalNum" @size-change="changePageSize"
        @current-change="changePageNum" />
    </div>
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
let localId = route.query.info;
let userId = route.query.user;

//请求文章
const params = reactive({
  roleId: "",
  pageSize: 5,
  pageNum: 1,
  totalNum: 0
})
const changePageSize = (val) => {
  params.pageSize = val
  getFavoriteList();
}
const changePageNum = (val) => {
  params.pageNum = val
  getFavoriteList();
}

const getFavoriteList = async () => {
    params.roleId = localId 
    get('/info/favorites', params)
    .then((res) => {
        favorites.value = res.data.resultList;
        params.totalNum = res.data.totalNum;
    });
};
getFavoriteList();

const enterFavorite = (favoriteId) => {
  router.push({
      path: '/user/mycollect', 
      query: {
          favorite: favoriteId,
          user: userId,
          info: localId
      }
  });
}

//打开编辑弹窗按钮
var dialogVisible = ref(false);
const showDia = () => {
  dialogVisible.value = true;
};
const handleClose = () => {
  dialogVisible.value = false;
}


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
  margin-bottom: 5px
}
.favorite :hover {
  border-width: 1px;
  border-color: deepskyblue;
}
.favorite_left {
  width: 60px;
  height: 60px;
}
.favorite_img {
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
  margin-right: 0px;
  /* padding-left: 1000; */
}

</style>
    