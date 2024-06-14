<script setup>
import { onMounted, ref, computed } from 'vue'
import { emitter, events } from "@/eventbus.js";
import apis from "@/apis.js";
import store from '@/store.js'
import {ElMessageBox} from "element-plus";
import {UploadAjaxError} from "element-plus/es/components/upload/src/ajax";

const currentGame = ref({ appid: 0, name: '' })
const pageInfo = ref({ total: 0, page: 1, size: 10 })
const patchList = ref([])
const expandPatch = ref(-1)

const isAuthenticated = computed(() => { return store.state.authKey !== null })
const uploadDialogState = ref({visible: false, patchId: 0, fileList: []})
const confirmDialogState = ref({visible: false, message: '', func: () => {}})
const inputTextState = ref({ visibleItem: '', content: '' })

function loadPatchesByGame(game, page, size, expandFirstPatch) {
  apis.getPatchesByAppid({
    appid: game.appid, page: page, size: size
  }).then(resp => {
    patchList.value = resp.data.data;
    pageInfo.value = { total: resp.data.total, page: resp.data.page, size: resp.data.size }
    if (expandFirstPatch && patchList.value.length > 0) {
      expandPatch.value = patchList.value.at(0).id
    }
  })
}

function showInputText(inputName, content) {
  if (inputTextState.value.visibleItem !== inputName) {
    inputTextState.value.content = content
    inputTextState.value.visibleItem = inputName
  }
}

function refreshThisPage() {
  inputTextState.value.content = ''
  inputTextState.value.visibleItem = ''
  emitter.emit(events.refresh)
}

function updatePatch(id, key, value) {
  apis.updatePatch({id: id, key: key, value: value})
      .then((response) => {
        refreshThisPage()
      })
}

function updateFile(id, key, value) {
  apis.updateFile({id: id, key: key, value: value})
      .then((response) => {
        refreshThisPage()
      })
}

function deletePatch(id) {
  apis.deletePatch({id: id})
      .then((response) => {
        refreshThisPage()
        emitter.emit(events.showMessage, { type: 'success', message: 'file deleted successfully.' })
      })
}

function deleteFile(id) {
  apis.deleteFile({id: id})
      .then((response) => {
        refreshThisPage()
        emitter.emit(events.showMessage, { type: 'success', message: 'file deleted successfully.' })
      })
}

function showConfirmDialog(message, func) {
  confirmDialogState.value.visible = true
  confirmDialogState.value.message = message
  confirmDialogState.value.func = func
}

function onPageSelected(page) {
  pageInfo.value.page = page
  loadPatchesByGame(currentGame.value, pageInfo.value.page, pageInfo.value.size)
}

function showUploadFileDialog(patchId) {
  uploadDialogState.value.fileList = []
  uploadDialogState.value.patchId = patchId
  uploadDialogState.value.visible = true
}

function checkDialogUploadFinished(done) {
  let allFinished = true
  for (let file of uploadDialogState.value.fileList) {
    console.log(JSON.stringify(file))
    if (file.status !== 'success') {
      allFinished = false
      break
    }
  }
  if (allFinished) {
    emitter.emit(events.refresh)
    done()
  } else {
    ElMessageBox.confirm('Are you sure to close this dialog? Files are not upload finished yet.')
        .then(() => {
          emitter.emit(events.refresh)
          done()
        })
  }
}

function calculateSize(sizeInByte) {
  if (sizeInByte > 1024 * 1024 * 1024) {
    return (sizeInByte / 1024 / 1024 / 1024).toFixed(2) + 'gb'
  }
  else if (sizeInByte > 1024 * 1024) {
    return (sizeInByte / 1024 / 1024).toFixed(2) + 'mb'
  }
  else if (sizeInByte > 1024) {
    return (sizeInByte / 1024).toFixed(2) + 'kb'
  } else {
    return sizeInByte + 'byte'
  }
}

function getPatchInputName(patchId, key) {
  return "patch-" + patchId + '-' + key
}

function getFileInputName(fileId, key) {
  return "file-" + fileId + '-' + key
}

function uploadFile(option) {
  const form = new FormData()
  form.append('file', option.file)
  form.append('patchId', uploadDialogState.value.patchId)
  const progressHandle = (progressEvent) => {
    let num = progressEvent.loaded / progressEvent.total * 100 | 0;
    option.onProgress({percent: num})
  }
  const url = "/api/v1/files/upload"
  apis.instance.post(url,
      form, {
        headers: {'Content-Type': 'multipart/form-data'},
        maxBodyLength: Infinity,
        maxContentLength: Infinity,
        emulateJSON: true,
        onUploadProgress: progressHandle
      })
      .then((response) => {
        option.onSuccess(response)
      })
      .catch((err) => {
        option.onError(new UploadAjaxError(err.message, err.status, 'POST', url))
      })
}

onMounted(() => {
  emitter.on(events.loadGame, game => {
    if (currentGame.value.appid !== game.appid) {
      currentGame.value = game
      pageInfo.value.page = 1
      loadPatchesByGame(currentGame.value, pageInfo.value.page, pageInfo.value.size, true)
    }
  })
  emitter.on(events.refresh, () => {
    loadPatchesByGame(currentGame.value, pageInfo.value.page, pageInfo.value.size)
  })
})


</script>

<template>
  <div>
    <div>
      <template v-if="currentGame.appid === 0">
        No Game Selected
      </template>

      <template v-else>
        <el-text type="primary" size="large">Current Game: {{ currentGame.name }} ({{ currentGame.appid }})</el-text>
        <el-divider direction="vertical" border-style="dashed" />
        <el-text type="danger" size="large">Total Patches: {{ pageInfo.total }}</el-text>
      </template>

    </div>

    <el-collapse style="margin-top: 16px" v-model="expandPatch" accordion>
      <el-collapse-item v-for="patch in patchList" :name="patch.id">
        <template #title>
          <div style="display: flex; justify-content: space-between; align-items: center;">
            <el-text size="large" :type="expandPatch === patch.id ? 'success' : 'primary'">
              {{ patch.patchName + ':' + patch.patchVersion }}
            </el-text>
          </div>
        </template>
        <div>
          <div style="display: flex; flex-flow: row nowrap; justify-content: flex-start; align-items: end;">

            <el-table border stripe :show-header="false"
                    :data="[
                {key: 'patchName', name: 'Name'},
                {key: 'patchVersion', name: 'Patch Version'},
                {key: 'appVersion', name: 'Correspond Game Version'},
                {key: 'description', name: 'Description'},
                {key: 'reference', name: 'Reference URL'}
            ]">

              <el-table-column label="field" width="180px">
                <template #default="scope">
                  <div style="display: flex; align-items: center; height: 32px">
                    {{ scope.row.name }}
                  </div>
                </template>
              </el-table-column>

              <el-table-column label="value" show-overflow-tooltip>
                <template #default="scope">
                  <div style="display: flex; align-items: center; word-wrap: break-word; white-space: normal">
                    <template v-if="inputTextState.visibleItem !== getPatchInputName(patch.id, scope.row.key)">

                      <template v-if="scope.row.key === 'reference'">
                        <el-link type="primary" :href="patch[scope.row.key]" target="_blank">{{ patch[scope.row.key] }}</el-link>
                      </template>
                      <template v-else>
                        <label>{{ patch[scope.row.key] }}</label>
                      </template>
                      <template v-if="isAuthenticated">
                        <el-icon size="20" style="margin-left: 10px"
                                 @click="showInputText(getPatchInputName(patch.id, scope.row.key), patch[scope.row.key])"><Edit /></el-icon>
                      </template>
                    </template>
                    <template v-if="isAuthenticated && inputTextState.visibleItem === getPatchInputName(patch.id, scope.row.key)">
                      <div style="display: flex; align-items: center; flex-flow: row nowrap;">
                        <el-input style="min-width: 320px; max-width: 720px" :type="scope.row.key === 'description' ? 'textarea': 'text'" v-model="inputTextState.content"/>
                        <el-button type="primary" plain @click="updatePatch(patch.id, scope.row.key, inputTextState.content)">Update</el-button>
                        <el-icon size="20" style="margin-left: 10px" @click="showInputText(null, null)"><Close/></el-icon>
                      </div>
                    </template>
                  </div>
                </template>
              </el-table-column>
            </el-table>

            <div style="display: flex; flex-flow: column nowrap; justify-content: normal; margin-left: 16px; margin-bottom: 2px">
              <el-button type="danger" plain style="margin: 0 0 8px 0"
                         @click="showConfirmDialog(`Confirm deleting patch: ${patch.patchName}?`, () => deletePatch(patch.id))">
                Delete Patch
              </el-button>
              <el-button type="primary" plain style="margin: 0 0 0 0" @click="showUploadFileDialog(patch.id)">
                Upload New Files
              </el-button>
            </div>
          </div>
          <el-divider content-position="left">
            Content Files:
          </el-divider>

          <el-table border stripe :show-header="false" :data="patch.files">

            <el-table-column v-for="field in ['filename', 'description']" :label="field">
              <template #default="scope">
                <div style="display: flex; align-items: center; word-wrap: break-word; white-space: normal">
                  <template v-if="inputTextState.visibleItem !== getFileInputName(scope.row.id, field)">
                    <label>{{ scope.row[field] ? scope.row[field] : '(Empty)' }}</label>
                    <template v-if="isAuthenticated">
                      <el-icon size="20" style="margin-left: 10px"
                               @click="showInputText(getFileInputName(scope.row.id, field), scope.row[field])"><Edit /></el-icon>
                    </template>
                  </template>
                  <template v-else>
                    <el-input style="margin: 0 8px" v-model="inputTextState.content"/>
                    <el-button type="primary" plain @click="updateFile(scope.row.id, field, inputTextState.content)">Update</el-button>
                    <el-icon size="20" style="margin-left: 10px" @click="showInputText(null, null)"><Close/></el-icon>
                  </template>

                </div>
              </template>
            </el-table-column>


            <el-table-column label="size" width="120px" align="center" fixed="right">
              <template #default="scope">
                <div style="display: flex; align-items: center; height: 32px">
                  {{ calculateSize(scope.row.size) }}
                </div>
              </template>
            </el-table-column>

            <el-table-column label="download" width="80px" align="center" fixed="right">
              <template #default="scope">
                <div style="display: flex; align-items: center; height: 32px">
                  <el-link :href="scope.row.url" type="primary">download</el-link>
                </div>
              </template>
            </el-table-column>

            <template v-if="isAuthenticated">
              <el-table-column label="operation" width="100px" align="center" fixed="right">
                <template #default="scope">
                  <el-button @click="showConfirmDialog(`Confirm deleting file: ${scope.row.filename}?`, () => deleteFile(scope.row.id))"
                             type="danger" plain >Delete</el-button>
                </template>
              </el-table-column>
            </template>

          </el-table>

        </div>
      </el-collapse-item>
    </el-collapse>

    <div>
      <el-pagination
          :hide-on-single-page="true"
          :total="pageInfo.total"
          :current-page="pageInfo.page"
          :page-size="pageInfo.size"
          @update:current-page="onPageSelected"
          layout="prev, pager, next"
      />
    </div>

    <el-dialog v-model="uploadDialogState.visible" title="Upload File" width="500"
               :close-on-click-modal="false" :close-on-press-escape="false"
               :before-close="checkDialogUploadFinished">
      <el-upload
          v-model:file-list="uploadDialogState.fileList"
          :http-request="uploadFile"
          multiple
      >
        <el-button type="primary">Click to upload</el-button>
        <template #tip>
          <div class="el-upload__tip">
            Upload your patch files.
          </div>
        </template>
      </el-upload>

    </el-dialog>

    <el-dialog v-model="confirmDialogState.visible" width="500">
      {{ confirmDialogState.message }}
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="confirmDialogState.visible = false">Cancel</el-button>
          <el-button type="primary" @click="confirmDialogState.visible = false; confirmDialogState.func()">
            Confirm
          </el-button>
        </div>
      </template>
    </el-dialog>

  </div>

</template>
<style scoped>

</style>