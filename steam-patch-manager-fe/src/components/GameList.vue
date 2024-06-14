<script setup>
import { ref, onMounted } from 'vue'
import { emitter, events } from "@/eventbus.js";

import apis from "@/apis.js";

const games = ref([])
const selectedAppId = ref(0)

function loadPatchedGameList() {
  apis.getSteamGames().then(resp => {
    games.value = resp.data;
  })
}

function selectGame(game) { // game: { appid: number, name: string }
  selectedAppId.value = game.appid
  emitter.emit(events.loadGame, game)
}

onMounted(() => {
  loadPatchedGameList()
  emitter.on(events.refresh, () => {
    loadPatchedGameList()
  })
})

</script>

<template>
  <div>
    <ul>
      <li>
        <el-link :type="selectedAppId === 0 ? 'primary': 'default'" @click="selectGame({appid: 0, name: ''})" >
          Select None
        </el-link>
      </li>
    <template v-for="game in games" :key="game.name" >
      <li>
        <el-link :type="selectedAppId === game.appid ? 'primary': 'default'" @click="selectGame(game)">
          {{ game.name }} ({{ game.appid }})
        </el-link>
      </li>
    </template>
    </ul>
  </div>
</template>

<style scoped>
</style>