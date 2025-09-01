<script setup>
import RouterLinkButton from '@/components/button/RouterLinkButton.vue';
</script>

<template>
  <router-link to="/">
    <v-icon-btn
      class="ml-3"
      icon="mdi-home"
      color="white"
    />
  </router-link>

  <v-icon-btn
    class="ml-1"
    icon="mdi-menu"
  />

  <v-icon-btn
    class="ml-1"
    icon="mdi-magnify"
  />

  <v-spacer />

  <router-link-button
    class="mr-5 font-be-vietnam-pro-thin"
    text="Login"
    to="login"
  />
</template>

<style scoped>
.font-be-vietnam-pro-thin {
  font-size: 16pt;
}
</style>
