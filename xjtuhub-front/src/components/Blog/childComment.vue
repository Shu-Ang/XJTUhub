<template>
	<div>
		<div class="comment-line-box" v-for="(childComment, index) in childComments">
			<div class="comment-list-item">
				<el-avatar :src="childComment.faceAddr" @click="goToInfo(childComment.roleId)" :size="35" style="width: 38px;"></el-avatar>
				<div class="right-box">
					<div class="new-info-box clearfix">
						<div class="comment-top">
							<div class="user-box">
								<span class="comment-name">{{ childComment.roleId }}</span>
								<el-tag size="mini" type="primary" v-show="childComment.roleId === blog.roleId"
									style="margin-left: 5px;">作者</el-tag>
								<span class="text">回复</span>
								<span class="nick-name">{{ parentComment.roleId }}</span>
								<span class="date">{{ childComment.commentDate }}</span>
								<div class="opt-comment">
									<i class="el-icon-delete"></i>
									<el-button @click="deleteComment(childComment)" type="default" size="mini" icon="Delete"
										style="border-radius: 15px;"></el-button>
									<i class="el-icon-chat-round" style="margin-left: 10px;"></i>
									<el-button @click="showReplay = !showReplay" type="default" size="mini"
										icon="ChatLineRound" style="border-radius: 15px;"></el-button>
								</div>
							</div>
						</div>
						<div class="comment-center">
							<div class="new-comment">{{ childComment.commentContent }}</div>
						</div>
					</div>
				</div>
			</div>
			<!-- 回复框 -->
			<replay v-if="childComment!=null" :rootId="rootId" :comment="childComment" :showReplay="showReplay" :blogId="blogId"
				:getCommentList="getCommentList" style="margin-top: 5px;"></replay>
			<!-- 嵌套递归 -->
			<child v-if="childComment.child!=null" :childComments="childComment.child" :parentComment="childComment" :blog="blog" :rootId="rootId"
				:getCommentList="getCommentList"></child>
		</div>
	</div>
</template>

<script>
import replay from '@/components/Blog/replay.vue'
import blogApi from '@/api/blog'
import userApi from '@/api/user'
import infoApi from '@/api/info'
import Message from '@/common/request/request-message'
import { tip ,getLocalToken} from '@/common'
import { Delete, ChatLineRound } from '@element-plus/icons-vue'
export default {
	name: "child",
	components: {
		replay
	},
	props: {
		childComments: {
			type: Array,
			default: null
		},
		parentComment: {
			type: Object,
			default: null
		},
		blog: {
			type: Object,
			default: null
		},
		rootId: {
			type: Number,
			default: null
		},
		getCommentList: {
			type: Function,
			default: null
		},
	},
	data() {
		return {
			userId:"",
			showReplay: false,     // 是否显示评论回复框
			blogId: this.blog.blogId,     // 博客ID
		}
	},
	methods: {
		// 关闭评论回复输入框
		closeReplay() {
			this.showReplay = !this.showReplay;
		},
		// 删除评论
		async deleteComment(comm) {
			let flag = await Message.confirm(
				{
					title: "警告",
					content: "确定要删除这条评论吗？"
				}
			);
			if (flag) {
				// 检查权限（本文作者 或 本条评论作者 或 系统管理员 可删除）
				if (this.userId === this.blog.roleId || this.userId === comm.roleId || this.userId === "admin") {
					let res = await blogApi.deleteComment(comm);
					if (res.success) {
						tip.success(res.msg);
						this.getCommentList();
					} else {
						tip.error(res.msg);
					}
				} else {
					tip.error("您没有删除本条评论的权限");
				}
			}
		},
		async goToInfo(id){
			await infoApi.goToInfo(id, this.userId)
		},
	},
	mounted() {
		this.blogId = this.blog.blogId;
		this.userId = this.$route.query.user
	}
}
</script>

<style lang="scss" scoped>
.comment-line-box {
	-webkit-box-orient: vertical;
	-webkit-box-direction: normal;
	flex-direction: column;
	position: relative;
	margin-top: 8px;
}

.comment-line-box .comment-list-item {
	display: flex;
	width: 100%;
}

.comment-line-box .right-box {
	padding-top: 5px;
	padding-bottom: 5px;
	width: 100%;
	margin-left: 8px;
}

.comment-line-box .right-box .new-info-box {
	width: 100%;
}

.comment-line-box .right-box .new-info-box .comment-top {
	display: flex;
	-webkit-box-pack: end;
	justify-content: flex-end;
	margin-bottom: 4px;
	line-height: 20px;
	font-size: 14px;
}

.comment-line-box .right-box .new-info-box .user-box {
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

.comment-name {
	display: flex;
	-webkit-box-align: center;
	align-items: center;
}

.date {
	font-size: 14px;
	color: #777888;
	margin-left: 5px;
}

.opt-comment {
	line-height: 20px;
	height: 20px;
	// display: none;
	position: absolute;
	right: 0;
	top: 0;
	padding-left: 16px;
	background: #fff;
}

.opt-comment:hover {
	cursor: pointer;
}

.new-comment {
	font-size: 14px;
	color: #222226;
	line-height: 22px;
	word-break: break-word;
}

.replay-box {
	padding-left: 32px;
}

.comment-top .text {
	color: #999aaa;
	margin: 0 6px;
	min-width: 28px;
}

.comment-top .nick-name {
	color: #777888;
	max-width: 105px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
</style>