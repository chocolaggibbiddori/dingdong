import { ref } from 'vue';

export function useModal() {
  const open = ref(false);

  function openModal() {
    open.value = true;
  }

  function closeModal() {
    open.value = false;
  }

  return {
    open,
    openModal,
    closeModal
  };
}
