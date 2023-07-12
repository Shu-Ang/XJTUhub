import { get, post, getLocalToken, tip } from "@/common"
import router from '@/router'
import userApi from './user'


export default {
	// 获取相关博客
	async getRelativeArticle(params) {
		return await get("/search/ArticleByCourse", params)
	},
	// 获取相关问题
	async getRelativeQuestion(params) {
		return await get("/search/QuestionByCourse", params)
	},
	// 分页获取相关博客
	async getRelativeArticlePage(params) {
		return await get("/search/ArticlePageByCourse", params)
	},
	// 分页获取相关问题
	async getRelativeQuestionPage(params) {
		return await get("/search/QuestionPageByCourse", params)
	},
	// 删除博客
	async deleteBlog(params) {
		return await post("/blog/delete", params)
	},

	// 获取博客内容
	async getBlogDetails(params) {
		return await get("/blog/detail", params)
	},

	// 获取最新博客
	async getLatestArticle(params) {
		return await get("/search/newArticlePage", params)
	},
	// 获取最新问答
	async getLatestQuestion(params) {
		return await get("/search/newQuestionPage", params)
	},
	async getArticleByContent(params) {
		return await get("/search/ArticlePageByContent", params)
	},
	async getQuestionByContent(params) {
		return await get("/search/QuestionPageByContent", params)
	},
	// 更新点赞数
	async getLikesNum(params) {
		return await get("/blog/getLikes", params)
	},
	// 点赞
	async like(params) {
		return await post("/blog/like", params)
	},
	async isLike(params) {
		return await get("/blog/isLike", params)
	},
	async getStarsNum(params) {
		return await get("/blog/getStar", params)
	},
	async star(params) {
		return await post("/blog/star", params)
	},
	async isStar(params) {
		return await get("/blog/isStar", params)
	},
	// 新增博客
	async addBlog(params) {
		return await post("/blog/releaseBlog", params)
	},

	// 获取评论列表
	async getCommentList(params) {
		return await get("/blog/getCommentList", params)
	},
	// 获取评论总数
	async countComment(params) {
		return await get("/blog/countComment", params)
	},

	// 添加评论
	async addComment(params) {
		return await post("/blog/comment", params)
	},

	// 删除评论
	async deleteComment(params) {
		return await post("/blog/deleteComment", params)
	},
	async getFaceAddrList(params){
		return await get("/blog/getFaceAddrList",params)
	},
	async goToBlog(params) {
		await userApi.getUserId().then(async res => {
			console.log(res.data.userId)
			if (res.success) {
				await post("/blog/view", {
					blogId: params,
					roleId: res.data.userId
				}).then(res => {
					if (res.success) {
						this.getBlogDetails({blogId: params}).then(res => {
							if (res.success) {
								if (res.data.category == 0) {
									router.push({
										path: "/blog",
										query: { blog: params }
									})
								} else {
									router.push({
										path: "/qa",
										query: { qa: params }
									})
								}
							} else {
								tip.error(res.msg)
							}
						})
					} else {
						tip.error(res.msg)
					}
				})
			} else {
				tip.error(res.msg)
			}
		})
	},
	async getAnswerList(params) {
		return await get("/blog/getAnswerList", params)
	},
	async getWriterList(params) {
		return await get("/blog/getWriterList", params)
	},
	async getAnswerLikeList(params) {
		return await get("/blog/getAnswerLikeList", params)
	},
	async getIsLikeList(params) {
		return await get("/blog/isLikeList", params)
	}
}
