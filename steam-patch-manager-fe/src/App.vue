<script setup>
import { onMounted, ref } from 'vue';
import { emitter, events } from "@/eventbus.js";

import GameList from "@/components/GameList.vue";
import PatchPanel from "@/components/PatchPanel.vue";
import {ElMessage} from "element-plus";
import MyTitle from "@/components/MyTitle.vue";

const isDarkTheme = ref(true)

onMounted(() => {
  emitter.on(events.showMessage, (data) => {
    ElMessage(data)
  })
})

</script>

<template>
  <div class="dark">
    <el-container style="padding: 32px">
      <el-header style="padding: 0 20px 0 20px; margin-bottom: 16px">
        <el-card>
          <my-title />
        </el-card>
      </el-header>
      <el-container>
        <el-aside style="display: flex;">
          <el-card style="margin: 20px 8px 20px 20px; width: 100%;">
            <game-list />
          </el-card>
        </el-aside>
        <el-container>
          <el-main style="display: flex">
            <el-card style="width: 100%;">
              <patch-panel />
            </el-card>
          </el-main>
          <el-footer style="margin-bottom: 20px">
            <el-card>
              <div style="display: flex; align-items: baseline; font-size: 12px">
                <el-text>Powered by <a href="https://github.com/LyricSeraph" target="_blank"><span>Lyric Law</span></a></el-text>
              </div>
            </el-card>
          </el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>
</style>
