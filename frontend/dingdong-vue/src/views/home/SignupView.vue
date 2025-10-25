<script setup>
import { computed, reactive, ref } from 'vue';
import commonApi from '@/apis/home/commonApi.js';
import LabelFormElement from '@/components/form/LabelFormElement.vue';
import EmailVerifyButton from '@/components/button/EmailVerifyButton.vue';
import { isValidKoreanName } from '@/utils/textUtil.js';

const step = ref(1);
const showPassword = ref(false);

const formData = reactive({
  name: '',
  email: '',
  sex: '',
  weddingDate: '',
  weddingRegion: '',
  nickname: '',
  username: '',
  password: ''
});

const possibleNext1 = computed(() => {
  return isValidKoreanName(formData.name) && emailDisabled.value && !!formData.sex;
});
const possibleNext = computed(() => {
  if (step.value === 1) {
    return possibleNext1.value;
  }

  return true;
});

const nameRule = name => isValidKoreanName(name) || '이름을 입력해 주세요.';

const emailDisabled = ref(false);

const regionList = ref([]);
// prettier-ignore
commonApi
  .getRegionList()
  .then(({ codeList }) => (regionList.value = codeList));
</script>

<template>
  <v-container
    class="fill-height"
    fluid
  >
    <v-row class="mb-15 justify-center">
      <v-col
        class="text-center"
        cols="12"
        sm="6"
        md="4"
      >
        <h2 class="mb-3 font-kaisei-decol-regular">Create account</h2>

        <v-form class="font-kohi-nanum-otf-light">
          <v-window v-model="step">
            <v-window-item :value="1">
              <label-form-element
                text="이름"
                required
              >
                <v-text-field
                  v-model="formData.name"
                  class="text-left"
                  variant="outlined"
                  rounded="lg"
                  type="text"
                  :rules="[nameRule]"
                />
              </label-form-element>

              <label-form-element
                text="이메일"
                required
              >
                <div class="d-flex ga-2">
                  <v-text-field
                    v-model="formData.email"
                    :disabled="emailDisabled"
                    variant="outlined"
                    rounded="lg"
                    type="text"
                  />

                  <email-verify-button
                    v-model="formData.email"
                    @verified="emailDisabled = true"
                  />
                </div>
              </label-form-element>

              <label-form-element
                text="성별"
                required
              >
                <v-btn-toggle
                  v-model="formData.sex"
                  class="ga-6"
                  style="height: 56px; margin-bottom: 22px"
                  variant="outlined"
                  mandatory
                >
                  <v-btn
                    class="flex-grow-1"
                    value="FEMALE"
                    rounded="lg"
                    color="red"
                    border="e"
                  >
                    공주님
                  </v-btn>

                  <v-btn
                    class="flex-grow-1"
                    value="MALE"
                    rounded="lg"
                    color="blue"
                    border="s"
                  >
                    왕자님
                  </v-btn>
                </v-btn-toggle>
              </label-form-element>
            </v-window-item>

            <v-window-item :value="2">
              <label-form-element
                text="예식 (희망)날짜"
                required
              >
                <v-date-input
                  v-model="formData.weddingDate"
                  input-format="yyyy/mm/dd"
                  variant="outlined"
                  rounded="lg"
                  :prepend-icon="null"
                  prepend-inner-icon="mdi-calendar"
                />
              </label-form-element>

              <label-form-element
                text="예식 (희망)지역"
                required
              >
                <v-select
                  v-model="formData.weddingRegion"
                  :items="regionList"
                  variant="outlined"
                  rounded="lg"
                />
              </label-form-element>

              <label-form-element text="별명">
                <v-text-field
                  v-model="formData.nickname"
                  variant="outlined"
                  rounded="lg"
                  type="text"
                />
              </label-form-element>
            </v-window-item>

            <v-window-item :value="3">
              <label-form-element
                text="아이디"
                required
              >
                <div class="d-flex ga-2">
                  <v-text-field
                    v-model="formData.username"
                    variant="outlined"
                    rounded="lg"
                    type="text"
                  />

                  <v-btn
                    height="56"
                    variant="outlined"
                    rounded="lg"
                    color="grey"
                  >
                    중복확인
                  </v-btn>
                </div>
              </label-form-element>

              <label-form-element
                text="비밀번호"
                required
              >
                <v-text-field
                  v-model="formData.password"
                  :class="{ 'font-sans-serif': !showPassword }"
                  placeholder="8~10글자"
                  variant="outlined"
                  rounded="lg"
                  :type="showPassword ? 'text' : 'password'"
                  :append-inner-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                  @click:append-inner="showPassword = !showPassword"
                />
              </label-form-element>

              <v-btn
                style="height: 56px; margin-top: 30px; margin-bottom: 22px"
                block
                variant="outlined"
                rounded="lg"
                color="grey"
              >
                가입하기
              </v-btn>
            </v-window-item>
          </v-window>
        </v-form>

        <div class="mt-5 d-flex justify-space-between">
          <button
            v-if="step > 1"
            class="font-kaisei-decol-regular"
            @click="step--"
          >
            Prev
          </button>

          <button
            v-if="step < 3"
            class="font-kaisei-decol-regular ms-auto"
            :disabled="!possibleNext"
            @click="step++"
          >
            Next
          </button>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.font-sans-serif {
  font-family: sans-serif;
}
</style>
