<template>
	<el-collapse-transition>
		<div v-show="showReplay">
			<div class="comment-edit-box d-flex">
				<form>
					<textarea class="comment-content" v-model="commentContent" placeholder="回复: " maxLength="1000"
						@keyup="countNum()"></textarea>
					<div class="comment-operate-box">
						<div class="comment-operate-l">
							<span class="tip">
								还能输入
								<em>{{ countWordNum }}</em>
								个字符
							</span>
						</div>
						<div class="comment-operate-r">
							<el-button type="danger" size="mini" style="border-radius: 20px;"
								@click="closeComment()">收起</el-button>
							<el-button type="primary" size="mini" style="border-radius: 20px;"
								@click="addComment()">评论</el-button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</el-collapse-transition>
</template>

<script>
import blogApi from '@/api/blog'
import { tip, getLocalToken } from '@/common'
import userApi from '@/api/user'
export default {
	name: "replay",
	props: {
		rootId: {
			type: Number,
			default: null
		},
		comment: {    // 父评论
			type: Object,
			default: null
		},
		showReplay: {
			type: Boolean,
			default: false
		},
		blogId: {
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
			countWordNum: 1000,     // 剩余可输入字数
			commentContent: "",     // 评论输入内容
			commentModel: {         // 添加评论模型
				commentContent: "",
				roleId: "",
				blogId: null,
				parentId: null,
				rootId: null
			}
		}
	},
	methods: {
		// 计算剩余可输入字数
		countNum() {
			this.countWordNum = 1000 - this.commentContent.length;
		},
		// 添加评论
		async addComment() {
			console.log()
			console.log(this.comment)
			this.commentModel.commentContent = this.commentContent;
			this.commentModel.blogId = this.blogId;
			this.commentModel.parentId = this.comment.commentId;
			this.commentModel.rootId = this.rootId;
			this.commentModel.roleId = this.userId;
			await blogApi.addComment(this.commentModel).then(res => {
				if (res.success) {
					tip.success(res.msg);
					this.$parent.closeReplay();    // 关闭输入框
					this.getCommentList();
					this.commentContent = "";
				} else {
					tip.error(res.msg);
				}
			})
		},
		async closeComment() {
			this.$parent.closeReplay();
		}
	},
	mounted() {
		this.userId = this.$route.query.user
	}
}
</script>

<style lang="scss" scoped>
.comment-edit-box {
	-webkit-box-pack: justify;
	justify-content: space-between;
}

.d-flex {
	display: flex !important;
}

.comment-operate-box {
	display: flex;
	-webkit-box-pack: justify;
	justify-content: space-between;
	padding: 8px 16px 0 16px;
	border-bottom-left-radius: 8px;
	border-bottom-right-radius: 8px;
	transition: display .3s ease-in-out;
}

.comment-operate-l,
.comment-operate-r {
	display: flex;
	-webkit-box-align: center;
	align-items: center;
}

.comment-operate-l .tip {
	font-size: 12px;
	color: #999aaa;
	line-height: 17px;
}

.tip em {
	color: #222226;
	margin: 0 4px;
	font-style: normal;
}

.comment-edit-box {
	padding-left: 40px;
	margin-bottom: 16px;
	margin-top: -8px;
}

.comment-edit-box form {
	width: 100%;
	position: relative;
	background: rgba(245, 246, 247, 0.8);
	border-radius: 8px;
	padding: 14px 0;
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
</style>