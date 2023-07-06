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
            <p>{{ QuestionNum }}</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <div class="charts-body" ref="map_ref"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
export default {
  name: 'dashBoard',
  data () {
    return {
      instance: null
    }
  },
  methods: {
    handleCreateMap () {
      this.instance = echarts.init(this.$refs.map_ref)
      this.instance.setOption({
        color: ['#4b9cba', '#53a551', '#f5c444', '#c84648'],
        xAxis: {
          type: 'category',
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [10, 20, 15, 8, 7, 11, 13],
            type: 'bar',
            barWidth: 50
          }
        ]
      })
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.handleCreateMap()
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

div.d-card > h1 {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 30px;
  color: #fff;
  text-align: center;
}

div.d-card > p {
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
}
</style>
