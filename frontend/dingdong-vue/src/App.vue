<script setup>
import { RouterView } from 'vue-router';
import DefaultAppBar from '@/components/appbar/DefaultAppBar.vue';
</script>

<template>
  <v-app>
    <v-app-bar elevation="0">
      <default-app-bar />
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>

    <v-footer></v-footer>
  </v-app>
</template>

<style scoped></style>
