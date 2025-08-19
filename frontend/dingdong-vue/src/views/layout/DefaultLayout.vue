<script setup>
import DefaultAppBar from '@/components/appbar/DefaultAppBar.vue';
import { RouterView } from 'vue-router';
</script>

<template>
  <v-app-bar elevation="0">
    <default-app-bar />
  </v-app-bar>

  <v-main>
    <router-view />
  </v-main>
</template>

<style scoped></style>
