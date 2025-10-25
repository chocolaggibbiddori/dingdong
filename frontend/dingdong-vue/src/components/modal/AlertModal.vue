<script setup>
import BaseModal from '@/components/modal/BaseModal.vue';

const open = defineModel({
  type: Boolean,
  default: false
});

defineProps({
  title: {
    type: String,
    default: '안내'
  },
  message: {
    type: String,
    required: true
  }
});
</script>

<template>
  <base-modal
    v-model="open"
    :title
  >
    {{ message }}

    <template #actions>
      <v-btn
        color="primary"
        variant="flat"
        @click="open = false"
      >
        확인
      </v-btn>
    </template>
  </base-modal>
</template>

<style scoped></style>
