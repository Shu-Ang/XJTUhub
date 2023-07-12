<template>
  <div class="common-layout">
    <div id="Bgr"></div>
    <el-container>
      <el-container>
        <el-aside width="400px">
          <div style="margin-top: 50px; margin-left: 50px">
            <el-tree :data="treeData" class="kbc_tree" :props="defaultProps" @node-click="handleNodeClick" node-key="id"
              highlight-current>
            </el-tree>
          </div>
        </el-aside>
        <el-main>
          <h2 align="" style="margin-left: 225px;">
            <el-button type="primary" :dark="isDark" plain size="large" style="margin-top: 20px;"
              @click="changeToArticle">文章</el-button>
            <el-button type="primary" :dark="isDark" plain size="large" style="margin-top: 20px;"
              @click="changeToQuestion">问答</el-button>
          </h2>
          <div id="ShowBlogs">
            <div v-for="blog in blogList" :key="blog.blogid" class="singleBlog">
              <el-link :underline="false" @click="blogApi.goToBlog(blog.blogId, userId)"
                style="font-size: xx-large; font-weight: bolder;">{{blog.course.coursename}} <span>|</span>{{ blog.title }}|</el-link>
              <h3>by:{{ blog.roleId }}
                <el-icon style="margin-left: 10px;">
                  <View />
                </el-icon>{{ blog.views }}
              </h3>

              <VueShowdown :markdown="blog.summary"
                style="font: 1em sans-serif; font-style: normal; font-size: large; background-color: #ffffff;"
                flavor="vanilla" :options="{ emoji: true, tables: true }" :extensions="extensions" />
            </div>
          </div>
          <!-- 分页 -->
          <div class="demo-pagination-block" style="margin-left: 225px;">
            <div class="demonstration"></div>
            <el-pagination v-model:current-page="params.pageNum" v-model:page-size="params.pageSize"
              :page-sizes="[5, 10, 15, 20]" :small="small" :disabled="disabled" :background="background"
              layout="sizes, prev, pager, next" :total="params.totalNum" @size-change="changePageSize"
              @current-change="changePageNum" />
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>



<script setup>
import { get, post, getLocalToken, tip } from "@/common";
import { useRoute, useRouter } from 'vue-router';
import { ElTree } from "element-plus";
import { ref, onMounted, reactive, watch, toRefs, computed } from "vue";
import { Search, Edit, Check, Message, Star, Delete } from '@element-plus/icons-vue';
import blogApi from '@/api/blog'
const router = useRouter();
const route = useRoute();
const params = reactive({
  content: "",
  courseId: "",
  pageSize: 5,
  pageNum: 1,
  totalNum: 0
})
const changePageSize = (val) => {

  params.pageSize = val
  getBlogList();
}
const changePageNum = (val) => {
  params.pageNum = val
  getBlogList();
}
let userId = route.query.user
const blogList = ref([]);
const category = ref(0)
//树形结构

var treeData = ref([]);

const defaultProps = {
  children: 'children',
  label: 'name',
  id: 'id',
};

//点击事件，请求对应课程的文章
const handleNodeClick = async (node) => {
  router.push("/home")
  if (node.children == null) {
    params.courseId = node.id
    if (category.value == 0) {
      await blogApi.getRelativeArticlePage(params).then(res => {
        if (res.success) {
          blogList.value = res.data.resultList
          params.totalNum = res.data.totalNum;
        } else {
          tip.error(res.msg)
        }
      })
    } else {
      await blogApi.getRelativeQuestionPage(params).then(res => {
        if (res.success) {
          blogList.value = res.data.resultList
          params.totalNum = res.data.totalNum;
        } else {
          tip.error(res.msg)
        }
      })
    }
  }
};

const getBlogList = async () => {
  if (route.query.search != null) {
    params.content = route.query.search
    if (category.value == 0) {
      await blogApi.getArticleByContent(params).then(res => {
        if (res.success) {
          blogList.value = res.data.resultList
          params.totalNum = res.data.totalNum;
        } else {
          tip.error(res.msg)
        }
      })
    } else {
      await blogApi.getQuestionByContent(params).then(res => {
        if (res.success) {
          blogList.value = res.data.resultList
          params.totalNum = res.data.totalNum;
        } else {
          tip.error(res.msg)
        }
      })
    }
  } else {
    if (category.value == 0) {
      await blogApi.getLatestArticle(params).then(res => {
        if (res.success) {
          blogList.value = res.data.resultList
          params.totalNum = res.data.totalNum;
        } else {
          tip.error(res.msg)
        }
      })
    } else {
      await blogApi.getLatestQuestion(params).then(res => {
        if (res.success) {
          blogList.value = res.data.resultList
          params.totalNum = res.data.totalNum;
        } else {
          tip.error(res.msg)
        }
      })
    }
  }
}

const changeToArticle = () => {
  category.value = 0
  getBlogList()
}

const changeToQuestion = () => {
  category.value = 1
  getBlogList()
}
// 与后台对接时去掉注释
const getData = async () => {
  return await get("/tree/getTree")
};


// 将后台数据进行转化
const convertData = (data) => {
  // data为后台传递过来的数据
  for (let i in data) {
    let d = data[i];
    let department = {};
    department.id = d.departmentId;
    department.name = d.departmentName;

    let majors = [];
    for (let j in d.majorList) {
      let m = d.majorList[j];
      let major = {};
      major.id = m.majorId;
      major.name = m.majorName;

      let courses = [];
      for (let k in m.courseList) {
        let c = m.courseList[k];
        let course = {}
        course.id = c.courseId;
        course.name = c.courseName;
        courses.push(course);
      }
      major.children = courses;

      majors.push(major);
    }
    department.children = majors;

    treeData.value.push(department);
  }
}

const showData = async () => {
  await getData().then(res => {
    convertData(res.data)
  })

}
showData();
getBlogList();
</script>


<style scoped>
.container {
  width: 100%;
}

.el-row {
  margin-bottom: 20px;
  /* margin-top: ; */
}

.el-row:last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.el-header {
  height: 100px;
}

.el-aside {
  height: calc(100vh - 100px);
}

.el-main {
  padding: 0;
  height: calc(100vh - 100px);
}

.kbc_tree {
  /* border: 3px solid #3c89ee;S */
  margin-top: 12px;
  padding: 12px;
  filter: alpha(Opacity=10);
  -moz-opacity: 0.9;
  opacity: 0.9;
  background: rgba(214, 246, 250, 0.5);
}

#Bgr {
  /* image{ */
  z-index: -1;
  width: 100%;
  height: 100%;
  position: fixed;
  /* background: url("@/assets/HomeBack.png"); */
  background: url("@/assets/xjtu.jpg");
  top: 0;
  filter: alpha(Opacity=70);
  -moz-opacity: 0.70;
  opacity: 0.7;
  background-position: 0 0;
  background-size: 100% 100%;
  /* } */
}

#ShowBlogs {
  max-width: 1000px;
  margin: 0 auto;
  background-color: aliceblue;
}

.singleBlog {
  padding: 20px;
  margin: 20px 0;
  background: rgba(255, 250, 240, 0.9);
  /* background-color: ; */
}

.title :hover {
  cursor: pointer;
}

.demo-basic {
  text-align: center;
}

.demo-basic .sub-title {
  margin-bottom: 10px;
  font-size: 14px;
  color: var(--el-text-color-secondary);
}

.demo-basic .demo-basic--circle,
.demo-basic .demo-basic--square {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.demo-basic .block {
  flex: 1;
}

.example-showcase .el-loading-mask {
  z-index: 9;
}</style>