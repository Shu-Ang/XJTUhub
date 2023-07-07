import {get, post, del} from "@/common"

export default{
	
	
	async getSearchBlogList(query) {
		return await get("/searchBlog", query)
	},

	// 查询博客列表
	async getBlogList(params) {
		return await get("/api/blog/list", params)
	},

	// 删除博客
	async deleteBlog(params) {
		return await del("/api/blog/delete", params)
	},

	// 更新点赞数
	async updateLike(params) {
		return await http.post("/api/blog/updateLike", params)
	},
	
	// 新增博客
	async addBlog(params) {
		return await http.post("/api/blog/add", params)
	},

	// 获取评论列表
	async getCommentList(params) {
		return await http.get("/api/comment/list", params)
	},

	// 获取最新评论
	async getLatestComments() {
		return await http.get("/api/comment/latest")
	},

	// 添加评论
	async addComment(params) {
		return await http.post("/api/comment/add", params)
	},

	// 删除评论
	async deleteComment(params) {
		return await http.post("/api/comment/delete", params);
	}
}

const getBlogById = async (blog)=> {
	await get("/blog/detail",blog)
}
export{getBlogById}