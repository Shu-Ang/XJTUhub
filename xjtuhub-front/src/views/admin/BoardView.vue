<!-- eslint-disable no-unused-vars -->
<template>
  <div class="dashboard">
    <el-row type="flex" :gutter="20" style="margin-bottom: 30px">
      <el-col :span="6">
        <el-card class="custom-card">
          <div class="d-card d1">
            <h1>用户数</h1>
            <p>{{ userNum }}</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="custom-card">
          <div class="d-card d2">
            <h1>访问量</h1>
            <p>{{ viewNum }}</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="custom-card">
          <div class="d-card d3">
            <h1>文章数</h1>
            <p>{{ articleNum }}</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="custom-card">
          <div class="d-card d4">
            <h1>问题数</h1>
            <p>{{ questionNum }}</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <div class="charts-body" ref="map_ref"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { get, tip } from '../../common'
export default {
  name: 'dashBoard',
  data() {
    return {
      userNum: 0,
      viewNum: 0,
      questionNum: 0,
      articleNum: 0,
      instance: null
    }
  },
  methods: {
    async handleCreateMap() {
      await get("/admin/blog/countRecentView").then(res => {
        if (res.success) {
          this.instance = echarts.init(this.$refs.map_ref)
          this.instance.setOption({
            color: ['#4b9cba', '#53a551', '#f5c444', '#c84648'],
            xAxis: {
              type: 'category',
              data: res.data.date
            },
            yAxis: {
              type: 'value'
            },
            series: [
              {
                data: res.data.data,
                type: 'bar',
                barWidth: 50
              }
            ]
          })
        } else {
          tip.error(res.msg)
        }
      })

    },
    async getUserNum() {
      await get("/admin/role/countRole").then(res => {
        if (res.success) {
          this.userNum = res.data
        } else {
          tip.error(res.msg)
        }
      })
    },
    async getViewNum() {
      await get("/admin/blog/countView").then(res => {
        if (res.success) {
          this.viewNum = res.data
        } else {
          tip.error(res.msg)
        }
      })
    },
    async getArticleNum() {
      await get("/admin/blog/countArticle").then(res => {
        if (res.success) {
          this.articleNum = res.data
        } else {
          tip.error(res.msg)
        }
      })
    },
    async getQuestionNum() {
      await get("/admin/blog/countQuestion").then(res => {
        if (res.success) {
          this.questionNum = res.data
        } else {
          tip.error(res.msg)
        }
      })
    },
    async getViewsLastWeek() {

    },
  },
  mounted() {
    this.$nextTick(() => {
      this.getViewsLastWeek()
      this.handleCreateMap()
    }).then(res => {
      this.getUserNum();
      this.getViewNum();
      this.getArticleNum();
      this.getQuestionNum();
    })

  }
}
</script>

<style>
.custom-card .el-card__body {
  padding: 0;
}

div.d-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 50px 20px;
  border-radius: 4px;
}

div.d-card>h1 {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 30px;
  color: #fff;
  text-align: center;
}

div.d-card>p {
  font-size: 24px;
  color: #fff;
  text-align: center;
}

div.d-card.d1 {
  background: #4b9cba;
}

div.d-card.d2 {
  background: #53a551;
}

div.d-card.d3 {
  background: #f5c444;
}

div.d-card.d4 {
  background: #c84648;
}

div.charts-body {
  background: #fff;
  width: 100%;
  height: 600px;
  box-shadow: 0 0 6px 0 rgba(0, 0, 0, 0.3);
  border-radius: 4px;
}</style>
