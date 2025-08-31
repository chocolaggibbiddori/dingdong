<script setup></script>

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

  <router-link
    class="mr-5 font-be-vietnam-pro-thin dingdong-text-button login-link"
    to="login"
  >
    Login
  </router-link>
</template>

<style scoped>
.font-be-vietnam-pro-thin {
  font-size: 16pt;
}

.login-link {
  text-decoration: none;
  color: inherit;
}

.login-link:hover {
  text-decoration: none;
  color: inherit;
}
</style>
