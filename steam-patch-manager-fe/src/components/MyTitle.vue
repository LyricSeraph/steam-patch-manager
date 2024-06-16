<script setup>
import { onMounted, ref, computed, reactive } from 'vue'
import { ElMessage } from "element-plus";
import { emitter, events } from "@/eventbus.js";
import apis from '@/apis.js'
import store from '@/store.js'

const inputKey = ref('')
const showDialog = ref(false)
const createPatchForm = ref({
  appid: 0,
  patchName: '',
  patchVersion: '',
  appVersion: '',
  description: '',
  reference: ''
})
const gameToCreatePatch = ref({ appid: 0, name: '' })
const isAuthenticated = computed(() => { return store.state.authKey !== null })

function checkAuthKey() {
  store.commit('setAuthKey', inputKey.value)
  apis.checkToken().then(res => {
    inputKey.value = ''
    ElMessage({
      type: "success",
      message: "Authentication successfully",
    })
  }).catch(err => {
    store.commit('setAuthKey', null)
  })
}

function revokeAuthKey() {
  store.commit('setAuthKey', null)
}

function getGameName(appid) {
  apis.getSteamGame({appid: appid}).then(resp => {
    if (resp.status === 0) {
      gameToCreatePatch.value.appid = resp.data.appid
      gameToCreatePatch.value.name = resp.data.name
    }
  })
}

function onSubmit() {
  apis.createPatch(createPatchForm.value).then((resp) => {
    if (resp.status === 0) {
      emitter.emit(events.showMessage, {type: 'success', message: 'Patch created successfully.' })
      emitter.emit(events.refreshAll)
    }
  })
}

function onCancel() {
  showDialog.value = false
  createPatchForm.value.appid = 0
  createPatchForm.value.patchName = ''
  createPatchForm.value.patchVersion = ''
  createPatchForm.value.appVersion = ''
  createPatchForm.value.description = ''
  createPatchForm.value.reference = ''
}

</script>

<template>

  <div style="width: 100%; display: flex; flex-flow: row nowrap; align-items: baseline">
    <label style="flex-grow: 1">Steam Patch Manager</label>
    <el-space size="large" />
    <template v-if="isAuthenticated">
      <el-button type="danger" plain @click="revokeAuthKey">Revoke Authenticate</el-button>
    </template>
    <template v-else>
      <el-input
          v-model="inputKey"
          style="width: 200px"
          type="password"
          placeholder="Please input auth-key"
          show-password />
      <el-button type="primary" plain @click="checkAuthKey">Authenticate</el-button>
    </template>
    <el-button v-show="isAuthenticated" type="primary" plain @click="showDialog = true">New Patch</el-button>
  </div>

  <el-dialog v-model="showDialog">
    <el-form :model="createPatchForm" label-width="auto" style="max-width: 600px">
      <el-form-item label="App Id">
        <el-input-number v-model="createPatchForm.appid" />
        <el-button type="primary" @click="getGameName(createPatchForm.appid)">Check App Name</el-button>
      </el-form-item>
      <el-form-item label="App Name">
        <el-text size="large" type="info">{{ gameToCreatePatch.name ? gameToCreatePatch.name : '(No Game Found)' }}</el-text>
      </el-form-item>
      <el-form-item label="Patch Name">
        <el-input v-model="createPatchForm.patchName" />
      </el-form-item>
      <el-form-item label="Patch Version">
        <el-input v-model="createPatchForm.patchVersion" />
      </el-form-item>
      <el-form-item label="App Version">
        <el-input v-model="createPatchForm.appVersion" />
      </el-form-item>
      <el-form-item label="Description">
        <el-input v-model="createPatchForm.description" />
      </el-form-item>
      <el-form-item label="Reference URL">
        <el-input v-model="createPatchForm.reference" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Create</el-button>
        <el-button @click="onCancel">Cancel</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<style scoped>

</style>