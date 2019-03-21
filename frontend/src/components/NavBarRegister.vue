<template>
<section class="navappregister">
    <div>
    <!-- Create the login dialog -->
    <md-dialog :md-active.sync="loginDialog">
      <md-dialog-title>Login</md-dialog-title>
      <login/>
      <!--Button to close the dialog-->
    </md-dialog>

    <!--Create the register dialog-->
    <md-dialog :md-active.sync="registerDialog">
      <md-dialog-title>Create Account</md-dialog-title>
      <register/>
    </md-dialog>

    <!--The main navigation bar-->
    <md-toolbar class="md=accent" md-elevation="1">
        <!--SiteName refer to AccueilVue-->
        <a class="md-title" style="flex: 1" href ="/">SiteName</a>
        <!--Register button refer to the register page (RegisterVue)-->
        <md-button class="md-primary md-raised" @click="registerDialog = true">Sign in</md-button>
        <!--Login button open the login dialog-->
        <md-button class="md-primary md-raised" @click="loginDialog = true">Sign up</md-button>
    </md-toolbar>
    </div>
</section>
</template>

    
<style lang="scss" scoped>
.md-dialog{widows: 100%;}
</style>


<script>
import login from './Login.vue';
import register from './Register.vue';
export default {
    name: 'DialogCustom',
    components:{login, register},
    data: () => ({
      loginDialog: false,
      registerDialog: false,
    }),
    
}
</script>