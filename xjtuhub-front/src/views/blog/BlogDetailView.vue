<template class = "back">
    <div v-loading="loading" class="box">
        <el-main>
            <el-row>
                <!-- 侧边栏部分 -->
                <el-col :span="5" style="margin-left: 40px;">
                    <el-backtop :right="50" :bottom="50"> </el-backtop>
                    <!-- 作者信息 -->
                    <el-row>
                        <div class="writerInfo">
                            <el-col>
                                <el-card :body-style="{ padding: '10px' }">
                                    <el-avatar :size="270" :shape="square" :src="writer.faceAddr"
                                        @click="goToInfo(writer.roleId)" />
                                    <div style="padding: 14px; font-size: x-large;font-weight: 500; ">
                                        <span style="text-align: center; display:block;">{{ writer.roleId }} <el-tag
                                                size="mini" type="primary" style="margin-left: 5px;">作者</el-tag></span>
                                        <div style="font-size: large;">
                                            <span style="text-align: center; display:block;">e-mail:{{ writer.email
                                            }}</span>
                                        </div>
                                        <div style="font-size: large;">
                                            <span style="text-align: center; display:block;">{{ writer.sign }}</span>
                                        </div>
                                    </div>
                                </el-card>
                            </el-col>
                        </div>
                    </el-row>
                    <!-- 相关文章 -->
                    <el-row>
                        <div class="aside-box">
                            <el-card class="relative" style="margin-top: 20px;">
                                <h3 class="aside-title" style="font-size: x-large; font-weight: 500;">相关文章</h3>
                                <li v-for="rela in relativeList">
                                    <el-link type="default" @click="goToBlog(rela.blogId)" style="font-size: large;"
                                        :underline="true">{{
                                            rela.title }}<el-icon style="margin-right: 5px;">
                                            <View />
                                        </el-icon>
                                        {{ rela.views }}</el-link>
                                </li>
                            </el-card>
                        </div>
                    </el-row>
                </el-col>
                <!-- 文章主体部分 -->
                <el-col :span="17" style="margin-left: 10px;">
                    <!-- 内容区 -->
                    <div class="blog-content-box">
                        <!-- 头部信息 -->
                        <div class="article-header-box">
                            <div class="article-header">
                                <div class="article-title-box">
                                    <h1 class="title-article">{{ blog.title }}</h1>
                                </div>
                                <div class="article-info-box">
                                    <div class="article-bar-top">
                                        <span style="margin-left: 10px;color: black;">{{ blog.roleId }}</span>
                                        <span style="margin-left: 20px;color: #999aaa;display: inline-block;"><i
                                                class="el-icon-time"></i> 于 {{ blog.releaseDate }} 发布</span>
                                        <span style="margin-left: 15px;color: #999aaa;display: inline-block;"><i
                                                class="el-icon-view"></i>浏览量： {{ blog.views }}</span>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <el-card>
                            <VueShowdown :markdown="this.blog.summary"
                                style="font: 1em sans-serif; font-style: normal; font-size: large; background-color: #c7caca;"
                                flavor="vanilla" :options="{ emoji: true, tables: true }" :extensions="extensions" />
                        </el-card>
                        <!-- 文章内容 -->
                        <VueShowdown :markdown="articleContent"
                            style="font: 1em sans-serif; font-style: normal; font-size: x-large;" flavor="vanilla"
                            :options="{ emoji: true, tables: true }" :extensions="extensions" />

                        <!-- 底部栏 -->
                        <div class="more-toolbox-new">
                            <div class="left-toolbox">
                                <div class="toolbox-right">
                                    <ul class="toolbox-list">

                                        <li class="tool-item tool-item-size tool-active is-like">
                                            <el-tooltip class="item" effect="dark" content="点 赞" placement="top">
                                                <div class="like-div" ref="likeFix">
                                                    <el-icon @click="likeClick()" style="font-size: x-large;">
                                                        <Pointer />
                                                    </el-icon>
                                                    <span>{{ likeNum }}</span>
                                                </div>
                                            </el-tooltip>
                                        </li>
                                        <li class="tool-item tool-item-size tool-active is-like">
                                            <el-tooltip class="item" effect="dark" content="评 论" placement="top">
                                                <div class="comment-div">
                                                    <el-icon @click="commentShow = !commentShow"
                                                        style="font-size: x-large;">
                                                        <ChatLineRound />
                                                    </el-icon>
                                                    <span>{{ commentNum }}</span>
                                                </div>
                                            </el-tooltip>
                                        </li>
                                        <li class="tool-item tool-item-size tool-active is-like">
                                            <el-tooltip class="item" effect="dark" content="收 藏" placement="top">
                                                <div class="star-div" ref="starFix">
                                                    <el-icon @click="starClick()" style="font-size: x-large;">
                                                        <Star />
                                                    </el-icon>
                                                    <span>{{ starNum }}</span>
                                                </div>
                                            </el-tooltip>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 评论区 -->
                    <div class="comment-box comment-box-new2 login-comment-box-new" style="display: block;">
                        <!-- 评论抽屉 -->
                        <div v-show="commentShow" class="comment-box comment-box-new2"
                            style="display: block; padding: 10px 24px 32px 24px;">
                            <!-- 添加评论 -->
                            <div class="comment-edit-box d-flex">
                                <div class="user-img">
                                    <el-avatar :src="currUser.faceAddr" :size="35" style="width: 35px;"></el-avatar>
                                </div>
                                <form class="comment-form">
                                    <textarea class="comment-content" v-model="commentContent" placeholder="想对作者说点什么"
                                        maxlength="1000" @keyup="countNum()"></textarea>
                                    <div class="comment-operate-box">
                                        <div class="comment-operate-l">
                                            <span class="tip">
                                                还能输入
                                                <em>{{ commentWordNum }}</em>
                                                个字符
                                            </span>
                                        </div>
                                        <div class="comment-operate-r">
                                            <el-button type="primary" size="mini" style="border-radius: 20px;"
                                                @click="addComment()">评论</el-button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <!-- 显示评论 -->
                            <div class="comment-list-container">
                                <div class="comment-list-box comment-operate-item">
                                    <ul class="comment-list" v-for="comment in commentList">
                                        <!-- 评论根目录 -->
                                        <root v-if="comment != null && blog != null" :comment="comment" :blog="blog"
                                            :getCommentList="getCommentList">
                                        </root>
                                        <!-- 评论子目录 -->
                                        <li class="replay-box" style="display: block;">
                                            <ul class="comment-list">
                                                <!-- 子组件递归实现 -->
                                                <child v-if="comment.child != null && comment != null && blog != null"
                                                    :childComments="comment.child" :parentComment="comment" :blog="blog"
                                                    :rootId="comment.commentId" :getCommentList="getCommentList"></child>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <!-- 暂无评论的空状态 -->
                            <el-empty description="暂无评论，快来抢沙发吧" v-show="commentNum === 0"></el-empty>
                        </div>
                    </div>
                    <div>
                        <el-dialog v-model="favoriteListShow" title="选择你的收藏夹" width="30%" align-center>
                            <el-radio-group @change="selectChanged(this.favoriteId)" v-model="this.favoriteId"
                                v-for="favorite in favoriteList" style="flex-flow: column nowrap; display:table-row;">
                                <el-radio :label=favorite.favoriteId>{{ favorite.favoriteName }}</el-radio>
                            </el-radio-group>
                            <template #footer>
                                <span class="dialog-footer">
                                    <el-button @click="favoriteListShow = false">取消</el-button>
                                    <el-button type="primary" @click="submitStar()">
                                        确定
                                    </el-button>
                                </span>
                            </template>
                        </el-dialog>

                    </div>
                </el-col>
            </el-row>
        </el-main>
    </div>
</template>

<script>
import blogApi from '@/api/blog'
import userApi from '@/api/user'
import infoApi from '@/api/info'
import child from '@/components/Blog/childComment.vue'
import root from '@/components/Blog/rootComment.vue'
import { tip, getLocalToken } from '@/common'
import { Pointer, Star, ChatLineRound, View } from '@element-plus/icons-vue'
export default {
    name: "blogDetails",
    components: {
        child,
        root
    },
    data() {
        return {
            currUser: {
                roleId: "",
                faceAddr: "",
                email: "",
                sign: ""
            },
            blog: {     // 博客信息
                blogId: 0,
                title: "",
                summary: "",
                content: "",
                roleId: "",
                views: 0,
                releaseDate: null,
                courseId: 0,
            },
            writer: {
                roleId: "",
                faceAddr: null,
                email: "",
                sign: ""
            },
            like: {
                roleId: "",
                blogId: 0,
                flag: 0,
                commentId: null
            },
            star: {
                favoriteId: 0,
                blogId: 0,
            },
            relativeList: null,    // 相关文章列表
            articleContent: "",     // 文章内容 md
            likeClickNum: 0,    // 点赞次数
            likeNum: 0,        //点赞总数
            starClickNum: 0,    //收藏次数
            starNum: 0,            //收藏数
            favoriteList: null,      //收藏夹列表
            favoriteListShow: false,
            favoriteId: 0,
            commentNum: 0,      // 评论总数
            commentList: [      // 评论列表
                {
                    commentId: null,
                    commentContent: null,
                    roleId: null,
                    commentDate: null,
                    blogId: null,
                    parentId: null,
                    rootId: null,
                    faceAddr: null,
                }
            ],
            commentShow: false,
            commentContent: "",   // 评论内容
            commentWordNum: 1000,   // 评论剩余字数
            commentModel: {     // 评论模型
                commentContent: "",
                roleId: null,
                blogId: null,
                parentId: null,
                rootId: null
            },
        }
    },
    methods: {
        // 获取文章内容
        async getBlogDetails() {
            await blogApi.getBlogDetails({ blogId: this.blog.blogId }).then(async res => {
                await infoApi.getInfo({ roleId: res.data.roleId }).then(result => {
                    this.writer = result.data
                })
                this.blog = res.data;
                this.articleContent = res.data.content;
                await blogApi.getRelativeArticle({
                    blogId: this.blog.blogId,
                    courseId: this.blog.courseId
                }).then(res => {
                    if (res.success) {
                        this.relativeList = res.data;
                    } else {
                        tip.error(res.msg)
                    }
                })
            })
        },
        async updateLike() {
            await blogApi.getLikesNum({ blogId: this.blog.blogId }).then(async res => {
                if (res.success) {
                    this.likeNum = res.data
                    await userApi.getUserId(getLocalToken).then(async res => {
                        if (res.success) {
                            await blogApi.isLike({
                                roleId: res.data.userId,
                                blogId: this.blog.blogId
                            }).then(res => {
                                if (res.success) {
                                    this.likeClickNum = res.data
                                    if (this.likeClickNum === 1) {
                                        let likeElem = this.$refs.likeFix.parentNode;
                                        likeElem.style.color = "red";    // 调整点赞后样式
                                    }
                                } else {
                                    tip.error(res.msg)
                                }
                            })
                        } else {
                            tip.error(res.msg)
                        }
                    })
                }
            })
        },
        // 点赞点击事件
        async likeClick() {
            await userApi.getUserId(getLocalToken).then(async res => {
                this.like.roleId = res.data.userId
                this.like.blogId = this.blog.blogId
                if (this.likeClickNum === 0) {
                    await blogApi.like(this.like).then(async res => {
                        this.likeClickNum = 1
                        await this.updateLike().then(res => {
                            let likeElem = this.$refs.likeFix.parentNode;
                            likeElem.style.color = "red";    // 调整点赞后样式
                        })
                    })
                } else {
                    await blogApi.like(this.like).then(async res => {
                        this.likeClickNum = 0
                        await this.updateLike().then(res => {
                            let likeElem = this.$refs.likeFix.parentNode;
                            likeElem.style.color = null;    // 调整点赞后样式
                        })
                    })
                }
            })
        },
        async updateStar() {
            await blogApi.getStarsNum({ blogId: this.blog.blogId }).then(async res => {
                if (res.success) {
                    this.starNum = res.data
                    await userApi.getUserId(getLocalToken).then(async res => {
                        if (res.success) {
                            await blogApi.isStar({
                                blogId: this.blog.blogId,
                                roleId: res.data.userId
                            }).then(res => {
                                if (res.success) {
                                    this.starClickNum = res.data
                                    if (this.starClickNum === 1) {
                                        let starElem = this.$refs.starFix.parentNode;
                                        starElem.style.color = "rgb(226, 168, 8)";    // 调整收藏后样式
                                    }
                                }
                            })
                        }
                    })
                }
            })
        },
        async starClick() {
            if (this.starClickNum === 0) {
                await userApi.getUserId(getLocalToken).then(async res => {
                    if (res.success) {
                        await infoApi.getFavoriteList({ roleId: res.data.userId }).then(async res => {
                            this.favoriteList = res.data
                            this.favoriteListShow = true
                        })
                    } else {
                        tip.error(res.msg)
                    }
                })
            }

        },
        submitStar() {
            this.favoriteListShow = false
            console.log(this.favoriteId)
            blogApi.star({
                blogId: this.blog.blogId,
                favoriteId: this.favoriteId
            }).then(res => {
                if (res.success) {
                    this.updateStar();
                    tip.success(res.msg)
                } else {
                    tip.error(res.msg)
                }
            })
        },
        selectChanged(val) {
            console.log(val)
        },
        // 获取评论列表
        async getCommentList() {
            await blogApi.getCommentList({ blogId: this.blog.blogId }).then(async res => {
                if (res.success) {
                    this.commentList = res.data;
                    await blogApi.countComment({ blogId: this.blog.blogId }).then(res => {
                        if (res.success) {
                            this.commentNum = res.data;
                        } else {
                            tip.error(res.msg)
                        }
                    })
                } else {
                    tip.error(res.msg)
                }
            })
        },
        // 展开评论面板
        readComment() {
            this.drawer = true;
            this.drawTite = "评论 " + this.commentNum;
        },
        // 计算评论字数
        countNum() {
            let extraNum = 1000 - this.commentContent.length;
            if (extraNum <= 0)
                this.commentWordNum = 0;
            else
                this.commentWordNum = extraNum;
        },
        // 添加评论
        async addComment() {
            await userApi.getUserId(getLocalToken).then(async res => {
                this.commentModel.commentContent = this.commentContent;
                this.commentModel.blogId = this.blog.blogId;
                this.commentModel.roleId = res.data.userId;
                await blogApi.addComment(this.commentModel).then(res => {
                    if (res.success) {
                        tip.success(res.msg);
                        this.getCommentList();
                        this.commentContent = "";
                    } else {
                        tip.error(res.msg);
                    }
                })
            })
        },
        async goToBlog(blogId) {
            blogApi.goToBlog(blogId)
        },
        async goToInfo(roleId) {
            infoApi.goToInfo(roleId)
        },
        async getCurrUser() {
            await userApi.getUserId().then(res => {
                if (res.success) {
                    infoApi.getInfo({roleId: res.data.userId}).then(res => {
                        if (res.success) {
                            this.currUser.roleId = res.data.roleId
                            this.currUser.faceAddr = res.data.faceAddr
                            this.currUser.email = res.data.email
                            this.currUser.sign = res.data.sign
                        } else {
                            tip.error(res.msg)
                        }
                    })
                }else{
                    tip.error(res.msg)
                }
            })
        },
    },
    async created() {
        this.blog.blogId = this.$route.query.blog;     // 赋值博客ID
        await this.getCurrUser();
        await this.getBlogDetails();    // 获取文章内容
        await this.updateLike();
        await this.updateStar();
        await this.getCommentList();    // 获取评论列表
    }
}
</script>

<style lang="scss" scoped>
.back {
    background-color: antiquewhite;
}

.box {
    width: 100%;
    // height: 100vh;
    position: absolute;
    background-size: 100% 100%;
    background-color: #adddf3;
}

.writerInfo {
    width: 300px;
}

.writerImg {

    cursor: pointer;
}

.image {
    size: small;
    width: 200px;
    height: 200px;
}

.relative {
    width: 300px;
}

div.aside-box {
    margin-bottom: 8px;
    background-color: #fff;
    border-radius: 2px;
}

div.aside-box h3.aside-title {
    color: #3d3d3d;
    background: #f5f5f5;
    padding: 0 16px;
    margin: 0;
    height: 38px;
    line-height: 38px;
    position: relative;
    font-size: 14px;
    font-weight: bold;
}

div.aside-box div.aside-content {
    padding: 12px 16px 16px 16px;
    overflow: hidden;
}


div.aside-box div.aside-content * {
    word-wrap: break-word;
}


a {
    text-decoration: none;
    outline: none;
}


.blog-content-box {
    position: relative;
    padding: 0 24px 5px;
    background: #fff;
    border-radius: 2px;
}

.blog-content-box .article-header-box {
    border-bottom: 1px solid #f5f6f7;
    padding-top: 20px;
    z-index: 9;
    background-color: #fff;
}

.blog-content-box .article-header-box .article-title-box {
    margin-bottom: 8px;
}

.blog-content-box .article-header-box .article-title-box .title-article {
    font-size: 28px;
    word-wrap: break-word;
    color: #222226;
    font-weight: 600;
    margin: 0;
    word-break: break-all;
}

.blog-content-box .article-header-box .article-header .article-info-box {
    position: relative;
    background: #f8f8f8;
    border-radius: 4px;
    margin-top: 18px;
    padding: 10px 0;
}

.article-bar-top {
    color: #999aaa;
    width: 88%;
    display: flex;
}

.blog-content-box article {
    position: relative;
    padding-top: 16px;
    display: block;
}

.more-toolbox-new {
    display: flex;
    position: relative;
    margin-bottom: 8px;
}

.left-toolbox {
    position: relative;
    z-index: 999;
    left: 0;
    bottom: 0px;
    width: 100%;
    display: flex;
    flex-wrap: nowrap;
    -webkit-box-pack: justify;
    justify-content: space-between;
    -webkit-box-align: center;
    padding: 17px 24px;
    margin-top: 20px;
    height: 64px;
    box-sizing: border-box;
    background: #fff;
    box-shadow: 0 -1px 8px 0 rgb(0 0 0 / 6%);
    border-bottom-left-radius: 2px;
    border-bottom-right-radius: 2px;
}

.more-toolbox-new .toolbox-left {
    display: flex;
    flex-wrap: nowrap;
    -webkit-box-pack: justify;
    justify-content: space-between;
    -webkit-box-align: center;
    align-items: center;
}

.more-toolbox-new .toolbox-left span {
    height: 24px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    font-size: 16px;
    font-weight: 600;
    color: #222226;
    line-height: 24px;
    margin-right: 8px;
}

.toolbox-right {
    -webkit-box-flex: 1;
    flex: 1;
}

.more-toolbox-new .toolbox-list {
    width: 100%;
    height: 30px;
    display: flex;
    flex-wrap: nowrap;
    -webkit-box-align: center;
    align-items: center;
    -webkit-box-pack: end;
    justify-content: flex-end;
    color: rgb(153, 154, 170);
    margin: 0;
}

.more-toolbox-new .tool-active {
    box-sizing: border-box;
    position: relative;
}

.more-toolbox-new .tool-item {
    padding: 0 8px;
}

.more-toolbox-new .tool-active:first-child .like-div {
    padding-left: 0;
    display: flex;
    flex-wrap: nowrap;
    -webkit-box-align: center;
    align-items: center;
}

.more-toolbox-new .like-div span {
    font-size: 25px;
}

.more-toolbox-new .comment-div span {
    font-size: 25px;
}

.more-toolbox-new .star-div span {
    font-size: 25px;
}

ul {
    list-style: none;
    padding: 0;
    padding-inline-start: 5px;
    -webkit-box-sizing: border-box;
}

.like-div:hover {
    cursor: pointer;
    color: red;
}

.comment-div:hover {
    cursor: pointer;
    color: #2ba8e2;
}

.star-div:hover {
    cursor: pointer;
    color: rgb(226, 168, 8);
}

.aside-content li .title {
    line-height: 22px;
    margin-bottom: 2px;
    color: #999aaa;
    display: block;
}

.text-truncate {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.aside-content li p.comment {
    font-size: 14px;
    color: #555666;
    word-wrap: break-word;
    vertical-align: top;
    display: block;
    word-break: break-all;
    margin-top: 0;
    margin-bottom: 0;
}

.ellipsis {
    position: relative;
    line-height: 1.5em;
    max-height: 3em;
    overflow: hidden;
}

.comment-box.comment-box-new2.login-comment-box-new {
    padding: 16px 24px;
}

.comment-box.comment-box-new2 {
    margin-bottom: 8px;
    border-radius: 2px;
    background-color: #fff;
    width: 100%;
    height: max-content;
}

.comment-box.comment-box-new2.login-comment-box-new .has-comment .has-comment-tit {
    height: 32px;
    font-size: 18px;
    font-weight: 600;
    color: #222226;
    line-height: 32px;
}

.comment-box.comment-box-new2.login-comment-box-new .has-comment .has-comment-tit .count {
    font-size: 14px;
    color: #555666;
    line-height: 20px;
    margin-left: 12px;
    cursor: pointer;
}

.comment-box.comment-box-new2.login-comment-box-new .has-comment .has-comment-tit .text {
    font-size: 14px;
    color: #555666;
    line-height: 20px;
    margin-left: 4px;
    cursor: pointer;
}

.has-comment-tit .has-comment-bt-right {
    float: right;
    width: 82px;
    height: 32px;
    border-radius: 16px;
    text-align: center;
    padding-top: 5px;
    padding-bottom: 5px;
    font-size: 14px;
    color: #fff;
}

.has-comment .has-comment-con {
    color: #222226;
}

.hot-comment-box {
    display: flex;
    -webkit-box-pack: start;
    justify-content: flex-start;
    -webkit-box-align: start;
    align-items: flex-start;
    padding-top: 8px;
}

.hot-comment-box .hot-comment-con {
    width: 100%;
    margin-left: 8px;
    font-size: 14px;
    color: #222226;
    line-height: 22px;
    word-break: break-word;
    padding-top: 4px;
}

.hot-comment-box .hot-comment-user {
    margin-left: 8px;
    margin-right: 8px;
    font-size: 14px;
    color: #222226;
    line-height: 22px;
    padding-top: 3px;
}

.comment-box.comment-box.comment-box-new2 .comment-edit-box {
    -webkit-box-pack: justify;
    justify-content: space-between;
}

.comment-box.comment-box.comment-box-new2 .user-img {
    margin-right: 8px;
    padding-top: 5px;
}

.comment-box.comment-box-new2 .comment-form {
    width: 100%;
    position: relative;
    background: rgba(245, 246, 247, 0.8);
    border-radius: 8px;
    padding: 14px 0;
}

.d-flex {
    display: flex !important;
}

.comment-box.comment-box-new2 .comment-content {
    display: block;
    width: 100%;
    background: rgba(248, 249, 251, 0.8);
    border: none;
    padding: 0 16px;
    border-radius: 4px;
    resize: none;
    height: 88px;
    font-size: 14px;
    line-height: 22px;
    box-sizing: border-box;
    border-top-left-radius: 8px;
    border-top-right-radius: 8px;
    overflow: auto;
    outline: none;
}

.comment-box.comment-box-new2 .comment-operate-box {
    display: flex;
    -webkit-box-pack: justify;
    justify-content: space-between;
    padding: 8px 16px 0 16px;
    border-bottom-left-radius: 8px;
    border-bottom-right-radius: 8px;
    transition: display .3s ease-in-out;
}

.comment-box.comment-box-new2 .comment-operate-l,
.comment-box.comment-box-new2 .comment-operate-r {
    display: flex;
    -webkit-box-align: center;
    align-items: center;
}

.comment-box.comment-box-new2 .comment-operate-l .tip {
    font-size: 12px;
    color: #999aaa;
    line-height: 17px;
}

.comment-box.comment-box-new2 .tip em {
    color: #222226;
    margin: 0 4px;
    font-style: normal;
}

.comment-box.comment-box-new2 .comment-list-container {
    padding-top: 12px;
}

.comment-box.comment-box-new2 .comment-list .comment-line-box,
.comment-list .comment-line-box {
    -webkit-box-orient: vertical;
    -webkit-box-direction: normal;
    flex-direction: column;
    position: relative;
    margin-top: 8px;
}

.comment-box.comment-box-new2 .comment-list .comment-line-box .comment-list-item,
.comment-list .comment-line-box .comment-list-item {
    display: flex;
    width: 100%;
}

.comment-box.comment-box-new2 .comment-list .comment-line-box .right-box {
    padding-top: 5px;
    padding-bottom: 5px;
    width: 100%;
    margin-left: 8px;
}

.comment-box.comment-box-new2 .comment-list .comment-line-box .right-box .new-info-box {
    width: 100%;
}

.comment-box.comment-box-new2 .comment-list .comment-line-box .right-box .new-info-box .comment-top {
    display: flex;
    -webkit-box-pack: end;
    justify-content: flex-end;
    margin-bottom: 4px;
    line-height: 20px;
    font-size: 14px;
}

.comment-box.comment-box-new2 .comment-list .comment-line-box .right-box .new-info-box .user-box {
    -webkit-box-flex: 1;
    flex: 1;
    display: flex;
    -webkit-box-align: center;
    align-items: center;
    position: relative;
    right: 0;
    top: 0;
    padding-right: 8px;
}

.comment-box.comment-box-new2 .comment-name {
    display: flex;
    -webkit-box-align: center;
    align-items: center;
}

.comment-box.comment-box-new2 .date {
    font-size: 14px;
    color: #777888;
    margin-left: 5px;
}

.comment-box.comment-box-new2 .opt-comment {
    line-height: 20px;
    height: 20px;
    // display: none;
    position: absolute;
    right: 0;
    top: 0;
    padding-left: 16px;
    background: #fff;
}

.comment-box.comment-box-new2 .opt-comment:hover {
    cursor: pointer;
}

.comment-box.comment-box-new2 .new-comment {
    font-size: 14px;
    color: #222226;
    line-height: 22px;
    word-break: break-word;
}

.comment-box.comment-box-new2 .replay-box {
    padding-left: 32px;
}

.comment-box.comment-box-new2 .comment-top .text {
    color: #999aaa;
    margin: 0 6px;
    min-width: 28px;
}

.comment-box.comment-box-new2 .comment-top .nick-name {
    color: #777888;
    margin-right: 10px;
    max-width: 105px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.comment-box.comment-box-new2 .comment-list .comment-line-box .comment-edit-box {
    padding-left: 40px;
    margin-bottom: 16px;
    margin-top: -8px;
}

.comment-box.comment-box-new2 .comment-edit-box form {
    width: 100%;
    position: relative;
    background: rgba(245, 246, 247, 0.8);
    border-radius: 8px;
    padding: 14px 0;
}</style>
