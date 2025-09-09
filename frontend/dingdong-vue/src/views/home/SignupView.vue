<script setup>
import LabelFormElement from '@/components/form/LabelFormElement.vue';
import EmptyBox from '@/components/layout/EmptyBox.vue';
import { ref } from 'vue';

const step = ref(1);
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
                  variant="outlined"
                  rounded="lg"
                  type="text"
                />
              </label-form-element>

              <label-form-element
                text="이메일"
                required
              >
                <div class="d-flex ga-2">
                  <v-text-field
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
                    인증
                  </v-btn>
                </div>
              </label-form-element>

              <label-form-element
                text="성별"
                required
              >
                <v-btn-toggle
                  class="ga-6"
                  variant="outlined"
                  mandatory
                >
                  <v-btn
                    class="flex-grow-1"
                    value="female"
                    rounded="lg"
                    color="red"
                    border="e"
                  >
                    공주님
                  </v-btn>

                  <v-btn
                    class="flex-grow-1"
                    value="male"
                    rounded="lg"
                    color="blue"
                    border="s"
                  >
                    왕자님
                  </v-btn>
                </v-btn-toggle>
              </label-form-element>

              <empty-box :height="30" />
            </v-window-item>

            <v-window-item :value="2">
              <label-form-element
                text="예식 (희망)날짜"
                required
              >
                <v-date-input
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
                  variant="outlined"
                  rounded="lg"
                />
              </label-form-element>

              <label-form-element text="별명">
                <v-text-field
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
                  placeholder="8~10글자"
                  variant="outlined"
                  rounded="lg"
                  type="text"
                />
              </label-form-element>

              <empty-box height="37.6" />

              <v-btn
                style="height: 48px"
                block
                variant="outlined"
                rounded="lg"
                color="grey"
              >
                가입하기
              </v-btn>

              <empty-box height="22" />
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
            @click="step++"
          >
            Next
          </button>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped></style>
