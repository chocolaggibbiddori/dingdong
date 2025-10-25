<script setup>
import { onMounted, ref } from 'vue';
import BaseModal from '@/components/modal/BaseModal.vue';
import commonApi from '@/apis/home/commonApi.js';

const open = defineModel({
  type: Boolean,
  default: false
});

const props = defineProps({
  email: {
    type: String,
    required: true
  }
});

const emits = defineEmits(['verified']);

const answer = ref('');
const code = ref('');
const errorMessage = ref('');
function verifyCode() {
  if (answer.value === code.value) {
    emits('verified');
    open.value = false;
  } else {
    errorMessage.value = '올바른 인증 코드를 입력해 주세요.';
  }
}
function sendEmail() {
  // prettier-ignore
  commonApi
    .sendEmailCode(props.email)
    .then(({ code }) => answer.value = code);
}
onMounted(() => sendEmail());
</script>

<template>
  <base-modal
    v-model="open"
    title="인증번호 입력"
    title-class="font-kohi-nanum-otf-light text-secondary"
    action-off
  >
    <div class="d-flex flex-column ga-5">
      <v-text-field
        v-model="code"
        :error-messages="errorMessage"
        variant="outlined"
        rounded="lg"
        type="text"
        hide-details="auto"
      />

      <v-btn
        class="bg-primary font-kohi-nanum-otf-light text-tertiary"
        @click="verifyCode"
        variant="flat"
        rounded="lg"
        height="56"
        block
      >
        확인
      </v-btn>

      <v-btn
        class="bg-secondary font-kohi-nanum-otf-light text-tertiary"
        @click="sendEmail"
        variant="flat"
        rounded="lg"
        height="56"
        block
      >
        재발송
      </v-btn>
    </div>
  </base-modal>
</template>

<style>
.font-kohi-nanum-otf-light {
  font-size: 20px;
}
</style>
