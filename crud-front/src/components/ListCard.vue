<script setup lang="ts">
    import { ref } from 'vue';

    const props = defineProps<{
        id: string,
        title: string,
    }>();
    const emit = defineEmits(['listDeleted', 'listUpdated']);
    const listTitle = ref(props.title);
    
    function deleteList() {
        emit('listDeleted', props.id);
    }

    function updateList() {
        emit('listUpdated', props.id, listTitle)
    }
</script>

<template>
    <div class="listCard">
        <h2 class="listTitle">
           {{ title }} 
        </h2>
        <v-menu :location="'end'" >
            <template v-slot:activator="{props}">
                <v-icon class="iconMenu" size="26px" color="var(--grey400)" v-bind="props">mdi-dots-vertical</v-icon>
            </template>
            <v-list style="background-color: var(--grey500); border-radius: 8px; padding:0px; color: var(--grey300)">
                <v-dialog max-width="500">
                    <template v-slot:activator="{ props: activatorProps }">
                        <div class="itemMenu itemMenuTop" v-bind="activatorProps">
                            <v-icon>mdi-pencil</v-icon>
                            <span>Editar lista</span>
                        </div>                         
                    </template>
                    <template v-slot:default="{ isActive }" >
                        <vcard class="modalEditList">
                            <h3 class="modalTitle">Edite o título da sua lista</h3>
                            <form action="">
                                <input class="input" type="text" name="title" id="title" placeholder="Dê um nome à sua lista" v-model="listTitle">
                            </form>
                            <div class="listButtonModalUpdate">
                                <button class="buttonUpdateList" @click="updateList">Atualizar lista</button>
                                <span class="buttonCloseModalUpdate" @click="isActive.value = false">Cancelar</span>
                            </div>
                        </vcard>
                    </template>
                </v-dialog>
                <div class="itemMenu" @click="deleteList">
                    <v-icon>mdi-trash-can</v-icon>
                    <span>Excluir lista</span>
                </div>
            </v-list>
        </v-menu>
    </div>
</template>

<style scoped>
    .listCard {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 24px 32px;
        background-color: var(--grey600);
        border-radius: 12px;
        margin-bottom: 20px;
    }
    .listTitle {
        color: var(--grey100);
        font-weight: medium;
        font-size: 16px;
    }
    .iconMenu {
        cursor: pointer;
    }
    .itemMenu {
        display: flex;
        align-items: center;
        padding: 12px 32px 12px 16px;
    }
    .itemMenu:hover {
        cursor: pointer;
        background-color: rgb(25, 35, 49)    
    }
    .itemMenu span {
        display: inline-block;
        margin-left: 12px;
        font-weight: 600;
    }
    .modalEditList {
        background-color: var(--grey600);
        border-radius: 12px;
        padding: 32px;
    }
    .modalTitle {
        font-weight: 600;
        margin-bottom: 16px;
        color: var(--blue100);
    }
    .buttonUpdateList {
        background-color: var(--blue600);
        padding: 12px 24px;
        border-radius: 6px;
        color: var(--grey100);
        font-weight: 600;
    }
    .buttonUpdateList:hover {
        background-color: #086688;
        transition: 0.2s;
    }
    .listButtonModalUpdate {
        display: flex;
        align-items: center;
        gap: 24px;
    }
    .buttonCloseModalUpdate {
        font-weight: 600;
        color: var(--grey300);
        padding: 8px 16px;
    }
    .buttonCloseModalUpdate:hover {
        cursor: pointer;
    }
</style>