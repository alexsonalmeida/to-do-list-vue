<script setup lang="ts">
  import TaskCard from '../components/TaskCard.vue';
  import { onBeforeMount, ref } from 'vue';
  import { useRoute } from 'vue-router';
  import { api } from '../services/api';
  
  const route = useRoute();
  const id = route.params.id;
  const listData = ref({id: '', title: ''});
  const tasks = ref([])
  const taskTitle = ref('')
  const taskDescription = ref('')
  const finished = ref(false)
  const isEmpty = ref(false)
  const tasksToBeCompleted = ref([])
  const toggleUpdateButton = ref(false)

  onBeforeMount(async() => {
      await getList()
      await getTasks()
  })

  async function getList() {
    await api.get(`/lists/${id}`)
    .then((response)=>{
        listData.value = response.data
    }).catch((error)=> {
    })
  }

  async function getTasks() {
    await api.get(`/tasks/${id}`)
    .then((response) => {
        tasks.value = response.data
        isEmpty.value = false
    })
  }

  function createTask() {
    api.post(`/tasks/${id}`, {
        title: taskTitle.value,
        description: taskDescription.value,
        finished: finished.value
    })
    .then((response) => {
        getTasks()
    }).catch((error) => {
    })
  }    

  function updateTask(id, title, description, finished) {
    api.put('/tasks', [
      {
        id,
        title: title.value,
        description: description.value,
        finished: finished.value
      }
    ]).then(response => {
      getTasks()
    }).catch(error => {
    })
  }

  function taskCompleted(id) {
    api.put('/tasks', [
      {
        id,
        finished: true          
      }
    ]).then((response) => {
    }).catch(error => {
      console.log(error)
    })
  }

  function updateTasksSelecteds() {
    console.log(tasksToBeCompleted.value)
    api.put('/tasks', tasksToBeCompleted.value)
    .then(response => {
      getTasks()
      toggleUpdateButton.value = false
      tasksToBeCompleted.value = []
    }).catch(error => {
      console.log(error)
    })
  }

  function deleteTask(id: string) {
    api.delete(`tasks/${id}`)
    .then(async (response) => {
      await getTasks()
    }).catch((error) => {
      getTasks()
    })
  }

  function tasksCompletedByCheck(id, finished) {
    const task = {
      id,
      finished: !finished.value
    }
    tasksToBeCompleted.value.push(task)
    toggleUpdateButton.value = true
  }

</script>

<template>
  <div class="conteiner">
    <h1 class="title font-weight-bold" v-if="listData.id">{{ listData.title }}</h1>
    <v-dialog max-width="500">
      <template v-slot:activator="{ props: activatorProps }" >
        <div class="addButton" v-bind="activatorProps">
          <span class="buttonTag">
            Criar tarefa
          </span>
          <v-icon color="var(--blue200)">mdi-plus</v-icon>
        </div>          
      </template>
      <template v-slot:default="{ isActive }" >
        <vcard class="modalCreateTask" title="Crie sua lista">
          <h3 class="modalTitle">Crie sua tarefa</h3>
          <form action="">
            <label class="labelTask" for="title">Nome da tarefa</label>
            <input class="input" type="text" name="title" id="title" placeholder="Dê um nome à sua tarefa" v-model="taskTitle">
            <label class="labelTask" for="Description">Escreva uma descrição para sua tarefa</label>
            <input class="input" type="text" autocomplete="off" name="description" id="description" placeholder="Escreva a descrição da sua tarefa" v-model="taskDescription">
          </form>
          <div class="taskButtonModalCreate">
            <button class="buttonCreateTask" @click="createTask()">Criar tarefa</button>
            <span class="buttonCloseModalCreate" @click="isActive.value = false">Cancelar</span>
          </div>
        </vcard>
        </template>
    </v-dialog>

    <TaskCard v-for="(task) in tasks" :key="task.id" :id="task.id" :title="task.title" :description="task.description" :finished="task.finished" @taskDeleted="deleteTask" @task-updated="updateTask" @taskCompleted="taskCompleted" @tasksCompletedByCheck="tasksCompletedByCheck"></TaskCard>
    <div class="listEmptyMessage" v-show="tasks.length === 0">
      Essa lista não possui tarefas
    </div>
    <div class="buttonUpdateTasks" v-show="toggleUpdateButton" @click="updateTasksSelecteds">
      <span class="buttonUpdateTasksText">
        Atualizar tasks
      </span>
      <v-icon size="18px">mdi-reload</v-icon>
    </div>
  </div>
</template>

<style scoped>
  .conteiner {
    padding: 4rem 16rem;
  }
  .title {
    font-family: 'Open Sans';
    color: var(--blue100);
    font-size: 34px;
    margin-bottom: 20px;
  }
  .addButton {
    background-color: var(--blue700);
    padding: 12px 24px;
    border-radius: 6px;
    display: flex;
    align-items: center;
    gap: 10px;
    width: fit-content;
    margin-bottom: 32px;
  }
  .addButton:hover {
    cursor: pointer;
    background-color: #01384b;
    transition: 0.2s;
  }
  .buttonTag {
    font-family: "Open Sans";
    font-weight: 700;
    color: var(--blue200);
  }
  .modalCreateTask {
    background-color: var(--grey600);
    border-radius: 12px;
    padding: 32px;
  }
  .labelTask {
    margin-bottom: 12px;
    display: block;
    font-weight: 500;
    color: var(--grey300);
  }
  .modalTitle {
    font-weight: 600;
    margin-bottom: 20px;
    color: var(--blue100);
  }
  .taskButtonModalCreate {
    display: flex;
    align-items: center;
    gap: 24px;
  }
  .buttonCreateTask {
    background-color: var(--sucess);
    padding: 12px 24px;
    border-radius: 6px;
    color: var(--grey100);
    font-weight: 600;
  }
  .buttonCreateTask:hover {
    background-color: #028d66;
    transition: 0.2s;
  }
  .buttonCloseModalCreate {
    font-weight: 600;
    color: var(--grey300);
    padding: 8px 16px;
  }
  .buttonCloseModalCreate:hover {
    cursor: pointer;
  }
  .listEmptyMessage {
    font-weight: 500;
    color: var(--grey400);
    font-size: 16px;
    font-style: italic;
  }
  .buttonUpdateTasks {
    padding: 12px 24px;
    background-color: var(--blue700);
    border-radius: 8px;
    color: var(--blue200);
    max-width: fit-content;
    display: flex;
    align-items: center;
    gap: 12px;
    margin-top: 48px;
  }
  .buttonUpdateTasksText {
    font-weight: 600;
  }
  .buttonUpdateTasks:hover {
    cursor: pointer;
  }
</style>
