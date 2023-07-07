<template>
    <section class='container'>
        <section class='itemLeft'>
            <div class="sidebarright">
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
            </div>
        </section>
        <section class='itemCenter'>
            <el-col>
                <el-card v-loading="loading" class="markdown-body">
                    <h1>{{ blog.title }}</h1>
                    <blockquote>{{ '作者：' + blog.roleId + ' | 发布时间：' + blog.releaseDate + ' | 浏览数：' + blog.views }}
                    </blockquote>
                    <VueShowdown :markdown="blog.content" flavor="vanilla" :options="{ emoji: true, tables: true }"
                        :extensions="extensions" />
                </el-card>
            </el-col>
        </section>
        <section class='itemRight'>
            <div class="sidebarright">
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
            </div>
        </section>
    </section>
    <!-- 显示评论 -->
<div class="comment-list-container">
	<div class="comment-list-box comment-operate-item">
		<ul class="comment-list" v-for="comment in commentList">
			<!-- 评论根目录 -->
			<root :comment="comment" :blog="blog" :getCommentList="getCommentList"></root>
			<!-- 评论子目录 -->
			<li class="replay-box" style="display: block;">
				<ul class="comment-list">
					<!-- 子组件递归实现 -->
					<child :childComments="comment.child" :parentComment="comment" :blog="blog" :rootParentId="comment.id" :getCommentList="getCommentList" v-if="comment.child != null"></child>
				</ul>
			</li>
		</ul>
	</div>
</div>

</template>
<script >
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
            isFixed: false
        };
    },
    created() {
        blog.blogId = this.$route.params.pathMatch[0]
        getBlogById(blog)
            .then(res => {

                this.blog = res.data;
                console.log(this.$route.params.pathMatch[0])
            })

    },
    handleScroll() {
        const scrollPosition = window.scrollY;
        if (scrollPosition > 0) {
            this.isFixed = true;
        } else {
            this.isFixed = false;
        }
    }
};

</script>
<style scoped>
/* 中间容器 */
.container {

    margin: 0 auto;
    display: flex;
    top: 0px;

    .itemRight {
        flex: 3;
    }

    .itemLeft {
        flex: 3;

    }

    .itemCenter {
        flex: 7;
    }
}

.el-card {
    margin-top: 30px;
    padding: 20px;
}

.sidebarleft {
    position: fixed;
    /* 设置固定定位 */
    top: 0;
    /* 设置固定在顶部 */

}

.sidebarright {
    position: fixed;
    /* 设置固定定位 */
    top: 0;
    /* 设置固定在顶部 */

}</style>
