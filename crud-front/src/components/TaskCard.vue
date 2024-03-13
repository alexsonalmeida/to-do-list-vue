<script setup lang="ts">
    import { ref } from 'vue';

    const props = defineProps<{
        id: string,
        title: string,
        description: string,
        finished: boolean
    }>();
    const emit = defineEmits(['taskDeleted', 'taskUpdated', 'taskCompleted', 'tasksCompletedByCheck']);
    const taskTitle = ref(props.title);
    const taskDescription = ref(props.description)
    const finished = ref(props.finished)
    
    function deleteTask() {
        emit('taskDeleted', props.id);
    }

    function updateTask() {
        emit('taskUpdated', props.id, taskTitle, taskDescription, finished)
    }

    function setTaskAsCompleted() {
        finished.value = true
        emit('taskCompleted', props.id)
    }

    function updateTaskFinished() {
        emit('tasksCompletedByCheck', props.id, finished)
    }
</script>

<template>
    <div class="taskCard">
        <v-checkbox v-model="finished" @click="updateTaskFinished" style="color: var(--blue700);" ></v-checkbox>
        <div class="taskData">
            <div>
                <p class="taskTitle">{{ props.title }}</p>
                <p class="taskDescription">{{ props.description }}</p>                
            </div>
            <v-menu :location="'end'">
                <template v-slot:activator="{props}">
                    <v-icon style="color: var(--grey400);" size="26px" v-bind="props">mdi-dots-horizontal</v-icon>   
                </template>
                <v-list style="background-color: var(--grey500); border-radius: 8px; padding:0px; color: var(--grey300)">
                    <div class="itemMenu" @click="setTaskAsCompleted">
                        <v-icon>mdi-check-circle</v-icon>
                        <span>Marcar como concluída</span>
                    </div>
                    <v-dialog max-width="500">
                        <template v-slot:activator="{ props: activatorProps }">
                            <div class="itemMenu itemMenuTop" v-bind="activatorProps">
                                <v-icon>mdi-pencil</v-icon>
                                <span>Editar tarefa</span>
                            </div>                         
                        </template>
                        <template v-slot:default="{ isActive }" >
                            <vcard class="modalEditList">
                                <h3 class="modalTitle">Edite sua tarefa</h3>
                                <form action="">
                                    <label class="labelTask" for="title">Nome da tarefa</label>
                                    <input class="input" type="text" name="title" id="title" placeholder="Dê um nome à sua lista" v-model="taskTitle">
                                    <label class="labelTask" for="description">Descrição</label>
                                    <input class="input" autocomplete="off" type="text" name="description" id="description" placeholder="Digite a descrição da sua task" v-model="taskDescription">
                                </form>
                                <div class="listButtonModalUpdate">
                                    <button class="buttonUpdateList" @click="updateTask">Atualizar tarefa</button>
                                    <span class="buttonCloseModalUpdate" @click="isActive.value = false">Cancelar</span>
                                </div>
                            </vcard>
                        </template>
                    </v-dialog>
                    <div class="itemMenu" @click="deleteTask">
                        <v-icon>mdi-trash-can</v-icon>
                        <span>Excluir tarefa</span>
                    </div>
                </v-list>
            </v-menu>
            
        </div>
    </div>
</template>

<style scoped>
    .taskCard {
        display: flex;
        align-items: center;
        gap: 12px;
        width: 100%;
        margin-bottom: 20px;
    }
    .taskTitle {
        color: var(--grey100);
        font-weight: medium;
        font-size: 16px;
    }
    .taskData {
        display: flex;
        justify-content: space-between;
        background-color: var(--grey600);
        padding: 28px 32px;
        border-radius: 12px;
        width: 100%;
    }
    .taskTitle {
        color: var(--blue100);
        font-weight: 600;
        font-size: 20px;
        margin-bottom: 12px;
    }
    .taskDescription {
        color: var(--grey300);
        font-weight: 500;
        font-size: 14px;
    }
    .labelTask {
        margin-bottom: 12px;
        display: block;
        font-weight: 500;
        color: var(--grey300);
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
        margin-bottom: 20px;
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