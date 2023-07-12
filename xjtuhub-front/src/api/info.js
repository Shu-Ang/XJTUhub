import {get, post} from "@/common"
import router from "../router"

export default{

    async goToInfo(roleId){
        router.push({
            path: "/user",
            query:{info: roleId}
        })
    },
    async getInfo(params){
        return await get("/info/getInfo", params)
    },
    async getFavoriteList(params){
        return await get("/info/favorites", params)
    }
}
