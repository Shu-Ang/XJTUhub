<template>
<div>
	<div class="comment-line-box">
		<div class="comment-list-item">
			<el-avatar :src="comment.faceAddr" :size="35" style="width: 38px;"></el-avatar>
			<div class="right-box">
				<div class="new-info-box clearfix">
					<div class="comment-top">
						<div class="user-box">
							<span class="comment-name">{{ comment.roleId }}</span>
							<el-tag size="mini" type="primary" v-show="comment.roleId === blog.roleId" style="margin-left: 5px;">作者</el-tag>
							<span class="date">{{ comment.commentDate }}</span>
							<div class="opt-comment">
									<i class="el-icon-delete"></i>
									<el-button @click="deleteComment(comment)" type="default" size="mini" icon="Delete"
										style="border-radius: 15px;"></el-button>
									<i class="el-icon-chat-round" style="margin-left: 10px;"></i>
									<el-button @click="rootCommentShow = !rootCommentShow" type="default" size="mini"
										icon="ChatLineRound" style="border-radius: 15px;"></el-button>
								</div>
						</div>
					</div>
					<div class="comment-center">
						<div class="new-comment">{{ comment.commentContent }}</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 回复框 -->
		<replay v-if="comment!=null" :rootId="comment.commentId" :comment="comment" :showReplay="rootCommentShow" :blogId="blogId"  :getCommentList="getCommentList" style="margin-top: 5px;"></replay>
	</div>
</div>
</template>

<script>
import replay from '@/components/Blog/replay.vue'
import blogApi from '@/api/blog'
import userApi from '@/api/user'
import infoApi from '@/api/info'
import { tip, getLocalToken} from '@/common'
import Message from '@/common/request/request-message'
export default {
	name: "root",
	components: {
		replay
	},
	props: {
		comment: {
			type: Object,
			default: null
		},
		blog: {
			type: Object,
			default: null
		},
		getCommentList: {
			type: Function,
			default: null
		},
	},
	data() {
		return {
			rootCommentShow: false,     // 评论回复输入框是否可见
			blogId: this.blog.blogId,     // 博客ID
		}
	},
	methods: {
		// 关闭评论回复输入框
		closeReplay() {
			this.rootCommentShow = !this.rootCommentShow;
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
				await userApi.getUserId(getLocalToken).then(async res => {
					console.log(comm.roleId)
					if (res.data.userId === this.blog.roleId || res.data.userId === comm.roleId || res.data.userId === "admin") {
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
				})
			}
		},
	},
	mounted() {
		this.blogId = this.blog.blogId;
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
</style>