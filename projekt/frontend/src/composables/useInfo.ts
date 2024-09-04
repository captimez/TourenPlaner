import { compile, computed, reactive, readonly, ref } from "vue";

const state = reactive({
    value: ref("")
})

export function useInfo(){
    const loescheInfo = () => {
        if(state.value != ""){
            state.value = ""
        }
    }
    
    const setzeInfo = (msg: string) => {
        state.value = msg
    }

    return {
        info: computed(() => state.value),
        state: readonly(state),
        loescheInfo,
        setzeInfo
    }
}


