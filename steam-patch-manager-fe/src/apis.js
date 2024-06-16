import store from "@/store";
import { emitter, events } from "@/eventbus.js";

import axios from "axios";
import md5 from "md5";

const instance = axios.create({
    timeout: 300000
})

const salt= 'steam-patch-manager'

instance.interceptors.request.use(function (config) {
    // Do something before request is sent
    let epoch = Date.now() / 1000
    let currentMinute = (epoch - epoch % 60) / 60
    let minutePart = md5("" + currentMinute).toUpperCase()
    let keyPart = md5((store.state.authKey + salt).toUpperCase()).toUpperCase()
    let tokenValue = md5(minutePart + keyPart).toUpperCase()
    config.headers["Auth-Token"] = tokenValue
    return config;
}, function (error) {
    // Do something with request error
    return Promise.reject(error);
});

instance.interceptors.response.use(function (response) {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    let payload = response.data
    if (payload.status === null || payload.status !== 0) {
        emitter.emit(events.showMessage, {
            type: "error",
            message: "Request failed, server error: " + payload.message
        })
        throw "Server error"
    }
    return payload;
}, function (error) {
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    console.log("response error: ", error)
    let response = error.response
    let payload = response.data
    if (response.status === 401 && payload.status !== null && payload.status === 1) {
        store.commit("setAuthKey", '')
        emitter.emit(events.showMessage, {
            type: "error",
            message: "Authentication failed, please config your authentication key!"
        })
    } else {
        emitter.emit(events.showMessage, {
            type: "error",
            message: "Request failed, http status: " + response.status
        })
    }
    return Promise.reject(error);
});

export default {
    instance,
    async getSteamGames() {
        return await instance.get("/api/v1/games")
    },
    async getSteamGame(data) {
        return await instance.get("/api/v1/games/" + data.appid);
    },
    async checkToken() {
        return await instance.get("/api/v1/auth")
    },
    async getPatchesByAppid(data) { // { appid: number, page: number, size: number }
        return await instance.get("/api/v1/patches", { params: data })
    },
    async createPatch(data) {
        return await instance.post("/api/v1/patches", data)
    },
    async updatePatch(data) { // { id: number, key: string, value: string }
        return await instance.put("/api/v1/patches/" + data.id, data)
    },
    async updateFile(data) { // { id: number, key: string, value: string }
        return await instance.put("/api/v1/files/" + data.id, data)
    },
    async deletePatch(data) { // { id: number }
        return await instance.delete("/api/v1/patches/" + data.id)
    },
    async deleteFile(data) { // { id: number }
        return await instance.delete("/api/v1/files/" + data.id)
    },
}