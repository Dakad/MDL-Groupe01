<template>
  <section class="register">
    <form novalidate class="md-layout" @submit.prevent="validateUser">
      <md-card class="md-layout-item md-small-size-100">
        <!-- -->

        <md-card-content>
          <div class="md-layout md-gutter">
            <div class="md-layout-item md-small-size-100">
              <md-field :class="getValidationClass('firstName')">
                <label for="first-name">First Name</label>
                <md-input
                  name="first-name"
                  id="first-name"
                  autocomplete="given-name"
                  v-model.lazy.trim="form.firstName"
                  :disabled="sending"
                />

                <span class="md-error" v-if="!$v.form.firstName.required">The first name is required</span>
                <span class="md-error" v-if="!$v.form.firstName.minLength">Invalid first name</span>
              </md-field>
            </div>

            <div class="md-layout-item md-small-size-100">
              <md-field :class="getValidationClass('lastName')">
                <label for="last-name">Last Name</label>
                <md-input
                  name="last-name"
                  id="last-name"
                  autocomplete="family-name"
                  v-model.lazy.trim="form.lastName"
                  :disabled="sending"
                />
                <span class="md-error" v-if="!$v.form.lastName.required">The last name is required</span>
                <span class="md-error" v-if="!$v.form.lastName.minLength">Invalid last name</span>
              </md-field>
            </div>
          </div>

          <md-field
            :class="[getValidationClass('email'), {'md-invalid': invalid['email'] != null}]"
          >
            <label for="email">Email</label>
            <md-input
              type="email"
              name="email"
              id="email"
              autocomplete="email"
              v-model.lazy.trim="form.email"
              :disabled="sending"
            />
            <span class="md-error" v-if="!$v.form.email.required">The email is required</span>
            <span class="md-error" v-if="!$v.form.email.email">Invalid email</span>
            <span class="md-error" v-if="invalid['email']">{{invalid['email']}}</span>
          </md-field>

          <md-field
            :class="[getValidationClass('username'), {'md-invalid': invalid['username'] != null}]"
          >
            <label for="username">Username</label>
            <md-input
              type="text"
              name="username"
              id="username"
              autocomplete="username"
              v-model.lazy.trim="form.username"
              :disabled="sending"
            />
            <span class="md-error" v-if="!$v.form.username.required">The username is required</span>
            <span class="md-error" v-if="!$v.form.username.minLength">Invalid first name</span>
            <span class="md-error" v-if="invalid['username']">{{invalid['username']}}</span>
          </md-field>
          <!-- <password-strength-meter :password="form.password"></password-strength-meter> -->
          <md-field
            :class="[getValidationClass('password'), {'md-invalid': invalid['password'] != null}]"
          >
            <label for="password">Password</label>
            <md-input
              type="password"
              name="password"
              id="password"
              pattern=".([a-zA-Z0-9]+)."
              autocomplete="password"
              v-model.lazy.trim="form.password"
              :disabled="sending"
            />
            <span
              :class="[{'md-helper-text':!invalid['password']}, {'md-error':invalid['password']} ]"
            >
              Use 8 to 30 characters with a mix of
              <b>UPPER</b>, lowercase and numbers
            </span>

            <span class="md-error" v-if="!$v.form.password.required">The password is required</span>
            <span class="md-error" v-if="!$v.form.password.minLength">At least 8 characters</span>
            <span
              class="md-error"
              v-if="!$v.form.password.sameAsEmail"
            >Cannot be the same as the email</span>
            <span
              class="md-error"
              v-if="!$v.form.password.sameAsUsername"
            >Cannot be the same as the username</span>
          </md-field>
        </md-card-content>

        <div class="loading-overlay" v-if="sending">
          <md-progress-bar md-mode="indeterminate"/>
        </div>
        <hr>
        <!-- <pre>{{ $v.form.$invalid  }}</pre> -->
        <md-card-actions>
          <!-- <md-button block type="submit" variant="outline-primary">Create user</md-button> -->
          <b-button block type="submit" variant="primary" :disabled="sending">Create account</b-button>

          <!-- <md-button type="submit" class="md-primary" :disabled="$v.form.$invalid">Create user</md-button> -->
        </md-card-actions>
      </md-card>
    </form>
  </section>
</template>

<script>
import { validationMixin } from "vuelidate";
import {
  required,
  email,
  minLength,
  maxLength,
  not,
  sameAs
} from "vuelidate/lib/validators";
// import PasswordStrengthMeter  from "./PasswordStrengthMeter.vue";
import { postSignin } from "@/services/api-user";

export default {
  name: "Signin",
  components: {},
  mixins: [validationMixin],
  props: {},
  data: function() {
    return {
      form: {
        firstName: null,
        lastName: null,
        username: null,
        email: null,
        password: null
      },
      sending: false,
      invalid: {}
    };
  },
  validations: {
    form: {
      firstName: {
        required,
        minLength: minLength(2)
      },
      lastName: {
        required,
        minLength: minLength(2)
      },
      username: {
        required,
        minLength: minLength(2)
      },
      email: {
        required,
        email
      },
      password: {
        required,
        minLength: minLength(8),
        maxLength: maxLength(30),
        sameAsUsername: not(sameAs("username")),
        sameAsEmail: not(sameAs("email"))
      }
    }
  },
  methods: {
    getValidationClass(fieldName) {
      const field = this.$v.form[fieldName];
      if (field) {
        return {
          "md-invalid": field.$invalid && field.$dirty
        };
      }
    },
    clearForm() {
      this.$v.$reset();
      this.form.firstName = null;
      this.form.lastName = null;
      this.form.username = null;
      this.form.password = null;
      this.form.email = null;
    },
    validateUser() {
      this.$v.$touch();
      if (!this.$v.$invalid) {
        this.invalid = {};
        this.saveUser();
      } else {
        this.$emit("error", "Invalid data - Please correct your input");
      }
    },
    saveUser() {
      this.sending = true;
      postSignin(this.form)
        .then(res => {
          console.log(res);
          this.sending = false;
          this.clearForm();
          this.$emit("success");
        })
        .catch(err => {
          if (err.status == 400) {
            this.msg = "Registration denied - Please correct your input";
            this.invalid = Object.assign({}, err.body["validation"]);
          }
          if (err.status == 409) {
            this.msg = "Registration denied - " + err.body["error"];
          }
          console.error(err);
          this.sending = false;
          this.$emit("error", this.msg);
        });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.md-progress-bar {
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
}
</style>
