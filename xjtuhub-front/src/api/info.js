import { get, post } from "@/common"
import router from "../router"
import axios from "axios"
export default {

    async goToInfo(params, userId) {
        let routeData = router.resolve({
            path: "/user",
            query: { info: params ,user: userId}
        })
        window.open(routeData.href,'_blank')
    },
    async getInfo(params) {
        return await get("/info/getInfo", params)
    },
    async getFavoriteList(params) {
        return await get("/blog/getFavoriteList", params)
    }
}
