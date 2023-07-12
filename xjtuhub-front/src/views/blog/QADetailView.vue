<template class = "back">
    <div class="background">
        <div v-loading="loading" class="box">
            <el-main>
                <el-row>
                    <el-backtop :right="50" :bottom="50" />
                    <!-- 侧边栏部分 -->
                    <el-col :span="5" style="margin-left: 40px;">
                        <!-- 作者信息 -->
                        <el-row>
                            <div class="writerInfo">
                                <el-col>
                                    <el-card :body-style="{ padding: '10px' }">
                                        <el-avatar :size="270"  :shape="square" :src="writer.faceAddr" @click="goToInfo(writer.roleId)"/>
                                        <div style="padding: 14px; font-size: x-large;font-weight: 500; ">
                                            <span style="text-align: center; display:block;" >{{ writer.roleId }} <el-tag size="mini" type="primary" style="margin-left: 5px;">题主</el-tag></span>
                                            
                                            <div style="font-size: large;">
                                                <span style="text-align: center; display:block;" >e-mail:{{ writer.email }}</span>
                                            </div>
                                            <div style="font-size: large;">
                                                <span style="text-align: center; display:block;" >{{ writer.sign }}</span>
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
                                    <h3 class="aside-title" style="font-size: x-large; font-weight: 500;">相关问题</h3>
                                    <li v-for="rela in relativeList">
                                        <el-link type="default" @click="goToBlog({ blogId: rela.blogId })"
                                            style="font-size: large;" :underline="true">{{
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
                        <div class="blog-content-box" style="margin-left: -33px;">
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
                                    style="font: 1em sans-serif; font-style: normal; font-size: large; background-color: #d9edf0;"
                                    flavor="vanilla" :options="{ emoji: true, tables: true }" :extensions="extensions" />
                            </el-card>
                            <!-- 文章内容 -->
                            <VueShowdown :markdown="this.blog.content"
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
                                                        <el-icon @click="likeClick(this.blog.blogId)"
                                                            style="font-size: x-large;">
                                                            <Pointer />
                                                        </el-icon>
                                                        <span>{{ likeNum }}</span>
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
                    </el-col>
                    <el-row v-for="(answer, index) in answerList">
                        <el-col :span="5" style="margin-left: 40px;">
                            <!-- 作者信息 -->
                            <el-row>
                                <div class="writerInfo" style="margin-top: 30px;">
                                    <el-col>
                                        <el-card v-if="writerList[index]" :body-style="{ padding: '10px' }">
                                            <div class="writerImg" @click="goToInfo(writerList[index].roleId)">
                                                <img :src="writerList[index].faceAddr" class="image" />
                                            </div>
                                            <div style="padding: 14px; font-size: x-large;font-weight: 500;">
                                                <span>{{ writerList[index].roleId }}</span>
                                                <el-tag size="mini" type="primary" style="margin-left: 5px;" v-show="writerList[index].roleId == blog.blogId">题主</el-tag>
                                                <div style="font-size: large;">
                                                    <span>e-mail:{{ writerList[index].email }}</span>
                                                </div>
                                                <div style="font-size: large;">
                                                    <span>{{ writerList[index].sign }}</span>
                                                </div>
                                            </div>
                                        </el-card>
                                    </el-col>
                                </div>
                            </el-row>
                        </el-col>
                        <!-- 文章主体部分 -->
                        <el-col :span="17" style="margin-left: 10px;">
                            <!-- 内容区 -->
                            <div class="blog-content-box" style="margin-top: 30px;">
                                <!-- 头部信息 -->
                                <div class="article-header-box">
                                    <div class="article-header">
                                        <div class="article-info-box">
                                            <div class="article-bar-top">
                                                <span style="margin-left: 10px;color: black;">{{ answer.roleId }}</span>
                                                <span style="margin-left: 20px;color: #999aaa;display: inline-block;"><i
                                                        class="el-icon-time"></i> 于 {{ answer.releaseDate }} 发布</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- 文章内容 -->
                                <VueShowdown :markdown="answer.content"
                                    style="font: 1em sans-serif; font-style: normal; font-size: x-large;" flavor="vanilla"
                                    :options="{ emoji: true, tables: true }" :extensions="extensions" />
                                <!-- 底部栏 -->
                                <div class="more-toolbox-new">
                                    <div class="left-toolbox">
                                        <div class="toolbox-right">
                                            <ul class="toolbox-list">
                                                <li class="tool-item tool-item-size tool-active is-like">
                                                    <el-tooltip class="item" effect="dark" content="点 赞" placement="top">
                                                        <div class="like-div">
                                                            <el-icon @click="likeListClick(index)"
                                                                style="font-size: x-large; " :ref="Refs">
                                                                <Pointer />
                                                            </el-icon>
                                                            <span>{{ likeList[index] }}</span>
                                                        </div>
                                                    </el-tooltip>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="5" style="margin-left: 40px;">
                            <!-- 作者信息 -->
                    <el-row>
                            <div class="writerInfo">
                                <el-col>
                                    <el-card :body-style="{ padding: '10px' }">
                                        <el-avatar :size="270"  :shape="square" :src="user.faceAddr" @click="goToInfo(user.roleId)"/>
                                        <div style="padding: 14px; font-size: x-large;font-weight: 500; ">
                                            <span style="text-align: center; display:block;" >{{ user.roleId }} <el-tag v-if="user.roleId == blog.roleId" size="mini" type="primary" style="margin-left: 5px;">题主</el-tag></span>
                                            <div style="font-size: large;">
                                                <span style="text-align: center; display:block;" >e-mail:{{ user.email }}</span>
                                            </div>
                                            <div style="font-size: large;">
                                                <span style="text-align: center; display:block;" >{{ user.sign }}</span>
                                            </div>
                                        </div>
                                    </el-card>
                                </el-col>
                            </div>
                        </el-row>
                        </el-col>
                        <el-col style="margin-top: -300px; margin-left: -35px;">
                            <el-form-item label="" prop="content">
                                <mavon-editor v-model="form.content" ref="md" @imgAdd="handleEditorImgAdd"
                                    @imgDel="handleEditorImgDel" style=" margin-left: 425px; width: 1300px;" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-footer style="margin-left: 1600px;">
                        <el-button type="primary" @click="release()">发布</el-button>
                    </el-footer>
                </el-row>

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
            </el-main>
        </div>
    </div>
</template>

<script>
import blogApi from '@/api/blog'
import userApi from '@/api/user'
import infoApi from '@/api/info'
import child from '@/components/Blog/childComment.vue'
import root from '@/components/Blog/rootComment.vue'
import { tip } from '@/common'
import { Pointer, Star, View } from '@element-plus/icons-vue'
import axios from 'axios'
export default {
    name: "blogDetails",
    components: {
        child,
        root
    },
    data() {
        return {
            blog: {     // 博客信息
                blogId: 0,
                title: "",
                summary: "",
                content: "",
                roleId: "",
                views: 0,
                releaseDate: null,
                courseId: 0,
                course: null
            },
            writer: {
                roleId: "",
                faceAddr: null,
                email: "",
                sign: ""
            },
            user: {
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
            likeNum: 0,        //点赞总数
            starClickNum: 0,    //收藏次数
            starNum: 0,            //收藏数
            favoriteList: null,      //收藏夹列表
            favoriteListShow: false,
            favoriteId: 0,
            answerList: [],
            writerList: [],
            likeList: [],
            form: {
                roleId: null,
                content: '',
                category: 1,
                isDraft: 0,
                isPrivate: 0,
                rootId: null,
                courseId: null,
            }
        }
    },
    methods: {
        // 获取问题内容
        async getBlogDetails() {
            await blogApi.getBlogDetails({ blogId: this.blog.blogId }).then(async res => {
                await infoApi.getInfo({ roleId: res.data.roleId }).then(result => {
                    this.writer = result.data
                })
                this.blog = res.data;
                this.articleContent = res.data.content;
                await blogApi.getRelativeQuestion({
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
        async getAnswerList() {
            await blogApi.getAnswerList({ blogId: this.blog.blogId }).then(res => {
                if (res.success) {
                    this.answerList = res.data
                } else {
                    tip.error(res.msg)
                }
            })
        },
        async getWriterList() {
            await blogApi.getWriterList({ blogId: this.blog.blogId }).then(res => {
                if (res.success) {
                    this.writerList = res.data
                } else {
                    tip.error(res.msg)
                }
            })
        },
        async updateLikeList() {
            await blogApi.getAnswerLikeList({ blogId: this.blog.blogId }).then(async res => {
                if (res.success) {
                    this.likeList = res.data
                } else {
                    tip.error(res.data)
                }
            })
        },
        async updateLikeOfIndex(index) {
            await blogApi.getLikesNum({ blogId: this.answerList[index].blogId }).then(async res => {
                if (res.success) {
                    this.likeList[index] = res.data
                }
            })
        },
        async likeListClick(index) {
            await userApi.getUserId().then(async res => {
                if (res.success) {
                    this.like.roleId = res.data.userId
                    this.like.blogId = this.answerList[index].blogId
                    await blogApi.like(this.like).then(async res => {
                        if (res.success) {
                            await this.updateLikeOfIndex(index)
                        } else {
                            tip.error(res.msg)
                        }
                    })
                } else {
                    tip.error(res.msg)
                }
            })
        },
        async updateLike() {
            await blogApi.getLikesNum({ blogId: this.blog.blogId }).then(async res => {
                if (res.success) {
                    this.likeNum = res.data
                    await userApi.getUserId().then(async res => {
                        if (res.success) {
                            this.user.roleId = res.data.userId
                            await infoApi.getInfo({ roleId: res.data.userId }).then(res => {
                                this.user.faceAddr = res.data.faceAddr
                                this.user.email = res.data.email
                                this.user.sign = res.data.sign
                            })
                            await blogApi.isLike({
                                roleId: res.data.userId,
                                blogId: this.blog.blogId
                            }).then(res => {
                                if (res.success) {
                                    if (res.data === 1) {
                                        let likeElem = this.$refs.likeFix.parentNode;
                                        likeElem.style.color = "red";    // 调整点赞后样式
                                    } else {
                                        let likeElem = this.$refs.likeFix.parentNode;
                                        likeElem.style.color = null;    // 调整点赞后样式
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
            await userApi.getUserId().then(async res => {
                this.like.roleId = res.data.userId
                this.like.blogId = this.blog.blogId
                await blogApi.like(this.like).then(async res => {
                    await this.updateLike()
                })
            })
        },
        async updateStar() {
            await blogApi.getStarsNum({ blogId: this.blog.blogId }).then(async res => {
                if (res.success) {
                    this.starNum = res.data
                    await userApi.getUserId().then(async res => {
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
                await userApi.getUserId().then(async res => {
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
        async goToBlog(blogId) {
            blogApi.goToBlog(blogId)
        },
        async goToInfo(roleId) {
            infoApi.goToInfo(roleId)
        },
        handleEditorImgAdd(pos, $file) {
            const formdata = new FormData();
            formdata.append('image', $file)
            console.log(formdata)
            axios.post("/dev/blog/uploadImg", formdata, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then((result) => {
                console.log(result)
                if (result.data.status === 200) {
                    console.log(result)
                    tip.success("上传成功")
                    console.log(result.data)
                    console.log(result.data.data.imgUrl)
                    let url = result.data.data.imgUrl
                    let name = $file.name
                    if (name.includes('-')) {
                        name = name.replace(/-/g, '')
                    }
                    let content = this.form.content
                    // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)  这里是必须要有的
                    if (content.includes(name)) {
                        let oStr = `(${pos})`
                        let nStr = `(${url})`
                        let index = content.indexOf(oStr)
                        let str = content.replace(oStr, '')
                        let insertStr = (soure, start, newStr) => {
                            return soure.slice(0, start) + newStr + soure.slice(start)
                        }
                        this.form.content = insertStr(str, index, nStr)
                    }
                } else {
                    tip.error("上传失败")
                }
            })
        },
        async handleEditorImgDel(pos) {
        },
        async release() {
            this.form.category = 1
            this.form.roleId = this.user.roleId
            this.form.courseId = this.blog.courseId
            this.form.rootId = this.blog.blogId
            await blogApi.addBlog(this.form).then(res => {
                if (res.success) {
                    tip.success("发布成功！")
                    this.form.content = ""
                } else {
                    tip.error(res.msg)
                }
            })
        },
    },
    async created() {
        this.blog.blogId = this.$route.query.qa;     // 赋值博客ID
        await this.getBlogDetails();    // 获取文章内容
        await this.getAnswerList();
        await this.getWriterList();
        await this.updateLike();
        await this.updateStar();
        await this.updateLikeList();
    },

}
</script>

<style lang="scss" scoped>
.back {
    background-color: rgb(117, 78, 26);
}

.background {
    background: url('@/assets/xjtu.jpg');
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
    width: 1300px;
    position: relative;
    padding: 0 24px 5px;
    background: #f8fafa;
    border-radius: 2px;

}

.blog-content-box .article-header-box {
    border-bottom: 1px solid #202020;
    padding-top: 20px;
    z-index: 9;
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
    color: rgb(99, 104, 184);
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
}
</style>
