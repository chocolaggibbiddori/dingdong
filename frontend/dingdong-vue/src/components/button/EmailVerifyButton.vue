<script setup>
import { computed, ref } from 'vue';
import { isValidEmail } from '@/utils/emailUtil.js';
import { useModal } from '@/composables/useModal.js';
import EmailCodeModal from '@/components/modal/EmailCodeModal.vue';

const email = defineModel({
  type: String,
  required: true
});

defineProps({
  text: {
    type: String,
    required: false,
    default: '인증'
  }
});

const emits = defineEmits(['verified']);

const disabled = computed(() => !isValidEmail(email.value));
const modal = useModal();

const verified = ref(false);
function onVerified() {
  verified.value = true;
  emits('verified');
}
</script>

<template>
  <div>
    <v-btn
      v-if="!verified"
      :disabled
      height="56"
      variant="outlined"
      rounded="lg"
      color="grey"
      @click.prevent="modal.openModal()"
    >
      {{ text }}
    </v-btn>

    <v-icon
      v-else
      class="check-icon mt-3"
      icon="mdi-check-bold"
      color="green"
    />

    <email-code-modal
      v-model="modal.open.value"
      :email
      @verified="onVerified"
    />
  </div>
</template>

<style scoped>
.check-icon {
  width: 64px;
}
</style>
