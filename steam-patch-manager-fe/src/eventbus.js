import mitt from 'mitt'

export const emitter = mitt()

export const events = {
    showMessage: 'showMessage', // param: { type: string, message: string }
    loadGame: 'loadGame', // param: { appid: number, name: string }
    refresh: 'refresh', // no param
}

export default {
    emitter, events
}