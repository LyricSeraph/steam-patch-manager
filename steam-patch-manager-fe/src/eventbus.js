import mitt from 'mitt'

export const emitter = mitt()

export const events = {
    showMessage: 'showMessage', // param: { type: string, message: string }
    loadGame: 'loadGame', // param: { appid: number, name: string }
    refreshAll: 'refreshAll', // no param
    refreshPatch: 'refreshPatch',

}

export default {
    emitter, events
}