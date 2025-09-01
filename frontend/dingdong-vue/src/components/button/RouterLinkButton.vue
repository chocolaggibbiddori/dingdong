<script setup>
defineProps({
  text: {
    type: String,
    required: false,
    default: 'Default text'
  },
  to: String
});
</script>

<template>
  <router-link
    class="dingdong-text-button"
    :to
  >
    <slot>{{ text }}</slot>
  </router-link>
</template>

<style scoped>
.dingdong-text-button {
  text-decoration: none;
  color: inherit;
}

.dingdong-text-button:hover {
  text-decoration: none;
  color: inherit;
}
</style>
