<script setup>
import { computed } from 'vue';

const open = defineModel({
  type: Boolean,
  default: false
});

const props = defineProps({
  title: {
    type: String,
    required: true
  },
  titleClass: {
    type: String,
    default: ''
  },
  position: {
    type: String,
    default: 'center',
    validator: value => ['center', 'right'].includes(value)
  },
  actionOff: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(['confirm']);

function onConfirm() {
  emit('confirm');
  open.value = false;
}

const dialogPosition = {
  center: {
    maxWidth: '400',
    transition: 'dialog-transition'
  },
  right: {
    width: '500',
    contentClass: 'dialog-right',
    transition: 'slide-x-reverse-transition'
  }
};
const dialogProps = computed(() => dialogPosition[props.position]);
</script>

<template>
  <v-dialog
    v-model="open"
    v-bind="dialogProps"
  >
    <v-card rounded="lg">
      <v-card-title :class="titleClass">
        {{ title }}
      </v-card-title>

      <v-spacer />

      <v-card-text>
        <slot />
      </v-card-text>

      <v-spacer />

      <v-card-actions v-if="!actionOff">
        <slot name="actions">
          <v-btn
            variant="text"
            @click="open = false"
          >
            취소
          </v-btn>

          <v-btn
            color="primary"
            variant="flat"
            @click="onConfirm"
          >
            확인
          </v-btn>
        </slot>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style>
.dialog-right {
  position: absolute !important;
  right: 0 !important;
  margin: 0 !important;
  min-height: 100% !important;
}
</style>
