<template>
    <el-main>
        <el-col :loading=true :span="20" :offset="2">
            <el-card v-loading="loading" class="markdown-body">
                <h1>{{ blog.title }}</h1>
                <blockquote>{{ '作者：' + blog.roleId + ' | 发布时间：' + blog.releaseDate + ' | 浏览数：' + blog.views }}
                </blockquote>
                <VueShowdown :markdown="blog.content" flavor="vanilla" :options="{ emoji: true, tables: true }"
                    :extensions="extensions" />
            </el-card>
        </el-col>
        <el-backtop></el-backtop>
    </el-main>
    <el-aside>
        <el-col>
            <el-card :body-style="{ padding: '10px' }">
                <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
                    class="image" />
                <div style="padding: 14px">
                    <span>Yummy hamburger</span>
                    <div class="bottom">
                        <time class="time">{{ currentDate }}</time>
                        <el-button text class="button">Operating</el-button>
                    </div>
                </div>
            </el-card>
        </el-col>
    </el-aside>
</template>

<script>
import { getBlogById } from "@/api/blog";
import { reactive } from "vue";
const blog = reactive({
    blogId: 7
})
export default {
    name: "Article",
    data() {
        return {
            blog: { content: "" },

        };
    },

    created() {
        blog.blogId = this.$route.params.pathMatch[0]
        getBlogById(blog)
            .then(res => {

                this.blog = res.data;
                console.log(this.$route.params.pathMatch[0])
            })

    }
};
</script>

<style scoped>
.el-card {
    margin-top: 30px;
    padding: 20px;
}
</style>
