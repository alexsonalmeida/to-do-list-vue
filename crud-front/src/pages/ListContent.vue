<script setup context lang="ts">
  import { onMounted, ref } from 'vue';
  import { api } from '../services/api';
  import { AxiosError } from 'axios';
  import ListCard from '../components/ListCard.vue';
  import { useRouter } from 'vue-router';

  const listTitle = ref('');
  const lists = ref([]);
  const router = useRouter()

  onMounted(() => {
    getLists()
  })

  function getLists() {
    api.get('/lists')
    .then((response)=>{
      lists.value = response.data
    }).catch((error)=> {
    })
  }

  function createList() {
    api.post('/lists', {
      title: listTitle.value,
    }).then((response) => {
      getLists()
    }).catch((error: AxiosError) => {
    })
  }

  function deleteList(id: string) {
    api.delete(`/lists/${id}`)
      .then(() => {
          getLists();
      }).catch(error => {
      });
  }

  function updateList(id: string, title) {
    api.put(`/lists/${id}`, {
      title: title.value
    }).then((response) => {
      getLists()
    }).catch(error => {
    })
  }

  function navigateToDetails(list) {
    router.push(`/ListTask/${list.id}`);
  }
</script>

<template>
  <main class="main">
    <div class="header d-flex flex-row align-center">
      <h1 class="title font-weight-bold">Suas To-do Lists</h1>
      <v-dialog max-width="500">
        <template v-slot:activator="{ props: activatorProps }" >
          <div class="addButton" v-bind="activatorProps">
            <span class="buttonTag">
              Criar Lista
            </span>
            <v-icon color="var(--blue200)">mdi-plus</v-icon>
          </div>          
        </template>
        <template v-slot:default="{ isActive }" >
          <vcard class="modalCreateList" title="Crie sua lista">
            <h3 class="modalTitle">Crie sua lista</h3>
            <form action="">
              <input class="input" type="text" name="title" id="title" placeholder="Dê um nome à sua lista" v-model="listTitle">
            </form>
            <div class="listButtonModalCreate">
              <button class="buttonCreateList" @click="createList()">Criar lista</button>
              <span class="buttonCloseModalCreate" @click="isActive.value = false">Cancelar</span>
            </div>
          </vcard>
        </template>
      </v-dialog>
    </div>
    <ListCard v-for="(list, index) in lists" :key="index" :id="list.id" :title="list.title" @listDeleted="deleteList" @list-updated="updateList" @click="navigateToDetails(list)"></ListCard>
    <div class="listEmptyMessage" v-show="lists.length === 0">
      Você não possui listas de tarefas
    </div>
  </main>
</template>

<style scoped>
  .title {
    font-family: 'Open Sans';
    color: var(--blue700);
    font-size: 34px;
  }
  .header {
    gap: 48px;
    margin-bottom: 24px;
  }
  .main {
    padding: 4rem 16rem;
  }
  .addButton {
    background-color: var(--blue700);
    padding: 12px 24px;
    border-radius: 6px;
    display: flex;
    align-items: center;
    gap: 10px;
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
  .modalCreateList {
    background-color: var(--grey600);
    border-radius: 12px;
    padding: 32px;
  }
  .modalTitle {
    font-weight: 600;
    margin-bottom: 16px;
    color: var(--blue100);
  }
  .listButtonModalCreate {
    display: flex;
    align-items: center;
    gap: 24px;
  }
  
  .buttonCreateList {
    background-color: var(--sucess);
    padding: 12px 24px;
    border-radius: 6px;
    color: var(--grey100);
    font-weight: 600;
  }
  .buttonCreateList:hover {
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
</style>./components/ListCard.vue
